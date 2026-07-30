package davisantos.dev.OrderManager.modules.user.service;

import davisantos.dev.OrderManager.modules.user.domain.User;
import davisantos.dev.OrderManager.modules.user.dto.UserRequest;
import davisantos.dev.OrderManager.modules.user.dto.UserResponse;
import davisantos.dev.OrderManager.modules.user.mapper.UserMapper;
import davisantos.dev.OrderManager.modules.user.repository.UserRepository;
import davisantos.dev.OrderManager.shared.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserResponse create(UserRequest dto) {
        User user = userMapper.toEntity(dto);
        return userMapper.toDto(userRepository.save(user));
    }

    @Transactional(readOnly = true)
    public UserResponse findById(Long id){
        User response = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));
        return userMapper.toDto(response);
    }

    @Transactional(readOnly = true)
    public List<UserResponse> findAll(){
        return userRepository.findAll()
                .stream()
                .map(userMapper::toDto)
                .toList();
    }

    public UserResponse update(Long id, UserRequest dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));

        user.setEmail(dto.email());
        user.setUsername(dto.username());

        return userMapper.toDto(user);
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}
