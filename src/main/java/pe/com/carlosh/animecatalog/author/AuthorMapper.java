package pe.com.carlosh.animecatalog.author;

import pe.com.carlosh.animecatalog.author.dto.AuthorRequestDTO;
import pe.com.carlosh.animecatalog.author.dto.AuthorResponseDTO;

import java.time.LocalDateTime;

public class AuthorMapper {

    public static AuthorResponseDTO toResponse(Author author) {
        return new AuthorResponseDTO(
                author.getId(),
                author.getFirstName(),
                author.getLastName(),
                author.getBirthDate(),
                author.getNationality()
        );
    }

    public static Author toEntity(AuthorRequestDTO req) {
        return new Author(
                req.firstName(),
                req.lastName(),
                req.birthDate(),
                req.nationality()
        );
    }

    public static void updateEntity(Author author,AuthorRequestDTO req){
        author.setFirstName(req.firstName());
        author.setLastName(req.lastName());
        author.setBirthDate(req.birthDate());
        author.setNationality(req.nationality());
    }

}