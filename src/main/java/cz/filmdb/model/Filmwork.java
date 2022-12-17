package cz.filmdb.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

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
    public Long fid;

    @Column(nullable = false)
    public String name;

    @Column(name = "audience_score")
    public float audienceScore;

    @Column(name = "critics_score")
    public float criticsScore;

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
    public Set<Genre> genres;

    public Filmwork(String name, Set<Genre> genres) {
        this.name = name;
        this.genres = genres;
    }

    public Filmwork(Long fid, String name, Set<Genre> genres) {
        this.fid = fid;
        this.name = name;
        this.genres = genres;
    }

    public Filmwork() {

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
