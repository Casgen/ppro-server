package cz.filmdb.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import cz.filmdb.deserial.TVShowDeserializer;
import cz.filmdb.serial.TVShowSerializer;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Setter
@Getter
@Entity
@Table(name = "tvshow")
@JsonSerialize(using = TVShowSerializer.class)
@JsonDeserialize(using = TVShowDeserializer.class)
public class TVShow extends Filmwork {

    @Column(name = "number_of_seasons")
    private int numberOfSeasons;

    @Column(name = "running_from")
    private LocalDate runningFrom;

    @Column(name = "running_to")
    private LocalDate runningTo;


    public TVShow(String name, LocalDate runningFrom, LocalDate runningTo, Set<Genre> genres, int numberOfSeasons) {
        super(name, genres);
        this.numberOfSeasons = numberOfSeasons;
        this.runningFrom = runningFrom;
        this.runningTo = runningTo;
    }

    public TVShow() {
        super();
        this.numberOfSeasons = 0;
        this.runningFrom = LocalDate.of(LocalDate.MIN.getYear(), LocalDate.MIN.getMonth(), LocalDate.MIN.getDayOfMonth());
        this.runningTo = null;
    }

    public TVShow(Long id, String name, float audienceScore, float criticsScore, Set<Genre> genres,
                  Set<Occupation> occupations, Set<Review> reviews, int numberOfSeasons, LocalDate runningFrom, LocalDate runningTo) {
        super(id, name,audienceScore, criticsScore, genres,occupations,reviews, null);
        this.runningTo = runningTo;
        this.runningFrom = runningFrom;
        this.numberOfSeasons = numberOfSeasons;
    }

    public TVShow(Long id, String name, float audienceScore, float criticsScore, Set<Genre> genres,
                  Set<Occupation> occupations, Set<Review> reviews, int numberOfSeasons, LocalDate runningFrom,
                  LocalDate runningTo, Set<FilmworkImage> images) {
        super(id, name,audienceScore, criticsScore, genres,occupations,reviews, images);
        this.runningTo = runningTo;
        this.runningFrom = runningFrom;
        this.numberOfSeasons = numberOfSeasons;
    }
}
