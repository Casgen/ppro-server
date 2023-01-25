package cz.filmdb.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class FilmworkImage {

    @Id
    @SequenceGenerator(
            name = "filmwork_image_sequence",
            sequenceName = "filmwork_image_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "filmwork_image_sequence"
    )
    private Long id;

    private boolean isTitle;

    private String img;

}
