package pe.com.carlosh.animecatalog.genre;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.com.carlosh.animecatalog.genre.dto.GenreRequestDTO;
import pe.com.carlosh.animecatalog.genre.dto.GenreResponseDTO;


@RestController
@RequestMapping("/api/genres")
@RequiredArgsConstructor
public class GenreController {
    private final GenreService genreService;

    @GetMapping
    public Page<GenreResponseDTO> findAllActives(Pageable pageable){
        return genreService.findAllActives(pageable);
    }

    @GetMapping("/{id}")
    public GenreResponseDTO findById(@PathVariable Long id){
        return genreService.findById(id);
    }

    @GetMapping("/search")
    public Page<GenreResponseDTO> search(@RequestParam String name, Pageable pageable){
        return genreService.searchByName(name,pageable);
    }

    @PostMapping
    public ResponseEntity<GenreResponseDTO> create(@Valid @RequestBody GenreRequestDTO req){
        return ResponseEntity.status(HttpStatus.CREATED).body(genreService.create(req));
    }

    @PutMapping("/{id}")
    public GenreResponseDTO update(@PathVariable Long id,@Valid @RequestBody GenreRequestDTO req){
        return genreService.update(id,req);
    }

    @DeleteMapping("/{id}")
    public GenreResponseDTO delete(@PathVariable Long id){
        return genreService.delete(id);
    }

    @PatchMapping("/{id}/enable")
    public GenreResponseDTO enable(@PathVariable Long id){
        return genreService.enable(id);
    }
}
