package pe.com.carlosh.animecatalog.studio.dto;


public record StudioResponseDTO(
        Long id,
        String name,
        String country,
        Integer creationYear
){
}
