package com.server.api;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import com.server.DTO.UserDto;
import com.server.domain.Role;
import com.server.domain.User;
import com.server.services.UserService;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserResource {
    private final UserService userService;
    private final ModelMapper modelMapper;

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok().body(userService.getUsers());
    }

    @GetMapping("/role/{id}")
    public ResponseEntity<List<UserDto>> getUserByRole(@PathVariable("id") Role role) {
        List<User> users = userService.getUserByRole(role);

        List<UserDto> response = users.stream()
                .map(element -> modelMapper.map(element, UserDto.class))
                .collect(Collectors.toList());

        return ResponseEntity
                .ok()
                .body(response);
    }

    @PostMapping("/user/save")
    public ResponseEntity<User> saveUser(@RequestBody User user) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/user/save").toUriString());
        return ResponseEntity
                .created(uri)
                .body(userService.saveUser(user));
    }

    @PostMapping("/role/save")
    public ResponseEntity<Role> saveRole(@RequestBody Role role) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/role/save").toUriString());
        return ResponseEntity
                .created(uri)
                .body(userService.saveRole(role));
    }

    @PostMapping("/role/addtouser")
    public ResponseEntity<?> addRoleToUser(@RequestBody RoleToUserForm form) {
        userService.addRoleToUser(form.getUsername(), form.getRoleName());
        return ResponseEntity
                .ok()
                .build();
    }
}

@Data
class RoleToUserForm {
    private String username;
    private String roleName;
}
