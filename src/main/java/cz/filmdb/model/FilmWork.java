package cz.filmdb.model;

import jakarta.persistence.*;

import java.util.ArrayList;

@Table
@Entity(name = "film_work")
public class FilmWork {
    @Id
    @SequenceGenerator(
            name = "filmwork_sequence",
            sequenceName = "filmwork_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "filmwork_sequence"
    )
    public Long id;
    public String name;
    public float audienceScore;
    public float criticsScore;
    @OneToMany
    public ArrayList<Genre> genres;

    public FilmWork(Long id, String name, ArrayList<Genre> genres) {
        this.id = id;
        this.name = name;
        this.genres = genres;
    }

    public FilmWork() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getAudienceScore() {
        return audienceScore;
    }

    public void setAudienceScore(float audienceScore) {
        this.audienceScore = audienceScore;
    }

    public float getCriticsScore() {
        return criticsScore;
    }

    public void setCriticsScore(float criticsScore) {
        this.criticsScore = criticsScore;
    }

    public ArrayList<Genre> getGenres() {
        return genres;
    }

    public void setGenres(ArrayList<Genre> genres) {
        this.genres = genres;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
