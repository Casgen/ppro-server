package cz.filmdb.model;

import cz.filmdb.enums.RoleType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.*;

@Getter
@Setter
@Entity
public class Movie extends Filmwork {

    @Column(nullable = false, name = "release_date")
    public LocalDate releaseDate;

    public Movie(String name, LocalDate releaseDate, Set<Genre> genres) {
        super(name, genres);
        this.releaseDate = releaseDate;
    }

    public Movie(String name, LocalDate releaseDate, Set<Genre> genres, Set<Occupation> occupations) {
        super(name, genres, occupations);
        this.releaseDate = releaseDate;
    }

    public Movie() {}


    @Override
    public String toString() {
        return "Movie{" +
                "releaseDate=" + releaseDate +
                ", fid=" + fid +
                ", name='" + name + '\'' +
                ", audienceScore=" + audienceScore +
                ", criticsScore=" + criticsScore +
                ", genres=" + genres +
                ", cast=" + occupations +
                '}';
    }
}
