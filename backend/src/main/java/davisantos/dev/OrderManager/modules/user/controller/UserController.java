package davisantos.dev.OrderManager.modules.user.controller;

import davisantos.dev.OrderManager.modules.user.dto.UserRequest;
import davisantos.dev.OrderManager.modules.user.dto.UserResponse;
import davisantos.dev.OrderManager.modules.user.service.UserService;
import davisantos.dev.OrderManager.shared.utils.GenericController;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController implements GenericController {

    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return ResponseEntity.ok(userService.findAll());
    }

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserRequest dto) {
        UserResponse response = userService.create(dto);
        return ResponseEntity.created(generateUri(response.id())).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable Long id, @Valid @RequestBody UserRequest dto) {
        return ResponseEntity.ok(userService.update(id, dto));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
