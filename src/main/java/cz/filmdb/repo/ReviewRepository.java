package cz.filmdb.repo;

import cz.filmdb.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("SELECT r FROM Review r JOIN r.filmwork filmworks JOIN r.user users WHERE filmworks.fid IN (:filmworkId)")
    public List<Review> findReviewsByFilmwork(@Param("filmworkId") Long id);

    @Query("SELECT r FROM Review r JOIN r.filmwork filmworks JOIN r.user users WHERE filmworks.fid IN (:userId)")
    public List<Review> findReviewsByUser(@Param("userId") Long id);
}
