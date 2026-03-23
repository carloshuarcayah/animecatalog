package pe.com.carlosh.animecatalog.user;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.com.carlosh.animecatalog.user.dto.UserResponse;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;

    public Page<UserResponse> findAll(Pageable pageable){
        return userRepository.findAll(pageable).map(UserMapper::toResponse);
    }

}
