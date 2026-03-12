package pe.com.carlosh.animecatalog.genre.dto;


import jakarta.validation.constraints.NotNull;

public record GenreRequestDTO(
        @NotNull
        String name,
        String description
) {
}
