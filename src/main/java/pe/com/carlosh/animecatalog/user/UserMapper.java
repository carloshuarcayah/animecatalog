package pe.com.carlosh.animecatalog.user;

import pe.com.carlosh.animecatalog.user.dto.UserResponse;

public class UserMapper {
    public static UserResponse toResponse(User user){
        return new UserResponse(user.getId(),user.getUsername(),user.getRole().name(),user.getCreatedAt(), user.isEnabled());
    }
}
