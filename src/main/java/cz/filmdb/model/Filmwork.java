package cz.filmdb.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    protected Long fid;

    @Column(nullable = false)
    protected String name;

    @Transient
    protected transient float audienceScore;

    @Column(name = "critics_score", nullable = true)
    protected float criticsScore;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "filmwork_genre",
            joinColumns = {
                    @JoinColumn(name = "filmwork_id"),
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "genre_id")
            }
    )
    @JsonManagedReference
    protected Set<Genre> genres;

    @OneToMany(mappedBy = "filmwork", cascade = CascadeType.ALL)
    @JsonManagedReference
    protected Set<Occupation> occupation;

    @OneToMany(mappedBy = "filmwork", cascade = CascadeType.ALL)
    @JsonManagedReference
    protected Set<Review> reviews;

    public Filmwork(String name, Set<Genre> genres) {
        this.name = name;
        this.genres = genres;
        this.occupation = Set.of();
        this.criticsScore = 0.f;
    }

    public Filmwork(String name) {
        this.name = name;
        this.genres = Set.of();
        this.occupation = Set.of();
        this.criticsScore = 0.f;
    }

    public Filmwork(Long fid, String name, Set<Genre> genres) {
        this.fid = fid;
        this.name = name;
        this.genres = genres;
        this.criticsScore = 0.f;
    }

    public Filmwork() {

    }

    public float getAudienceScore() {
        List<Review> reviewList = new ArrayList<>(reviews);
        reviewList.sort(new SortByAudienceScore());

        float middleIndex = reviewList.size()/2.f;

        if ((reviewList.size() % 2) != 0) return reviewList.get((int) middleIndex).getScore();

        return (reviewList.get((int) middleIndex).getScore() + reviewList.get((int) middleIndex-1).getScore()) / 2.f;

    }

    public void setOccupation(Map<Person,List<RoleType>> map) {
        this.occupation = Occupation.of(this, map);
    }

    @Override
    public String toString() {
        return "FilmWork{" +
                "id=" + fid +
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
        return Float.compare(o1.getScore(),o2.getScore());
    }
}
