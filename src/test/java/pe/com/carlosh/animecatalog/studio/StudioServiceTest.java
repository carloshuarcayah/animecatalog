package pe.com.carlosh.animecatalog.studio;

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
import pe.com.carlosh.animecatalog.studio.dto.CreateStudioDTO;
import pe.com.carlosh.animecatalog.studio.dto.StudioResponseDTO;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudioServiceTest {

    @Mock
    private StudioRepository studioRepository;

    @InjectMocks
    private StudioService studioService;

    private Studio studio1;
    private Studio studio2;
    private CreateStudioDTO req;

    @BeforeEach
    void setUp() {
        studio1 = new Studio("Prueba1","Pais1",2000);
        studio2 = new Studio("Prueba2","Pais2",2000);
        studio1.setId(1L);
        studio2.setId(2L);
        req = new CreateStudioDTO("PruebaCreacion","PaisCreado",2000);
    }

    @Test
    @DisplayName("Should return a page with 2 studios")
    void findAllActives() {
        Pageable pageable = PageRequest.of(0,10);
        Page<Studio> page = new PageImpl<>(List.of(studio1,studio2));
        when(studioRepository.findByActiveTrue(pageable)).thenReturn(page);

        Page<StudioResponseDTO> result = studioService.findAllActives(pageable);

        assertNotNull(result);
        assertEquals(page.getTotalElements(),result.getTotalElements());
        assertEquals("Prueba1",result.getContent().getFirst().name());
        assertEquals("Prueba2",result.getContent().getLast().name());
        verify(studioRepository,times(1)).findByActiveTrue(pageable);
    }

    @Test
    @DisplayName("Should return an studio with id: 1")
    void findById() {
        Long idTest = 1L;
        when(studioRepository.findByIdAndActiveTrue(idTest)).thenReturn(Optional.of(studio1));
        StudioResponseDTO result = studioService.findById(idTest);
        assertNotNull(result);
        assertEquals(idTest,result.id());
        assertEquals(studio1.getName(),result.name());
        verify(studioRepository,times(1)).findByIdAndActiveTrue(idTest);
    }

    @Test
    @DisplayName("Should return a page with names that contains the string: Prueba")
    void findByNameContainingIgnoreCase() {
        String nameTest="Prueba";
        Pageable pageable = PageRequest.of(0,10);
        Page<Studio> page = new PageImpl<>(List.of(studio1,studio2));

        when(studioRepository.findByNameContainingIgnoreCaseAndActiveTrue(nameTest,pageable)).thenReturn(page);
        Page<StudioResponseDTO> result = studioService.findByNameContainingIgnoreCase(nameTest,pageable);
        assertNotNull(result);
        assertEquals(2,result.getTotalElements());
        assertEquals("Prueba1", result.getContent().getFirst().name());
        assertEquals("Prueba2", result.getContent().getLast().name());
        verify(studioRepository,times(1)).findByNameContainingIgnoreCaseAndActiveTrue(nameTest,pageable);
    }

    @Test
    @DisplayName("Should save an studio and return a response with name: PruebaCreacion")
    void create() {
        Long idCreatedTest= 33L;
        when(studioRepository.existsByNameIgnoreCase(req.name())).thenReturn(false);
        when(studioRepository.save(any(Studio.class))).thenAnswer(
                invocation -> {
                    Studio saved = invocation.getArgument(0);
                    saved.setId(idCreatedTest);
                    return saved;
                }
        );
        StudioResponseDTO result = studioService.create(req);
        assertNotNull(result);
        assertEquals(idCreatedTest,result.id());
        assertEquals("PruebaCreacion",result.name());
        verify(studioRepository,times(1)).save(any(Studio.class));
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