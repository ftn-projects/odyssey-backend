package com.example.odyssey.validation;

import com.example.odyssey.dtos.TimeSlotDTO;
import com.example.odyssey.dtos.accommodations.AccommodationDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class AccommodationConstraintValidator implements ConstraintValidator<AccommodationConstraint, AccommodationDTO> {
    @Override
    public boolean isValid(AccommodationDTO dto, ConstraintValidatorContext constraintValidatorContext) {
        return dto.getMinGuests() <= dto.getMaxGuests();
    }
}
