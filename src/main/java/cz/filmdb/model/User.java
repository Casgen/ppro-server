package cz.filmdb.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import cz.filmdb.deserial.UserDeserializer;
import cz.filmdb.serial.UserSerializer;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Setter
@Getter
@Entity
@Builder
@Table
@AllArgsConstructor
@JsonSerialize(using = UserSerializer.class)
@JsonDeserialize(using = UserDeserializer.class)
// The needed getUsername() and getPassword() methods are already overridden by the lombok @Getter annotation
public class User implements UserDetails {

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
    private Long id;

    @Column(unique = true)
    private String username;

    @Column(unique = true)
    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole userRole;


    // Users which will watch, wont watch, have watched or is watching some movies or tv shows.
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(name = "plans_to_watch",
            joinColumns = {
                    @JoinColumn(name = "user_id"),
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "filmwork_id")
            }
    )
    @JsonManagedReference("users-plans-to-watch-ref")
    private Set<Filmwork> plansToWatch = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(name = "is_watching",
            joinColumns = {
                    @JoinColumn(name = "user_id"),
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "filmwork_id")
            }
    )
    @JsonManagedReference("users-watching-ref")
    private Set<Filmwork> isWatching = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(name = "wont_watch",
            joinColumns = {
                    @JoinColumn(name = "user_id"),
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "filmwork_id")
            }
    )
    @JsonManagedReference("users-wont-watch-ref")
    private Set<Filmwork> wontWatch = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(name = "have_watched",
            joinColumns = {
                    @JoinColumn(name = "user_id"),
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "filmwork_id")
            }
    )
    @JsonManagedReference("users-watched-ref")
    private Set<Filmwork> hasWatched = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
    @JsonManagedReference("users-reviews-ref")
    public Set<Review> userReviews = new HashSet<>();


    public User(Long id, String username, String email, String password) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.userReviews = new HashSet<>();
        this.hasWatched = new HashSet<>();
        this.wontWatch = new HashSet<>();
        this.isWatching = new HashSet<>();
    }

    public User(Long id, String username, String email, String password, UserRole userRole) {
        this(id, username, email, password);
        this.userRole = userRole;
    }

    public User(String username, String email, String password, UserRole userRole) {
        this(null, username, email, password, userRole);
    }

    public User() {
    }

    public User(Long id, String username) {
        this(id, username, null, null, null);
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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(userRole.name()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void addToPlansToWatch(Filmwork filmwork) {
        this.plansToWatch.add(filmwork);
        filmwork.getUsersPlanToWatch().add(this);
    }

    public void addToWontWatch(Filmwork filmwork) {
        this.wontWatch.add(filmwork);
        filmwork.getUsersWontWatch().add(this);
    }

    public void addToHasWatched(Filmwork filmwork) {
        this.hasWatched.add(filmwork);
        filmwork.getUsersHaveWatched().add(this);
    }

    public void addToWatching(Filmwork filmwork) {
        this.isWatching.add(filmwork);
        filmwork.getUsersWatching().add(this);
    }



    public void removeFromPlansToWatch(Filmwork filmwork) {
        this.plansToWatch.remove(filmwork);
        filmwork.getUsersPlanToWatch().remove(this);
    }

    public void removeFromWontWatch(Filmwork filmwork) {
        this.wontWatch.remove(filmwork);
        filmwork.getUsersWontWatch().remove(this);
    }

    public void removeFromHasWatched(Filmwork filmwork) {
        this.hasWatched.remove(filmwork);
        filmwork.getUsersHaveWatched().remove(this);
    }

    public void removeFromIsWatching(Filmwork filmwork) {
        this.isWatching.remove(filmwork);
        filmwork.getUsersWatching().remove(this);
    }
}
