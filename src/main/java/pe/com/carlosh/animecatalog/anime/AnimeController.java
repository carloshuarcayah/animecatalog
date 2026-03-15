package pe.com.carlosh.animecatalog.anime;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.com.carlosh.animecatalog.anime.dto.AnimeRequestDTO;
import pe.com.carlosh.animecatalog.anime.dto.AnimeResponseDTO;

@RestController
@RequestMapping("/api/animes")
@RequiredArgsConstructor
public class AnimeController {
    private final AnimeService animeService;

    @GetMapping
    public Page<AnimeResponseDTO> findAllActives(Pageable pageable) {
        return animeService.findAllActives(pageable);
    }

    @GetMapping("/{id}")
    public AnimeResponseDTO findById(@PathVariable Long id) {
        return animeService.findById(id);
    }

    @GetMapping("/search")
    public Page<AnimeResponseDTO> search(@RequestParam String name, Pageable pageable) {
        return animeService.searchByName(name, pageable);
    }

    @PostMapping
    public ResponseEntity<AnimeResponseDTO> create(@Valid @RequestBody AnimeRequestDTO req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(animeService.create(req));
    }

    @PutMapping("/{id}")
    public AnimeResponseDTO update(@PathVariable Long id, @Valid @RequestBody AnimeRequestDTO req) {
        return animeService.update(id, req);
    }

    @DeleteMapping("/{id}")
    public AnimeResponseDTO delete(@PathVariable Long id) {
        return animeService.delete(id);
    }

    @PatchMapping("/{id}/enable")
    public AnimeResponseDTO enable(@PathVariable Long id) {
        return animeService.enable(id);
    }
}