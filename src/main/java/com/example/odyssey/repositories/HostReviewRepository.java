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
            "WHERE (:hostName IS NULL OR UPPER(CONCAT(r.host.name, ' ', r.host.surname)) LIKE %:hostName%) " +
            "  AND (:hostId IS NULL OR r.host.id = :hostId) " +
            "  AND (:submitterId IS NULL OR r.submitter.id = :submitterId) " +
            "  AND (:listStatuses IS NULL OR r.status IN :listStatuses)")
    List<HostReview> findAllWithFilter(
            @Param("hostName") String hostName,
            @Param("hostId") Long hostId,
            @Param("submitterId") Long guestId,
            @Param("listStatuses") List<Review.Status> listStatuses
    );
    List<HostReview> findAllByHost_Id(Long id);

    List<HostReview> findAllBySubmitter_Id(Long id);

    List<HostReview> findAllByStatus(Review.Status status);
}
