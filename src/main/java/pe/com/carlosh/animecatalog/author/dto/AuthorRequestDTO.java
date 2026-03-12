package pe.com.carlosh.animecatalog.author.dto;

import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;

public record AuthorRequestDTO(
        @NotBlank
        String firstName,
        @NotBlank
        String lastName,
        LocalDate birthDate,
        String nationality
) {}