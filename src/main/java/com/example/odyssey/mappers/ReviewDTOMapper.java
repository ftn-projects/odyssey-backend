package com.example.odyssey.mappers;

import com.example.odyssey.dtos.accommodations.AccommodationDTO;
import com.example.odyssey.dtos.reviews.AccommodationReviewDTO;
import com.example.odyssey.dtos.reviews.HostReviewDTO;
import com.example.odyssey.dtos.reviews.ReviewDTO;
import com.example.odyssey.entity.accommodations.Accommodation;
import com.example.odyssey.entity.reviews.AccommodationReview;
import com.example.odyssey.entity.reviews.HostReview;
import com.example.odyssey.entity.reviews.Review;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReviewDTOMapper {
    private static ModelMapper mapper;

    @Autowired
    public ReviewDTOMapper(ModelMapper mapper) {
        ReviewDTOMapper.mapper = mapper;
    }

    public static HostReview fromDTOtoHostReview(HostReviewDTO dto) {
        return mapper.map(dto, HostReview.class);
    }

    public static HostReviewDTO fromHostReviewToDTO(HostReview review) {
        return new HostReviewDTO(review);
    }

    public static AccommodationReview fromDTOtoAccommodationReview(AccommodationReviewDTO dto) {
        return mapper.map(dto, AccommodationReview.class);
    }

    public static AccommodationReviewDTO fromAccommodationReviewToDTO(AccommodationReview review) {
        return new AccommodationReviewDTO(review);
    }

    public static ReviewDTO fromReviewToDTO(Review review) {
        if (review instanceof HostReview) {
            return new HostReviewDTO((HostReview) review);
        }
        return new AccommodationReviewDTO((AccommodationReview) review);
    }
}
