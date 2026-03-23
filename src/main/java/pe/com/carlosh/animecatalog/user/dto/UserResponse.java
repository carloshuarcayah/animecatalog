package pe.com.carlosh.animecatalog.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record UserResponse(
        long id,
        String username,
        String role,
        LocalDateTime createdAt,
        //ver estado del usuario
        boolean active
) {
}
