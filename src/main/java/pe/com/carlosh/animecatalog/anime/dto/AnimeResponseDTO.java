package pe.com.carlosh.animecatalog.anime.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

public record AnimeResponseDTO(
        Long id,
        String name,
        String description,
        Integer episodes,
        BigDecimal score,
        String status,
        String type,
        LocalDate releaseDate,
        String imageURL,
        String studioName,
        String authorName,
        Set<String> genres
) {}
