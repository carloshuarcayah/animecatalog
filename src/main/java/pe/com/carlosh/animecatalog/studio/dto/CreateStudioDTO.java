package pe.com.carlosh.animecatalog.studio.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateStudioDTO(
        @NotBlank
        String name,
        String country,
        Integer yearCreation
){
}