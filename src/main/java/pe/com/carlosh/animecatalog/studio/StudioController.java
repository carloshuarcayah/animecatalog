package pe.com.carlosh.animecatalog.studio;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.com.carlosh.animecatalog.studio.dto.CreateStudioDTO;
import pe.com.carlosh.animecatalog.studio.dto.StudioResponseDTO;

@RestController
@RequestMapping("/api/studios")
@RequiredArgsConstructor
public class StudioController {

    private final StudioService studioService;

    @GetMapping
    public Page<StudioResponseDTO> findAll(Pageable pageable) {
        return studioService.findAllActives(pageable);
    }

    @GetMapping("/{id}")
    public StudioResponseDTO findById(@PathVariable Long id) {
        return studioService.findById(id);
    }

    @GetMapping("/search")
    public Page<StudioResponseDTO> search(
            @RequestParam String name,
            Pageable pageable) {
        return studioService.findByNameContainingIgnoreCase(name, pageable);
    }

    @PostMapping
    public ResponseEntity<StudioResponseDTO> create(@Valid @RequestBody CreateStudioDTO request) {
        StudioResponseDTO response = studioService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public StudioResponseDTO update(
            @PathVariable Long id,
            @Valid @RequestBody CreateStudioDTO request) {
        return studioService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public StudioResponseDTO delete(@PathVariable Long id) {
        return studioService.delete(id);
    }

    @PatchMapping("/{id}/enable")
    public StudioResponseDTO enable(@PathVariable Long id) {
        return studioService.enable(id);
    }
}