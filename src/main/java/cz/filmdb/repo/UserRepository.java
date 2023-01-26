package cz.filmdb.repo;

import cz.filmdb.model.Filmwork;
import cz.filmdb.model.Movie;
import cz.filmdb.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

    @Query("SELECT u FROM User u WHERE SOUNDEX(u.username) = SOUNDEX(:query)")
    Page<User> findAllByUsernameContainingIgnoreCase(@Param("query") String query, Pageable pageable);

    @Query("SELECT f from Filmwork f JOIN f.usersPlanToWatch user WHERE user.id IN (:userId) ")
    Page<Filmwork> findUsersPlansToWatchListById(@Param("userId") Long id, Pageable pageable);

    @Query("SELECT f from Filmwork f JOIN f.usersHaveWatched user WHERE user.id IN (:userId) ")
    Page<Filmwork> findUsersHasWatchedListById(@Param("userId") Long id, Pageable pageable);

    @Query("SELECT f from Filmwork f JOIN f.usersWatching user WHERE user.id IN (:userId) ")
    Page<Filmwork> findUsersWatchingListById(@Param("userId") Long id, Pageable pageable);

}
