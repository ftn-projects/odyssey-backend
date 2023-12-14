package com.example.odyssey.validation;

import com.example.odyssey.dtos.TimeSlotDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class TimeSlotConstraintValidator implements ConstraintValidator<TimeSlotConstraint, TimeSlotDTO> {
    @Override
    public boolean isValid(TimeSlotDTO ts, ConstraintValidatorContext constraintValidatorContext) {
        return !ts.getEnd().isBefore(ts.getStart());
    }
}
