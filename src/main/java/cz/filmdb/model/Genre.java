package cz.filmdb.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import cz.filmdb.deserial.GenreDeserializer;
import cz.filmdb.serial.GenreSerializer;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

// WATCH OUT FOR DUPLICATE IMPORTS OR IMPORTS LIKE THESE, THEY CAN OVERRIDE YOUR BEANS!
// import org.springframework.data.relational.core.mapping.Table;

@Setter
@Getter
@Table
@Entity
@JsonSerialize(using = GenreSerializer.class)
@JsonDeserialize(using = GenreDeserializer.class)
public class Genre {

    @Id
    @SequenceGenerator(
            name = "genre_sequence",
            sequenceName = "genre_sequence"
    )
    @GeneratedValue(
            strategy = GenerationType.AUTO,
            generator = "genre_sequence"
    )
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "genres", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private Set<Filmwork> filmworks;

    public Genre(String name) {
        this.name = name;
    }

    public Genre(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Genre(Long id, String name, Set<Filmwork> filmworks) {
        this.id = id;
        this.name = name;
        this.filmworks = filmworks;
    }

    public Genre() {

    }

    public Genre(Genre newGenre) {
        this.id = newGenre.id;
        this.name = newGenre.name;
        this.filmworks = newGenre.filmworks;
    }

    @Override
    public String toString() {
        return "Genre{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public void addFilmwork(Filmwork filmwork) {
        this.filmworks.add(filmwork);
        filmwork.getGenres().add(this);
    }

    public void removeFilmwork(Filmwork filmwork) {
        this.filmworks.remove(filmwork);
        filmwork.getGenres().remove(this);
    }
}
