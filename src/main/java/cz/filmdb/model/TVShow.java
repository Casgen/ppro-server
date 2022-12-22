package cz.filmdb.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.util.Set;

@Setter
@Getter
@Entity
public class TVShow extends Filmwork {

    @Column(name = "number_of_seasons")
    private int numberOfSeasons;

    @Column(name = "running_from")
    private Date runningFrom;

    @Column(name = "running_to", nullable = true)
    private Date runningTo;


    public TVShow(String name, Date runningFrom, Set<Genre> genres, int numberOfSeasons) {
        super(name, genres);
        this.numberOfSeasons = numberOfSeasons;
        this.runningFrom = runningFrom;
    }

    public TVShow(String name, int numberOfSeasons, Set<Person> creators) {
        super(name);
        this.numberOfSeasons = numberOfSeasons;
        this.runningFrom = null;
    }

    public TVShow() {
        super();
        this.numberOfSeasons = 0;
        this.runningFrom = new Date(0);
        this.runningTo = null;
    }
}
