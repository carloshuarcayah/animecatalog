package pe.com.carlosh.animecatalog.author;

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
import pe.com.carlosh.animecatalog.author.dto.AuthorResponseDTO;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthorServiceTest {
    @Mock
    AuthorRepository authorRepository;

    @InjectMocks
    AuthorService authorService;

    private Author author1;
    private Author author2;

    @BeforeEach
    void setUp() {
        author1 = new Author("Hajime", "Isayama",
                LocalDate.of(1986, 8, 29), "Japanese");
        author1.setId(1L);

        author2 = new Author("Eiichiro", "Oda",
                LocalDate.of(1975, 1, 1), "Japanese");
        author2.setId(2L);

    }

    @Test
    @DisplayName("Should return a page with 2 authors")
    void findAll() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Author> page = new PageImpl<>(List.of(author1,author2));
        when(authorRepository.findByActiveTrue(pageable)).thenReturn(page);

        Page<AuthorResponseDTO>result= authorService.findAll(pageable);

        assertEquals(2,result.getTotalElements());
        verify(authorRepository,times(1)).findByActiveTrue(pageable);
    }

    @Test
    void findById() {
    }

    @Test
    void searchByName() {
    }

    @Test
    void create() {
    }

    @Test
    void update() {
    }

    @Test
    void enable() {
    }

    @Test
    void delete() {
    }
}