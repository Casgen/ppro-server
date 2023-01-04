package cz.filmdb.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table
@Getter
@Setter
public class Review {

    @Id
    @SequenceGenerator(
            sequenceName = "review_sequence",
            name = "review_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.AUTO,
            generator = "review_sequence"
    )
    private Long id;

    public Review(User user, Filmwork filmwork, String comment, float score) {
        this.user = user;
        this.filmwork = filmwork;
        this.comment = comment;
        this.score = score;
        this.date = LocalDateTime.now();
    }

    public Review() {}

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    //Do not set the CascadeType here, it's supposed to be set only on the other side.
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "filmwork_id")
    private Filmwork filmwork;

    private LocalDateTime date;
    private String comment;
    private float score;


}
