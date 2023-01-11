package cz.filmdb.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import cz.filmdb.serial.UserSerializer;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
@Entity
@Table
@JsonSerialize(using = UserSerializer.class)
public class User {

    @Id
    @SequenceGenerator(
            sequenceName = "user_sequence",
            name = "user_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.AUTO,
            generator = "user_sequence"
    )
    public Long id;
    public String username;

    @Column(unique = true)
    public String email;
    public String password;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
    public Set<Review> userReviews;

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public User(Long id, String username, String email, String password) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public User() {

    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
