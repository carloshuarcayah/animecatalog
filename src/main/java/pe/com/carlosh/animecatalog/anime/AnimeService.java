package pe.com.carlosh.animecatalog.anime;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.com.carlosh.animecatalog.anime.dto.AnimeRequestDTO;
import pe.com.carlosh.animecatalog.anime.dto.AnimeResponseDTO;
import pe.com.carlosh.animecatalog.author.Author;
import pe.com.carlosh.animecatalog.author.AuthorRepository;
import pe.com.carlosh.animecatalog.common.exception.DuplicateResourceException;
import pe.com.carlosh.animecatalog.common.exception.ResourceNotFoundException;
import pe.com.carlosh.animecatalog.genre.Genre;
import pe.com.carlosh.animecatalog.genre.GenreRepository;
import pe.com.carlosh.animecatalog.studio.Studio;
import pe.com.carlosh.animecatalog.studio.StudioRepository;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AnimeService {
    private final AnimeRepository animeRepository;
    private final StudioRepository studioRepository;
    private final GenreRepository genreRepository;
    private final AuthorRepository authorRepository;

    public Page<AnimeResponseDTO> findAllActives(Pageable pageable) {
        return animeRepository.findByActiveTrue(pageable).map(AnimeMapper::toResponse);
    }

    public AnimeResponseDTO findById(Long id) {
        Anime existingAnime = animeRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("Anime nnot found with id: " + id));
        return AnimeMapper.toResponse(existingAnime);
    }

    public Page<AnimeResponseDTO> searchByName(String name, Pageable pageable) {
        return animeRepository.findByNameContainingIgnoreCaseAndActiveTrue(name, pageable).map(AnimeMapper::toResponse);
    }

    @Transactional
    public AnimeResponseDTO create(AnimeRequestDTO req) {
        if (animeRepository.existsByNameIgnoreCase(req.name())) {
            throw new DuplicateResourceException("Anime already exists with name: " + req.name());
        }

        Studio studio = null;
        Author author=null;

        if(req.studioId()!=null)
            studio = studioRepository.findByIdAndActiveTrue(req.studioId()).orElseThrow(() -> new ResourceNotFoundException("Studio not found with id: " + req.studioId()));

        if(req.authorId()!=null)
            author = authorRepository.findByIdAndActiveTrue(req.authorId()).orElseThrow(() -> new ResourceNotFoundException("Author not found with id: " + req.authorId()));

        Set<Genre> genres = new HashSet<>();
        if (req.genreIds() != null && !(req.genreIds().isEmpty())) {
            genres = req.genreIds().stream().map(g ->
                    genreRepository.findByIdAndActiveTrue(g).orElseThrow(() -> new ResourceNotFoundException("Genre not found with id: " + g))
            ).collect(Collectors.toSet());
        }

        Anime anime = AnimeMapper.toEntity(req, studio, author, genres);

        return AnimeMapper.toResponse(animeRepository.save(anime));
    }

    @Transactional
    public AnimeResponseDTO update(Long id, AnimeRequestDTO req){
        Anime existingAnime = animeRepository.findByIdAndActiveTrue(id).orElseThrow(()->new ResourceNotFoundException("Anime not found with id: "+id));

        boolean nameChanged = !existingAnime.getName().equalsIgnoreCase(req.name());
        boolean alreadyExists = animeRepository.existsByNameIgnoreCase(req.name());

        if(nameChanged && alreadyExists)
            throw new DuplicateResourceException("This anime name already exists");

        Studio studio = null;
        Author author=null;

        if(req.studioId()!=null)
            studio = studioRepository.findByIdAndActiveTrue(req.studioId()).orElseThrow(() -> new ResourceNotFoundException("Studio not found with id: " + req.studioId()));

        if(req.authorId()!=null)
            author = authorRepository.findByIdAndActiveTrue(req.authorId()).orElseThrow(() -> new ResourceNotFoundException("Author not found with id: " + req.authorId()));

        Set<Genre> genres = new HashSet<>();
        if (req.genreIds() != null && !(req.genreIds().isEmpty())) {
            genres = req.genreIds().stream().map(g ->
                    genreRepository.findByIdAndActiveTrue(g).orElseThrow(() -> new ResourceNotFoundException("Genre not found with id: " + g))
            ).collect(Collectors.toSet());
        }

        AnimeMapper.updateEntity(existingAnime,req,studio,author,genres);

        return AnimeMapper.toResponse(existingAnime);
    }

    @Transactional
    public AnimeResponseDTO delete(Long id) {
        Anime anime = animeRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("Anime not found with id: " + id));
        anime.setActive(false);
        return AnimeMapper.toResponse(anime);
    }

    @Transactional
    public AnimeResponseDTO enable(Long id) {
        Anime anime = animeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Anime not found with id: " + id));
        anime.setActive(true);
        return AnimeMapper.toResponse(anime);
    }
}