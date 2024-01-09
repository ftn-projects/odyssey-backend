package com.example.odyssey.services;

import com.example.odyssey.entity.reservations.Reservation;
import com.example.odyssey.entity.reviews.AccommodationReview;
import com.example.odyssey.entity.reviews.HostReview;
import com.example.odyssey.entity.reviews.Review;
import com.example.odyssey.repositories.AccommodationReviewRepository;
import com.example.odyssey.repositories.HostReviewRepository;
import com.example.odyssey.repositories.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@Service
public class ReviewService {
    @Autowired
    private AccommodationReviewRepository accommodationReviewRepository;

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private HostReviewRepository hostReviewRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    public List<AccommodationReview> getAllAccommodationReviews() {
        return accommodationReviewRepository.findAll();
    }
    public List<HostReview> getAllHostReviews(){
        return hostReviewRepository.findAll();
    }

    public List<AccommodationReview> getAllAccommodationReviewsFiltered(Long accommodationId, Long submitterId, List<AccommodationReview.Status> listTypes) {
        return accommodationReviewRepository.findAllWithFilter(accommodationId, submitterId, listTypes);
    }

    public List<HostReview> getAllHostReviewsFiltered(Long hostId, Long submitterId, List<HostReview.Status> listTypes) {
        return hostReviewRepository.findAllWithFilter(hostId, submitterId, listTypes);
    }

    public AccommodationReview findAccommodationReviewById(Long id) {
        return accommodationReviewRepository.findById(id).orElse(null);
    }

    public HostReview findHostReviewById(Long id) {
        return hostReviewRepository.findById(id).orElse(null);
    }

    public List<AccommodationReview> findAccommodationReviewsByAccommodationId(Long id) {
        return accommodationReviewRepository.findAllByAccommodation_Id(id);
    }

    public List<HostReview> findHostReviewsByHostId(Long id) {
        return hostReviewRepository.findAllByHost_Id(id);
    }

    public AccommodationReview saveAccommodationReview(AccommodationReview review) {
        LocalDateTime startDate = LocalDateTime.now().minusMinutes(120);
        LocalDateTime endDate = LocalDateTime.now();

        List<Review.Status> reviewStatuses = Arrays.asList(
                Review.Status.REQUESTED,
                Review.Status.REPORTED,
                Review.Status.ACCEPTED
        );

        List<Reservation.Status> reservationStatuses = Arrays.asList(
          Reservation.Status.ACCEPTED
        );

        List<Reservation> reservations = reservationRepository.findAllWithFilterButCooler(
                null,
                reservationStatuses,
                review.getAccommodation().getId(),
                startDate,
                endDate
        );

        // Pass list of string values of enums
        List<AccommodationReview> reviews = accommodationReviewRepository.findAllWithFilter(
                review.getAccommodation().getId(),
                review.getSubmitter().getId(),
                reviewStatuses
        );

        if ((reviews == null || reviews.isEmpty()) && !reservations.isEmpty()) {
            return accommodationReviewRepository.save(review);
        }

        return null;
    }

    public AccommodationReview reportAccommodationReview(Long id) {
        AccommodationReview review = accommodationReviewRepository.findById(id).orElse(null);
        if (review != null) {
            review.setStatus(Review.Status.REPORTED);
            return accommodationReviewRepository.save(review);
        }
        return null;
    }

    public HostReview reportHostReview(Long id) {
        HostReview review = hostReviewRepository.findById(id).orElse(null);
        if (review != null) {
            review.setStatus(Review.Status.REPORTED);
            return hostReviewRepository.save(review);
        }
        return null;
    }
    public HostReview saveHostReview(HostReview review) {
        LocalDateTime startDate = LocalDateTime.now().minusMinutes(120);
        LocalDateTime endDate = LocalDateTime.now();

        List<Review.Status> reviewStatuses = Arrays.asList(
                Review.Status.REQUESTED,
                Review.Status.REPORTED,
                Review.Status.ACCEPTED
        );

        List<String> reservationStatuses = Arrays.asList(
                Reservation.Status.ACCEPTED.toString()
        );

        List<Reservation> reservations = reservationService.getFiltered(
                review.getHost().getId(),
                reservationStatuses,
                null,
                startDate.toEpochSecond(ZoneOffset.UTC),
                endDate.toEpochSecond(ZoneOffset.UTC)
        );

        // Pass list of string values of enums
        List<HostReview> reviews = hostReviewRepository.findAllWithFilter(
                review.getHost().getId(),
                review.getSubmitter().getId(),
                reviewStatuses
        );

        if ((reviews == null || reviews.isEmpty()) && !reservations.isEmpty()) {
            return hostReviewRepository.save(review);
        }

        return null;
    }
    public void deleteAccommodationReview(Long id) {
        accommodationReviewRepository.deleteById(id);
    }

    public void deleteHostReview(Long id) {
        hostReviewRepository.deleteById(id);
    }
}
