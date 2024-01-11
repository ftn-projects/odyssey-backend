package com.example.odyssey.repositories;

import com.example.odyssey.entity.reviews.AccommodationReview;
import com.example.odyssey.entity.reviews.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;

import java.util.List;

public interface AccommodationReviewRepository extends JpaRepository<AccommodationReview, Long> {
    @NonNull
    List<AccommodationReview> findAll();

    @Query("SELECT r " +
            "FROM AccommodationReview r " +
            "WHERE (:accommodationTitle IS NULL OR LOWER(r.accommodation.title) LIKE %:accommodationTitle%) " +
            "  AND (:accommodationId IS NULL OR r.accommodation.id = :accommodationId) " +
            "  AND (:submitterId IS NULL OR r.submitter.id = :submitterId) " +
            "  AND (:listStatuses IS NULL OR r.status IN :listStatuses)")
    List<AccommodationReview> findAllWithFilter(
            @Param("accommodationTitle") String accommodationTitle,
            @Param("accommodationId") Long accommodationId,
            @Param("submitterId") Long guestId,
            @Param("listStatuses") List<Review.Status> listStatuses
    );
    List<AccommodationReview> findAllByAccommodation_Id(Long id);

    List<AccommodationReview> findAllBySubmitter_Id(Long id);

    List<AccommodationReview> findAllByStatus(Review.Status status);

}
