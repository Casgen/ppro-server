package cz.filmdb.repo;

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
}
