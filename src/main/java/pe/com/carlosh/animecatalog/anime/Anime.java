package pe.com.carlosh.animecatalog.anime;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pe.com.carlosh.animecatalog.author.Author;
import pe.com.carlosh.animecatalog.genre.Genre;
import pe.com.carlosh.animecatalog.studio.Studio;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "animes")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class Anime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 255)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    private Integer episodes;

    //MAX IS 100.00
    @Column(precision = 5, scale = 2)
    private BigDecimal score;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private AnimeStatus status;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private AnimeType type;

    private LocalDate releaseDate;

    @Column(length = 300)
    private String imageURL;

    @ManyToOne
    @JoinColumn(name = "studio_id")
    private Studio studio;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @ManyToMany
    @JoinTable(name = "anime_genres")
    private Set<Genre> genres = new HashSet<>();

    @Column(nullable = false)
    private Boolean active;

    public Anime(String name,
                 String description,
                 Integer episodes,
                 BigDecimal score,
                 AnimeStatus status,
                 AnimeType type,
                 LocalDate releaseDate,
                 String imageURL,
                 Studio studio,
                 Author author) {
        this.name = name;
        this.description = description;
        this.episodes = episodes;
        this.score = score;
        this.status = status;
        this.type = type;
        this.releaseDate = releaseDate;
        this.imageURL = imageURL;
        this.studio = studio;
        this.author = author;
        this.active=true;
    }
    @PrePersist
    private void onCreate(){
        LocalDateTime now = LocalDateTime.now();

        createdAt=updatedAt=now;
    }

    @PreUpdate
    private void onUpdate(){
        updatedAt=LocalDateTime.now();
    }

}
