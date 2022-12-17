package cz.filmdb.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Entity
public class Movie extends Filmwork {

    @Column(nullable = false, name = "year_of_release")
    public int yearOfRelease;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "movie_director",
            joinColumns = {
                    @JoinColumn(name = "movie_id", referencedColumnName = "fid"),
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "director_id", referencedColumnName = "id")
            }
    )
    public Set<Person> directors;

    public Movie(String name, int yearOfRelease, Set<Genre> genres) {
        super(name, genres);
        this.yearOfRelease = yearOfRelease;
    }

    public Movie(String name, int yearOfRelease, Set<Genre> genres, Set<Person> directors) {
        super(name, genres);
        this.yearOfRelease = yearOfRelease;
        this.directors = directors;
    }

    public Movie() {

    }

    @Override
    public String toString() {
        return "Movie{" +
                "yearOfRelease=" + yearOfRelease +
                ", directors=" + directors +
                ", id=" + fid +
                ", name='" + name + '\'' +
                ", audienceScore=" + audienceScore +
                ", criticsScore=" + criticsScore +
                ", genres=" + genres +
                '}';
    }
}
