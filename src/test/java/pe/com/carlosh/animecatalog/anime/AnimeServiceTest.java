package pe.com.carlosh.animecatalog.anime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import pe.com.carlosh.animecatalog.anime.dto.AnimeRequestDTO;
import pe.com.carlosh.animecatalog.anime.dto.AnimeResponseDTO;
import pe.com.carlosh.animecatalog.author.Author;
import pe.com.carlosh.animecatalog.author.AuthorRepository;
import pe.com.carlosh.animecatalog.genre.Genre;
import pe.com.carlosh.animecatalog.genre.GenreRepository;
import pe.com.carlosh.animecatalog.studio.Studio;
import pe.com.carlosh.animecatalog.studio.StudioRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AnimeServiceTest {

    @Mock
    private AnimeRepository animeRepository;

    @Mock
    private StudioRepository studioRepository;

    @Mock
    private GenreRepository genreRepository;

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private AnimeService animeService;

    private Anime anime1;
    private Studio studio1;
    private Author author1;
    private Genre genre1;
    private AnimeRequestDTO req;
    @BeforeEach
    void setUp() {
        String animeURL="https://www.imdb.com/es-es/title/tt0409591/";

        studio1 = new Studio("Prueba1","Pais1",2000);
        studio1.setId(1L);

        author1 = new Author("Hajime", "Isayama",
                LocalDate.of(1986, 8, 29), "Japanese");
        author1.setId(1L);

        genre1 = new Genre("Action", "Action anime");
        genre1.setId(1L);


        anime1= new Anime("Naruto",
                "Anime de ninjas",200,
                new BigDecimal("8.2"),
                AnimeStatus.COMPLETED,
                AnimeType.TV,
                LocalDate.of(2007,10,9),animeURL,studio1,author1);
        anime1.setId(1L);
        anime1.setGenres(Set.of(genre1));

        req = new AnimeRequestDTO(
                "Attack on Titan",
                "Anime de titanes",
                87,
                new BigDecimal("9.5"),
                AnimeStatus.COMPLETED,
                AnimeType.TV,
                LocalDate.of(2013, 4, 7),
                animeURL,
                1L,
                1L,
                Set.of(1L)
        );
    }

    @Test
    @DisplayName("Should return a page with actives animes")
    void findAllActives() {
        Pageable pageable = PageRequest.of(0,10);
        Page<Anime> page = new PageImpl<>(List.of(anime1));
        when(animeRepository.findByActiveTrue(pageable)).thenReturn(page);
        Page<AnimeResponseDTO> result = animeService.findAllActives(pageable);

        assertNotNull(result);
        assertEquals(1,result.getTotalElements());
        assertEquals(1L,result.getContent().getFirst().id());
        verify(animeRepository,times(1)).findByActiveTrue(pageable);
    }

    @Test
    @DisplayName("Should return an AnimeResponseDTO with id: 1L")
    void findById() {
        Long idTested = 1L;
        when(animeRepository.findById(idTested)).thenReturn(Optional.of(anime1));
        AnimeResponseDTO result = animeService.findById(idTested);

        assertNotNull(result);
        assertEquals(idTested, result.id());
        verify(animeRepository,times(1)).findById(idTested);
    }

    @Test
    @DisplayName("Should return an anime that contains the string: Naru")
    void searchByName() {
        String name = "Naru";
        Pageable pageable = PageRequest.of(0,10);
        Page<Anime> page = new PageImpl<>(List.of(anime1));
        when(animeRepository.findByNameContainingIgnoreCaseAndActiveTrue(name, pageable)).thenReturn(page);
        Page<AnimeResponseDTO> result = animeService.searchByName(name,pageable);

        assertNotNull(result);
        assertEquals(1,result.getTotalElements());
        assertEquals("Naruto",result.getContent().getFirst().name());
        verify(animeRepository,times(1)).findByNameContainingIgnoreCaseAndActiveTrue(name,pageable);
    }

    @Test
    @DisplayName("Should create an anime")
    void create() {
        Long studioIdTested=1L;
        Long authorIdTested=1L;
        Long genreIdTested=1L;
        Long animeIdTested = 1L;
        String authorName= author1.getFirstName()+" "+author1.getLastName();

        when(animeRepository.existsByNameIgnoreCase(req.name())).thenReturn(false);
        when(studioRepository.findByIdAndActiveTrue(studioIdTested)).thenReturn(Optional.of(studio1));
        when(authorRepository.findByIdAndActiveTrue(authorIdTested)).thenReturn(Optional.of(author1));
        when(genreRepository.findByIdAndActiveTrue(genreIdTested)).thenReturn(Optional.of(genre1));

        when(animeRepository.save(any(Anime.class))).thenAnswer(invocation -> {
            Anime animeSaved = invocation.getArgument(0);
            animeSaved.setId(animeIdTested);
            return animeSaved;
        });

        AnimeResponseDTO result = animeService.create(req);

        assertNotNull(result);
        assertEquals(animeIdTested,result.id());
        assertEquals(1, (long) result.genres().size());
        assertEquals("Prueba1",result.studioName());
        assertEquals(authorName,result.authorName());
        verify(animeRepository,times(1)).save(any(Anime.class));
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }

    @Test
    void enable() {
    }
}