package cz.filmdb.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import cz.filmdb.deserial.MovieDeserializer;
import cz.filmdb.serial.MovieSerializer;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.*;

@Getter
@Setter
@Entity
@JsonSerialize(using = MovieSerializer.class)
@JsonDeserialize(using = MovieDeserializer.class)
public class Movie extends Filmwork {

    @Column(nullable = false, name = "release_date")
    public LocalDate releaseDate;

    public Movie(String name, LocalDate releaseDate, Set<Genre> genres) {
        super(name, genres);
        this.releaseDate = releaseDate;
    }

    public Movie() {}

    public Movie(Long id, String name, float audienceScore, float criticsScore, Set<Genre> genres,
                 Set<Occupation> occupations, Set<Review> reviews, LocalDate releaseDate, String image) {
        super(id, name, audienceScore, criticsScore, genres, occupations, reviews, image);
        this.releaseDate = releaseDate;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "releaseDate=" + releaseDate +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", audienceScore=" + audienceScore +
                ", criticsScore=" + criticsScore +
                ", genres=" + genres +
                ", cast=" + occupations +
                '}';
    }
}
