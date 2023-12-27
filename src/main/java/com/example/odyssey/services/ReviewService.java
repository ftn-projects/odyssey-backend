package com.example.odyssey.services;

import com.example.odyssey.entity.reviews.AccommodationReview;
import com.example.odyssey.entity.reviews.HostReview;
import com.example.odyssey.repositories.AccommodationReviewRepository;
import com.example.odyssey.repositories.HostReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
        return accommodationReviewRepository.save(review);
    }

    public HostReview saveHostReview(HostReview review) {
        return hostReviewRepository.save(review);
    }


}
