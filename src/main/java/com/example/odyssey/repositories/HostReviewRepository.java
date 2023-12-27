package com.example.odyssey.repositories;

import com.example.odyssey.entity.reviews.AccommodationReview;
import com.example.odyssey.entity.reviews.HostReview;
import com.example.odyssey.entity.reviews.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;

import java.util.List;

public interface HostReviewRepository extends JpaRepository<HostReview, Long> {
    @NonNull
    List<HostReview> findAll();

    @Query("SELECT r " +
            "FROM HostReview r " +
            "WHERE (:hostId IS NULL OR r.host.id = :hostId) " +
            "  AND (:submitterId IS NULL OR r.submitter.id = :submitterId) " +
            "  AND (:listTypes IS NULL OR r.status IN :listTypes)")
    List<HostReview> findAllWithFilter(
            @Param("hostId") Long hostId,
            @Param("submitterId") Long guestId,
            @Param("listTypes") List<Review.Status> listTypes
    );
    List<HostReview> findAllByHost_Id(Long id);

    List<HostReview> findAllBySubmitter_Id(Long id);

    List<HostReview> findAllByStatus(Review.Status status);
}
