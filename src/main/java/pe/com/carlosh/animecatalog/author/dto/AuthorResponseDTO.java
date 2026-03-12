package pe.com.carlosh.animecatalog.author.dto;

import java.time.LocalDate;

public record AuthorResponseDTO(
        Long id,
        String firstName,
        String lastName,
        LocalDate birthDate,
        String nationality
) {}