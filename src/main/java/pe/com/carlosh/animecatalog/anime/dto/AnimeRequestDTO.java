package pe.com.carlosh.animecatalog.anime.dto;

import jakarta.validation.constraints.*;
import pe.com.carlosh.animecatalog.anime.AnimeStatus;
import pe.com.carlosh.animecatalog.anime.AnimeType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

public record AnimeRequestDTO(
        @NotBlank
        String name,

        String description,

        @Min(1)
        Integer episodes,

        @DecimalMin("0.00")
        @DecimalMax("10.00")
        BigDecimal score,

        @NotNull
        AnimeStatus status,

        @NotNull
        AnimeType type,

        LocalDate releaseDate,

        String imageURL,

        Long studioId,

        Long authorId,

        Set<Long> genreIds
) {}