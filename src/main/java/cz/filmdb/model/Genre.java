package cz.filmdb.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import cz.filmdb.serial.FilmworksSerializer;
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
    public Long id;

    public String name;

    @ManyToMany(mappedBy = "genres", fetch = FetchType.LAZY)
    @JsonSerialize(using = FilmworksSerializer.class)
    public Set<Filmwork> filmworks;

    public Genre(String name) {
        this.name = name;
    }

    public Genre(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Genre() {

    }

    @Override
    public String toString() {
        return "Genre{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
