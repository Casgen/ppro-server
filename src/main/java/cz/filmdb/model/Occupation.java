package cz.filmdb.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import cz.filmdb.enums.RoleType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
@Entity
@Table(name = "occupation")
public class Occupation {

    @Id
    @SequenceGenerator(
            name = "occupation_sequence",
            sequenceName = "occupation_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "occupation_sequence"
    )
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "filmwork_id", nullable = false)
    private Filmwork filmwork;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "person_id", nullable = false)
    private Person person;

    @Enumerated(EnumType.STRING)
    private RoleType role;

    public Occupation(Filmwork filmwork, Person person, RoleType role) {
        this.filmwork = filmwork;
        this.person = person;
        this.role = role;
    }

    public Occupation(Long id, Person person, RoleType role) {
        this.filmwork = null;
        this.role = role;
        this.person = person;
    }

    public Occupation() {

    }

    @Override
    public String toString() {
        return "Occupation{" +
                "id=" + id +
                ", filmwork=" + filmwork +
                ", person=" + person +
                ", role=" + role +
                '}';
    }

    public static Set<Occupation> of(Filmwork filmwork, Map<Person, List<RoleType>> map) {

        Set<Occupation> occupations = new HashSet<>();

        for (Map.Entry<Person, List<RoleType>> entry : map.entrySet()) {

            for (RoleType role : entry.getValue()) {
                occupations.add(new Occupation(filmwork, entry.getKey(), role));
            }
        }

        return occupations;
    }
}
