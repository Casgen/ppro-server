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

    @Column(name = "running_to")
    private Date runningTo;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "tvshow_creator",
            joinColumns = {
                    @JoinColumn(name = "tvshow_id"),
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "creator_id")
            }
    )
    @JsonManagedReference
    private Set<Person> creators;

    public TVShow(String name, Set<Genre> genres, Set<Person> creators, int numberOfSeasons, Date runningFrom) {
        super(name, genres);
        this.numberOfSeasons = numberOfSeasons;
        this.runningFrom = runningFrom;
        this.creators = creators;
    }

    public TVShow(String name, int numberOfSeasons, Set<Person> creators) {
        super();
        this.numberOfSeasons = numberOfSeasons;
        this.runningFrom = runningFrom;
        this.creators = creators;
    }


    public TVShow() {

    }


    public int getNumberOfSeasons() {
        return numberOfSeasons;
    }

    public void setNumberOfSeasons(int numberOfSeasons) {
        this.numberOfSeasons = numberOfSeasons;
    }

    public Date getRunningFrom() {
        return runningFrom;
    }

    public void setRunningFrom(Date runningFrom) {
        this.runningFrom = runningFrom;
    }

    public Date getRunningTo() {
        return runningTo;
    }

    public void setRunningTo(Date runningTo) {
        this.runningTo = runningTo;
    }

    public Set<Person> getCreators() {
        return creators;
    }

    public void setCreators(Set<Person> creators) {
        this.creators = creators;
    }

}
