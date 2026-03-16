package pe.com.carlosh.animecatalog.genre.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record GenreRequestDTO(
        @NotBlank
        String name,
        String description
) {
}
