package pe.com.carlosh.animecatalog.genre;

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
import pe.com.carlosh.animecatalog.genre.dto.GenreRequestDTO;
import pe.com.carlosh.animecatalog.genre.dto.GenreResponseDTO;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GenreServiceTest {

    @Mock
    private GenreRepository genreRepository;

    @InjectMocks
    private GenreService genreService;

    private Genre genre1;
    private Genre genre2;
    private GenreRequestDTO req;

    @BeforeEach
    void setUp() {
        genre1 = new Genre("Action", "Action anime");
        genre1.setId(1L);

        genre2 = new Genre("Comedy", "Comedy anime");
        genre2.setId(2L);

        req = new GenreRequestDTO("Drama", "Drama anime");
    }

    @Test
    @DisplayName("Should return a page with 2 genres")
    void findAllActives() {
        Pageable pageable = PageRequest.of(0,10);
        Page<Genre> page = new PageImpl<>(List.of(genre1,genre2));
        when(genreRepository.findByActiveTrue(pageable)).thenReturn(page);

        Page<GenreResponseDTO> result = genreService.findAllActives(pageable);

        assertNotNull(result);
        assertEquals(2,result.getTotalElements());
        verify(genreRepository,times(1)).findByActiveTrue(pageable);
    }

    @Test
    @DisplayName("Should return a genre with id: 1")
    void findById() {
        Long idTest = 1L;
        when(genreRepository.findByIdAndActiveTrue(idTest)).thenReturn(Optional.of(genre1));

        GenreResponseDTO result = genreService.findById(idTest);

        assertNotNull(result);
        assertEquals(idTest, result.id());
        assertEquals("Action", result.name());
        verify(genreRepository, times(1)).findByIdAndActiveTrue(idTest);
    }

    @Test
    @DisplayName("Should return genres containing the string: Action")
    void searchByName() {
        String nameTest = "Action";
        Pageable pageable = PageRequest.of(0, 10);
        Page<Genre> page = new PageImpl<>(List.of(genre1));
        when(genreRepository.findByNameContainingIgnoreCase(nameTest, pageable)).thenReturn(page);

        Page<GenreResponseDTO> result = genreService.searchByName(nameTest, pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        verify(genreRepository, times(1)).findByNameContainingIgnoreCase(nameTest, pageable);
    }

    @Test
    @DisplayName("Should create a new genre")
    void create() {
        Long idTest = 3L;
        when(genreRepository.existsByNameIgnoreCase(req.name())).thenReturn(false);
        when(genreRepository.save(any(Genre.class))).thenAnswer(invocation -> {
            Genre saved = invocation.getArgument(0);
            saved.setId(idTest);
            return saved;
        });

        GenreResponseDTO result = genreService.create(req);

        assertNotNull(result);
        assertEquals(idTest, result.id());
        assertEquals("Drama", result.name());
        verify(genreRepository, times(1)).save(any(Genre.class));
    }

    @Test
    @DisplayName("Should update genre name")
    void update() {
        Long idTest = 1L;
        String newName = "Horror";
        GenreRequestDTO updateReq = new GenreRequestDTO(newName, "Horror anime");
        when(genreRepository.findByIdAndActiveTrue(idTest)).thenReturn(Optional.of(genre1));
        when(genreRepository.existsByNameIgnoreCase(newName)).thenReturn(false);

        GenreResponseDTO result = genreService.update(idTest, updateReq);

        assertEquals(newName, result.name());
        verify(genreRepository, never()).save(any(Genre.class));
    }

    @Test
    @DisplayName("Should soft delete a genre")
    void delete() {
        Long idTest = 1L;
        when(genreRepository.findByIdAndActiveTrue(idTest)).thenReturn(Optional.of(genre1));

        GenreResponseDTO result = genreService.delete(idTest);

        assertFalse(genre1.getActive());
        assertEquals(1L, result.id());
        verify(genreRepository, never()).save(any(Genre.class));
    }

    @Test
    @DisplayName("Should enable an inactive genre")
    void enable() {
        Long idTest = 1L;
        genre1.setActive(false);
        when(genreRepository.findById(idTest)).thenReturn(Optional.of(genre1));

        GenreResponseDTO result = genreService.enable(idTest);

        assertTrue(genre1.getActive());
        verify(genreRepository, never()).save(any(Genre.class));
    }
}