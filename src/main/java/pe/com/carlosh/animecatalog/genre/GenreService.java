package pe.com.carlosh.animecatalog.genre;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.com.carlosh.animecatalog.common.exception.DuplicateResourceException;
import pe.com.carlosh.animecatalog.common.exception.ResourceNotFoundException;
import pe.com.carlosh.animecatalog.genre.dto.GenreRequestDTO;
import pe.com.carlosh.animecatalog.genre.dto.GenreResponseDTO;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GenreService {
    private final GenreRepository genreRepository;

    public Page<GenreResponseDTO> findAllActives(Pageable pageable){
        return genreRepository.findByActiveTrue(pageable).map(GenreMapper::toResponse);
    }

    public GenreResponseDTO findById(Long id){
        Genre existingGenre= genreRepository.findByIdAndActiveTrue(id).orElseThrow(()->new ResourceNotFoundException("Genre not found with id:"+id));
        return GenreMapper.toResponse(existingGenre);
    }

    public Page<GenreResponseDTO> searchByName(String name, Pageable pageable){
        return genreRepository.findByNameContainingIgnoreCase(name,pageable).map(GenreMapper::toResponse);
    }

    @Transactional
    public GenreResponseDTO create(GenreRequestDTO req){
        if(genreRepository.existsByNameIgnoreCase(req.name())){
            throw new DuplicateResourceException("Genre name already exists");
        }

        Genre genre = GenreMapper.toEntity(req);

        genreRepository.save(genre);

        return GenreMapper.toResponse(genre);
    }

    @Transactional
    public GenreResponseDTO update(Long id, GenreRequestDTO req){
        Genre existingGenre = genreRepository.findByIdAndActiveTrue(id).orElseThrow(()->new ResourceNotFoundException("Genre not found with id: "+id));

        boolean itsName = req.name().equalsIgnoreCase(existingGenre.getName());

        if(!itsName && genreRepository.existsByNameIgnoreCase(req.name())){
            throw new DuplicateResourceException("Genre name already exists");
        }

        GenreMapper.updateGenre(existingGenre,req);

        genreRepository.save(existingGenre);

        return GenreMapper.toResponse(existingGenre);
    }

    @Transactional
    public GenreResponseDTO delete(Long id){
        Genre existingGenre = genreRepository.findByIdAndActiveTrue(id).orElseThrow(()->new ResourceNotFoundException("Genre not found with id: "+id));
        existingGenre.setActive(false);

        genreRepository.save(existingGenre);

        return GenreMapper.toResponse(existingGenre);
    }

    @Transactional
    public GenreResponseDTO enable(Long id){
        Genre existingGenre = genreRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Genre not found with id: "+id));
        existingGenre.setActive(true);

        genreRepository.save(existingGenre);

        return GenreMapper.toResponse(existingGenre);
    }

}
