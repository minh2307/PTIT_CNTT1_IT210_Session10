package com.example.ss10.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = DateCollisionValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckDateCollision {
    String message() default "Phòng lab đã được đăng ký trong khoảng thời gian này";
    
    Class<?>[] groups() default {};
    
    Class<? extends Payload>[] payload() default {};
}
