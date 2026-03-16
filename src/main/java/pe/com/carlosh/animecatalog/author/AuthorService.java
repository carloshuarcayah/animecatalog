package pe.com.carlosh.animecatalog.author;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.com.carlosh.animecatalog.author.dto.AuthorRequestDTO;
import pe.com.carlosh.animecatalog.author.dto.AuthorResponseDTO;
import pe.com.carlosh.animecatalog.common.exception.DuplicateResourceException;
import pe.com.carlosh.animecatalog.common.exception.ResourceNotFoundException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthorService {
    private final AuthorRepository authorRepository;

    public Page<AuthorResponseDTO> findAll(Pageable pageable){
        return authorRepository.findByActiveTrue(pageable).map(AuthorMapper::toResponse);
    }

    public AuthorResponseDTO findById(Long id){
        Author existingAuthor = authorRepository.findByIdAndActiveTrue(id)
                .orElseThrow(()-> new ResourceNotFoundException("Author not found with id: "+id));

        return AuthorMapper.toResponse(existingAuthor);
    }

    public Page<AuthorResponseDTO> searchByName(String name, Pageable pageable){
        return authorRepository.searchByName(name, pageable).map(AuthorMapper::toResponse);
    }

    @Transactional
    public AuthorResponseDTO create(AuthorRequestDTO req){
        if(authorRepository.existsByFirstNameIgnoreCaseAndLastNameIgnoreCase(req.firstName(), req.lastName())){
            throw new DuplicateResourceException("This author already exists.");
        }

        Author author = AuthorMapper.toEntity(req);

        authorRepository.save(author);

        return AuthorMapper.toResponse(author);
    }

    @Transactional
    public AuthorResponseDTO update(Long id,AuthorRequestDTO req){
        Author author = authorRepository.findByIdAndActiveTrue(id)
                .orElseThrow(()-> new ResourceNotFoundException("Author not found with id: "+id));

        boolean changedName=  !author.getFirstName().equalsIgnoreCase(req.firstName())||!author.getLastName().equalsIgnoreCase(req.lastName());

        if(changedName&&authorRepository.existsByFirstNameIgnoreCaseAndLastNameIgnoreCase(req.firstName(), req.lastName())){
            throw new DuplicateResourceException("This author already exists.");
        }

        AuthorMapper.updateEntity(author,req);

        authorRepository.save(author);

        return AuthorMapper.toResponse(author);
    }

    @Transactional
    public AuthorResponseDTO enable(Long id){
        Author author = authorRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Author not found with id: "+id));

        author.setActive(true);

        authorRepository.save(author);

        return AuthorMapper.toResponse(author);
    }

    @Transactional
    public AuthorResponseDTO delete(Long id){
        Author author = authorRepository.findByIdAndActiveTrue(id)
                .orElseThrow(()-> new ResourceNotFoundException("Author not found with id: "+id));

        author.setActive(false);

        authorRepository.save(author);

        return AuthorMapper.toResponse(author);
    }

}