package cz.filmdb.repo;

import cz.filmdb.model.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("SELECT r FROM Review r JOIN r.filmwork filmworks JOIN r.user users WHERE filmworks.id IN (:filmworkId)")
    Page<Review> findAllByFilmwork(@Param("filmworkId") Long id, Pageable pageable);

    @Query("SELECT r FROM Review r JOIN r.filmwork filmworks JOIN r.user users WHERE filmworks.id IN (:userId)")
    List<Review> findAllByUser(@Param("userId") Long id);

    @Query("SELECT r FROM Review r JOIN r.filmwork filmworks JOIN r.user users WHERE filmworks.id IN (:userId)")
    Page<Review> findAllByUser(@Param("userId") Long id, Pageable pageable);
}
