package cz.filmdb.model;

import jakarta.persistence.*;

// WATCH OUT FOR DUPLICATE IMPORTS OR IMPORTS LIKE THESE, THEY CAN OVERRIDE YOUR BEANS!
// import org.springframework.data.relational.core.mapping.Table;

@Table
@Entity(name = "genre")
public class Genre {

    @Id
    @SequenceGenerator(
            name = "genre_sequence",
            sequenceName = "genre_sequence"
    )
    @GeneratedValue(
            strategy = GenerationType.AUTO,
            generator = "genre_sequence"
    )
    public Long id;
    public String name;

    public Genre(String name) {
        this.name = name;
    }

    public Genre(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Genre() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
