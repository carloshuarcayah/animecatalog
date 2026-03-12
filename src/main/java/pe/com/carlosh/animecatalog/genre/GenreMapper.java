package pe.com.carlosh.animecatalog.genre;

import pe.com.carlosh.animecatalog.genre.dto.GenreRequestDTO;
import pe.com.carlosh.animecatalog.genre.dto.GenreResponseDTO;

public class GenreMapper {
    public static Genre toEntity(GenreRequestDTO req){
        return new Genre(req.name(), req.description());
    }

    public static GenreResponseDTO toResponse(Genre genre){
        return new GenreResponseDTO(genre.getId(), genre.getName(), genre.getDescription());
    }

    public static void updateGenre(Genre genre,GenreRequestDTO req){
        genre.setName(req.name());
        genre.setDescription(req.description());
    }
}
