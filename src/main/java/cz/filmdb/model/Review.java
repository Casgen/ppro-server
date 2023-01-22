package cz.filmdb.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import cz.filmdb.deserial.ReviewDeserializer;
import cz.filmdb.serial.ReviewSerializer;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table
@Getter
@Setter
@JsonSerialize(using = ReviewSerializer.class)
@JsonDeserialize(using = ReviewDeserializer.class)
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

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    @JsonBackReference("users-reviews-ref")
    private User user;

    //Do not set the CascadeType here, it's supposed to be set only on the other side.
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "filmwork_id")
    private Filmwork filmwork;

    @CreationTimestamp
    private LocalDateTime date;

    private String comment;

    private float score;


    public Review(Long id, User user, Filmwork filmwork, LocalDateTime date, String comment, float score) {
        this.id = id;
        this.user = user;
        this.filmwork = filmwork;
        this.date = date;
        this.comment = comment;
        this.score = score;
    }

    public Review(User user, Filmwork filmwork, String comment, float score) {
        this.user = user;
        this.filmwork = filmwork;
        this.comment = comment;
        this.score = score;
        this.date = LocalDateTime.now();
    }

    public Review() {}


}
