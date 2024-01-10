package com.example.odyssey.services;

import com.example.odyssey.entity.reviews.AccommodationReview;
import com.example.odyssey.entity.reviews.HostReview;
import com.example.odyssey.entity.reviews.Review;
import com.example.odyssey.repositories.AccommodationReviewRepository;
import com.example.odyssey.repositories.HostReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ReviewService {
    @Autowired
    private AccommodationReviewRepository accommodationReviewRepository;

    @Autowired
    private HostReviewRepository hostReviewRepository;

    public List<AccommodationReview> getAllAccommodationReviews() {
        return accommodationReviewRepository.findAll();
    }
    public List<HostReview> getAllHostReviews(){
        return hostReviewRepository.findAll();
    }

    public List<AccommodationReview> getAllAccommodationReviewsFiltered(Long accommodationId, Long submitterId, List<AccommodationReview.Status> listStatuses) {
        return accommodationReviewRepository.findAllWithFilter(null, accommodationId, submitterId, listStatuses);
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
        if (review == null) throw new NoSuchElementException(String.format("Review with id '%d' does not exist.", id));
        return review;
    }

    public List<AccommodationReview> findAccommodationReviewsByAccommodationId(Long id) {
        return accommodationReviewRepository.findAllByAccommodation_Id(id);
    }

    public List<HostReview> findHostReviewsByHostId(Long id) {
        return hostReviewRepository.findAllByHost_Id(id);
    }

    public AccommodationReview saveAccommodationReview(AccommodationReview review) {
        return accommodationReviewRepository.save(review);
    }

    public HostReview saveHostReview(HostReview review) {
        return hostReviewRepository.save(review);
    }


    public void accept(Long id) {
        Review review = findById(id);
        review.setStatus(Review.Status.ACCEPTED);
        save(review);
    }

    public void decline(Long id) {
        Review review = findById(id);
        review.setStatus(Review.Status.DECLINED);
        save(review);
    }

    public void save(Review review) {
        if (review instanceof AccommodationReview)
            accommodationReviewRepository.save((AccommodationReview) review);
        else hostReviewRepository.save((HostReview) review);
    }

    public List<Review> getAllFiltered(String search, List<Review.Status> statuses, List<String> types) {
        List<Review> reviews = new ArrayList<>();
        if (types == null || types.contains("ACCOMMODATION"))
            reviews.addAll(accommodationReviewRepository.findAllWithFilter(search, null, null, statuses));
        if (types == null || types.contains("HOST"))
            reviews.addAll(hostReviewRepository.findAllWithFilter(search, null, null, statuses));
        return reviews;
    }
}
