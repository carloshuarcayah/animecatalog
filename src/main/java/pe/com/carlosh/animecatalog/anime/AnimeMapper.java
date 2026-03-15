package pe.com.carlosh.animecatalog.anime;

import pe.com.carlosh.animecatalog.anime.dto.AnimeRequestDTO;
import pe.com.carlosh.animecatalog.anime.dto.AnimeResponseDTO;
import pe.com.carlosh.animecatalog.author.Author;
import pe.com.carlosh.animecatalog.genre.Genre;
import pe.com.carlosh.animecatalog.studio.Studio;
import java.util.Set;
import java.util.stream.Collectors;

public class AnimeMapper {

    public static Anime toEntity(AnimeRequestDTO req, Studio studio, Author author, Set<Genre> genres) {
        Anime anime = new Anime(
                req.name(),
                req.description(),
                req.episodes(),
                req.score(),
                req.status(),
                req.type(),
                req.releaseDate(),
                req.imageURL(),
                studio,
                author
        );
        anime.setGenres(genres);
        return anime;
    }

    public static AnimeResponseDTO toResponse(Anime anime) {
        return new AnimeResponseDTO(
                anime.getId(),
                anime.getName(),
                anime.getDescription(),
                anime.getEpisodes(),
                anime.getScore(),
                anime.getStatus().name(),
                anime.getType().name(),
                anime.getReleaseDate(),
                anime.getImageURL(),
                anime.getStudio() != null ? anime.getStudio().getName() : null,
                anime.getAuthor() != null ? anime.getAuthor().getFirstName() + " " + anime.getAuthor().getLastName() : null,
                anime.getGenres().stream().map(Genre::getName).collect(Collectors.toSet())
        );
    }

    public static void updateEntity(Anime anime, AnimeRequestDTO req, Studio studio, Author author, Set<Genre> genres) {
        anime.setName(req.name());
        anime.setDescription(req.description());
        anime.setEpisodes(req.episodes());
        anime.setScore(req.score());
        anime.setStatus(req.status());
        anime.setType(req.type());
        anime.setReleaseDate(req.releaseDate());
        anime.setImageURL(req.imageURL());
        anime.setStudio(studio);
        anime.setAuthor(author);
        anime.setGenres(genres);
    }
}
