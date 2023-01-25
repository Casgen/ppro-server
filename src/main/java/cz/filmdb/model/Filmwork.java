package cz.filmdb.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import cz.filmdb.enums.RoleType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Setter
@Getter
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "filmwork")
public class Filmwork {

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
    protected Long id;

    @Column(nullable = false)
    protected String name;

    protected String description;

    @Transient
    protected transient float audienceScore;

    @Column(name = "critics_score")
    protected float criticsScore;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(name = "filmwork_genre",
            joinColumns = {
                    @JoinColumn(name = "filmwork_id"),
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "genre_id")
            }
    )
    protected Set<Genre> genres;

    @OneToMany(mappedBy = "filmwork", cascade = CascadeType.ALL, orphanRemoval = true)
    protected Set<Occupation> occupations;

    @OneToMany(mappedBy = "filmwork", cascade = CascadeType.ALL)
    protected Set<Review> reviews;


    // Users which will watch, wont watch, have watched or is watching some movies or tv shows.

    @ManyToMany(mappedBy = "plansToWatch", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JsonBackReference("users-plans-to-watch-ref")
    protected Set<User> usersPlanToWatch;

    @ManyToMany(mappedBy = "hasWatched", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JsonBackReference("users-watched-ref")
    protected Set<User> usersHaveWatched;

    @ManyToMany(mappedBy = "isWatching", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JsonBackReference("users-watching-ref")
    protected Set<User> usersWatching;

    @ManyToMany(mappedBy = "wontWatch", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JsonBackReference("users-wont-watch-ref")
    protected Set<User> usersWontWatch;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    protected Set<FilmworkImage> imgPaths;

    public Filmwork(String name, Set<Genre> genres) {
        this.name = name;
        this.genres = genres;
        this.occupations = Set.of();
        this.criticsScore = 0.f;
    }

    public Filmwork(Long id, String name) {
        this.id = id;
        this.name = name;
        this.genres = Set.of();
        this.occupations = Set.of();
        this.criticsScore = 0.f;
        this.reviews = Set.of();
    }

    public Filmwork(Long id, String name, float audienceScore, float criticsScore, Set<Genre> genres,
                    Set<Occupation> occupations, Set<Review> reviews) {
        this(id, name);
        this.audienceScore = audienceScore;
        this.criticsScore = criticsScore;
        this.genres = genres;
        this.occupations = occupations;
        this.reviews = reviews;
    }

    public Filmwork(String name, Set<Genre> genres, Set<Occupation> occupations) {
        this.name = name;
        this.genres = genres;
        this.occupations = occupations;
        this.criticsScore = 0.f;
    }

    public Filmwork(String name) {
        this.name = name;
        this.genres = Set.of();
        this.occupations = Set.of();
        this.criticsScore = 0.f;
    }

    public Filmwork(Long id, String name, Set<Genre> genres) {
        this.id = id;
        this.name = name;
        this.genres = genres;
        this.criticsScore = 0.f;
    }

    public Filmwork() {

    }

    public float getAudienceScore() {

        if (reviews == null || reviews.isEmpty()) return 0.f;

        List<Review> reviewList = new ArrayList<>(reviews);
        reviewList.sort(new SortByAudienceScore());

        float middleIndex = reviewList.size() / 2.f;

        if ((reviewList.size() % 2) != 0)
            return reviewList.get((int) middleIndex).getScore();

        return (reviewList.get((int) middleIndex).getScore() + reviewList.get((int) middleIndex - 1).getScore()) / 2.f;

    }

    public void setOccupations(Map<Person, List<RoleType>> map) {
        this.occupations = Occupation.of(this, map);
    }

    public void addGenre(Genre genre) {
        this.getGenres().add(genre);
        genre.getFilmworks().add(this);
    }

    public void removeGenre(Genre genre) {
        this.getGenres().remove(genre);
        genre.getFilmworks().remove(this);
    }

    public void addToUsersPlanToWatch(User user) {
        this.usersPlanToWatch.add(user);
        user.getPlansToWatch().add(this);
    }

    public void addToUsersWontWatch(User user) {
        this.usersWontWatch.add(user);
        user.getWontWatch().add(this);
    }

    public void addToUsersHaveWatched(User user) {
        this.usersHaveWatched.add(user);
        user.getHasWatched().add(this);
    }

    public void addToUsersWatching(User user) {
        this.usersWatching.add(user);
        user.getIsWatching().add(this);
    }

    @Override
    public String toString() {
        return "FilmWork{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", audienceScore=" + audienceScore +
                ", criticsScore=" + criticsScore +
                ", genres=" + genres +
                '}';
    }

}

class SortByAudienceScore implements Comparator<Review> {
    @Override
    public int compare(Review o1, Review o2) {
        return Float.compare(o1.getScore(), o2.getScore());
    }
}

