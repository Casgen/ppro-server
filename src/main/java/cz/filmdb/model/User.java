package cz.filmdb.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
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
import java.util.List;
import java.util.Set;

@Setter
@Getter
@Entity
@Builder
@Table
@AllArgsConstructor
@JsonSerialize(using = UserSerializer.class)
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
    private Role role;


    // Users which will watch, wont watch, have watched or is watching some movies or tv shows.


    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "plans_to_watch",
            joinColumns = {
                    @JoinColumn(name = "user_id"),
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "filmwork_id")
            }
    )
    private Set<Filmwork> plansToWatch;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "is_watching",
            joinColumns = {
                    @JoinColumn(name = "user_id"),
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "filmwork_id")
            }
    )
    private Set<Filmwork> isWatching;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "wont_watch",
            joinColumns = {
                    @JoinColumn(name = "user_id"),
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "filmwork_id")
            }
    )
    private Set<Filmwork> wontWatch;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "have_watched",
            joinColumns = {
                    @JoinColumn(name = "user_id"),
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "filmwork_id")
            }
    )
    private Set<Filmwork> haveWatched;


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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
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
}
