package cz.filmdb.model;

import jakarta.persistence.*;

import java.sql.Date;
import java.util.ArrayList;

@Entity
@Table(name = "tv_show")
public class TVShow {

    @Id
    @SequenceGenerator(
            sequenceName = "tv_show_sequence",
            name = "tv_show_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.AUTO,
            generator = "tv_show_sequence"
    )
    private Long id;
    private int numberOfSeasons;
    private Date runningFrom;
    private Date runningTo;

    public TVShow(Long id, int numberOfSeasons, Date runningFrom, ArrayList<Person> creators) {
        this.id = id;
        this.numberOfSeasons = numberOfSeasons;
        this.runningFrom = runningFrom;
        this.creators = creators;
    }

    public TVShow(int numberOfSeasons, Date runningFrom, ArrayList<Person> creators) {
        this.numberOfSeasons = numberOfSeasons;
        this.runningFrom = runningFrom;
        this.creators = creators;
    }

    @ManyToMany
    private ArrayList<Person> creators;

    public TVShow() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public ArrayList<Person> getCreators() {
        return creators;
    }

    public void setCreators(ArrayList<Person> creators) {
        this.creators = creators;
    }

}
