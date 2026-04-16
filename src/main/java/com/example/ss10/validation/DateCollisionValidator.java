package com.example.ss10.validation;

import com.example.ss10.model.Booking;
import com.example.ss10.service.BookingService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class DateCollisionValidator implements ConstraintValidator<CheckDateCollision, Booking> {
    
    @Autowired
    private BookingService bookingService;
    
    @Override
    public void initialize(CheckDateCollision constraintAnnotation) {
    }
    
    @Override
    public boolean isValid(Booking booking, ConstraintValidatorContext context) {
        // Only validate for lab room bookings
        if (!"LAB_ROOM".equals(booking.getItemType()) || booking.getLabRoomId() == null) {
            return true;
        }
        
        // Check if there are any existing bookings for the same lab room in the same date range
        boolean hasCollision = bookingService.hasDateCollision(
            booking.getLabRoomId(),
            booking.getBorrowDate(),
            booking.getReturnDate(),
            booking.getId() // Exclude current booking when updating
        );
        
        if (hasCollision) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                "Phòng lab đã được đăng ký trong khoảng thời gian này"
            ).addConstraintViolation();
            return false;
        }
        
        return true;
    }
}
