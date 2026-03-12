package pe.com.carlosh.animecatalog.author;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.com.carlosh.animecatalog.author.dto.AuthorRequestDTO;
import pe.com.carlosh.animecatalog.author.dto.AuthorResponseDTO;

@RestController
@RequestMapping("/api/authors")
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    @GetMapping
    public Page<AuthorResponseDTO> findAll(Pageable pageable) {
        return authorService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public AuthorResponseDTO findById(@PathVariable Long id) {
        return authorService.findById(id);
    }

    @GetMapping("/search")
    public Page<AuthorResponseDTO> search(@RequestParam String name, Pageable pageable) {
        return authorService.searchByName(name, pageable);
    }

    @PostMapping
    public ResponseEntity<AuthorResponseDTO> create(@Valid @RequestBody AuthorRequestDTO request) {
        AuthorResponseDTO response = authorService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public AuthorResponseDTO update(@PathVariable Long id, @Valid @RequestBody AuthorRequestDTO request) {
        return authorService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public AuthorResponseDTO delete(@PathVariable Long id) {
        return authorService.delete(id);
    }

    @PatchMapping("/{id}/enable")
    public AuthorResponseDTO enable(@PathVariable Long id) {
        return authorService.enable(id);
    }
}