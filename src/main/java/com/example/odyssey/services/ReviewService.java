package com.example.odyssey.services;

import com.example.odyssey.entity.reservations.Reservation;
import com.example.odyssey.entity.reviews.AccommodationReview;
import com.example.odyssey.entity.reviews.HostReview;
import com.example.odyssey.entity.reviews.Review;
import com.example.odyssey.exceptions.reviews.CoolerReviewException;
import com.example.odyssey.exceptions.reviews.ReviewException;
import com.example.odyssey.exceptions.reviews.ReviewNotFoundException;
import com.example.odyssey.repositories.AccommodationReviewRepository;
import com.example.odyssey.repositories.HostReviewRepository;
import com.example.odyssey.repositories.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

    public List<HostReview> getAllHostReviews() {
        return hostReviewRepository.findAll();
    }

    public List<AccommodationReview> getAllAccommodationReviewsFiltered(Long accommodationId, Long submitterId, List<AccommodationReview.Status> listStatuses) {
        return accommodationReviewRepository.findAllWithFilter(null, accommodationId, submitterId, listStatuses);
    }

    public List<AccommodationReview> getAllAccommodationReviewsByHost(Long hostId, List<AccommodationReview.Status> listStatuses) {
        return accommodationReviewRepository.findAllByHost(hostId, listStatuses);
    }

    public List<HostReview> getAllHostReviewsFiltered(Long hostId, Long submitterId, List<HostReview.Status> listStatuses) {
        return hostReviewRepository.findAllWithFilter(null, hostId, submitterId, listStatuses);
    }

    public AccommodationReview findAccommodationReviewById(Long id) {
        return accommodationReviewRepository.findById(id).orElse(null);
    }

    public HostReview findHostReviewById(Long id) {
        return hostReviewRepository.findById(id).orElse(null);
    }

    public Review findById(Long id) {
        Review review = findAccommodationReviewById(id);
        if (review == null) review = findHostReviewById(id);
        if (review == null) throw new ReviewNotFoundException(id);
        return review;
    }

    public List<AccommodationReview> findAccommodationReviewsByAccommodationId(Long id) {
        return accommodationReviewRepository.findAllByAccommodation_Id(id);
    }

    public List<HostReview> findHostReviewsByHostId(Long id) {
        return hostReviewRepository.findAllByHost_Id(id);
    }

    public AccommodationReview saveAccommodationReview(AccommodationReview review) {
        review.setStatus(Review.Status.REQUESTED);
        LocalDateTime endDate = LocalDateTime.now().minusMinutes(10080);

        List<Review.Status> reviewStatuses = Arrays.asList(
                Review.Status.REQUESTED,
                Review.Status.REPORTED,
                Review.Status.ACCEPTED
        );

        List<Reservation.Status> reservationStatuses = List.of(
                Reservation.Status.ACCEPTED
        );

        List<Reservation> reservations = reservationRepository.findAllWithFilterButCooler(
                null,
                reservationStatuses,
                review.getAccommodation().getId(),
                endDate
        );

        // Pass list of string values of enums
        List<AccommodationReview> reviews = accommodationReviewRepository.findAllWithFilter(
                null,
                review.getAccommodation().getId(),
                review.getSubmitter().getId(),
                reviewStatuses
        );

        if (reservations.isEmpty()) {
            throw new CoolerReviewException("You don't have a reservation for this accommodation");
        }

        if (reviews != null && !reviews.isEmpty()) {
            throw new ReviewException("You have already reviewed this accommodation");
        }

        return accommodationReviewRepository.save(review);
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
        LocalDateTime startDate = LocalDateTime.now().minusMinutes(14400);
        LocalDateTime endDate = LocalDateTime.now();

        List<Review.Status> reviewStatuses = Arrays.asList(
                Review.Status.REQUESTED,
                Review.Status.REPORTED,
                Review.Status.ACCEPTED
        );

        List<String> reservationStatuses = Collections.singletonList(
                Reservation.Status.ACCEPTED.toString()
        );

        List<Reservation> reservations = reservationService.getFilteredByHost(
                review.getHost().getId(),
                reservationStatuses,
                null,
                startDate.toEpochSecond(ZoneOffset.UTC),
                endDate.toEpochSecond(ZoneOffset.UTC)
        );

        // Pass list of string values of enums
        List<HostReview> reviews = hostReviewRepository.findAllWithFilter(
                null,
                review.getHost().getId(),
                review.getSubmitter().getId(),
                reviewStatuses
        );

        if (reservations.isEmpty()) {
            throw new CoolerReviewException("You don't have a reservation for this accommodation");
        }

        if (reviews != null && !reviews.isEmpty()) {
            throw new ReviewException("You have already reviewed this accommodation");
        }

        return hostReviewRepository.save(review);
    }

    public Double getTotalRatingByAccommodation(Long id) {
        List<Review.Status> statuses = List.of(
                Review.Status.ACCEPTED
        );
        List<AccommodationReview> reviews = accommodationReviewRepository.findAllWithFilter(null, id, null, statuses);
        if (reviews == null || reviews.isEmpty()) return 0.0;
        return reviews.stream().mapToDouble(AccommodationReview::getRating).sum() / reviews.size();
    }

    public Double getTotalRatingByHost(Long id) {
        List<Review.Status> statuses = List.of(
                Review.Status.ACCEPTED
        );
        List<HostReview> reviews = hostReviewRepository.findAllWithFilter(null, id, null, statuses);
        if (reviews == null || reviews.isEmpty()) return 0.0;
        return reviews.stream().mapToDouble(HostReview::getRating).sum() / reviews.size();
    }

    public List<Integer> getRatingsByAccommodation(Long id) {
        List<Review.Status> statuses = Collections.singletonList(Review.Status.ACCEPTED);
        List<AccommodationReview> reviews = accommodationReviewRepository.findAllWithFilter(null, id, null, statuses);

        // Count occurrences of each rating using Java streams
        Map<Double, Long> ratingCounts = reviews.stream()
                .collect(Collectors.groupingBy(AccommodationReview::getRating, Collectors.counting()));

        return IntStream.range(1, 6)
                .mapToObj(i -> ratingCounts.getOrDefault((double) i, 0L))
                .map(Long::intValue)
                .collect(Collectors.toList());
    }

    public List<Integer> getRatingsByHost(Long id) {
        List<Review.Status> statuses = Collections.singletonList(Review.Status.ACCEPTED);
        List<HostReview> reviews = hostReviewRepository.findAllWithFilter(null, id, null, statuses);

        // Count occurrences of each rating using Java streams
        Map<Double, Long> ratingCounts = reviews.stream()
                .collect(Collectors.groupingBy(HostReview::getRating, Collectors.counting()));

        return IntStream.range(1, 6)
                .mapToObj(i -> ratingCounts.getOrDefault((double) i, 0L))
                .map(Long::intValue)
                .collect(Collectors.toList());
    }

    public void accept(Long id) {
        Review review = findById(id);
        if (review.getStatus().equals(Review.Status.DECLINED))
            throw new ReviewException("Review is already accepted");

        review.setStatus(Review.Status.ACCEPTED);
        save(review);
    }

    public void decline(Long id) {
        Review review = findById(id);
        if (review.getStatus().equals(Review.Status.DECLINED))
            throw new ReviewException("Review is already declined");

        review.setStatus(Review.Status.DECLINED);
        save(review);
    }

    public void dismiss(Long id) {
        Review review = findById(id);
        if (!review.getStatus().equals(Review.Status.REPORTED))
            throw new ReviewException("This review was not reported");

        review.setStatus(Review.Status.ACCEPTED);
        save(review);
    }

    public void reportReview(Long id) {
        Review review = findById(id);
        if (review.getStatus().equals(Review.Status.DECLINED) || review.getStatus().equals(Review.Status.REQUESTED))
            throw new ReviewException("Only accepted and reported reviews can be reported");

        review.setStatus(Review.Status.REPORTED);
        save(review);
    }

    public void save(Review review) {
        if (review instanceof AccommodationReview)
            accommodationReviewRepository.save((AccommodationReview) review);
        else hostReviewRepository.save((HostReview) review);
    }

    public List<Review> getAllFiltered(String search, List<Review.Status> statuses, List<String> types) {
        List<Review> reviews = new ArrayList<>();
        search = search != null ? search.toUpperCase() : null;

        if (types == null || types.contains("ACCOMMODATION"))
            reviews.addAll(accommodationReviewRepository.findAllWithFilter(search, null, null, statuses));
        if (types == null || types.contains("HOST"))
            reviews.addAll(hostReviewRepository.findAllWithFilter(search, null, null, statuses));
        return reviews;
    }

    public void deleteAccommodationReview(Long id) {
        accommodationReviewRepository.deleteById(id);
    }

    public void deleteHostReview(Long id) {
        hostReviewRepository.deleteById(id);
    }
}
