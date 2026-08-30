package com.example.expense_tracker.validation;

import com.example.expense_tracker.dto.UserRegisterRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, UserRegisterRequest> {

    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
    }

    @Override
    public boolean isValid(UserRegisterRequest request, ConstraintValidatorContext context) {
        if (request == null) {
            return true;
        }
        return request.getPassword().equals(request.getConfirmPassword());
    }
}
