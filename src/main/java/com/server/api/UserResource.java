package com.server.api;

import java.net.URI;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

import com.server.DTO.UserDto;
import com.server.domain.Role;
import com.server.domain.ShippingAddress;
import com.server.domain.User;
import com.server.domain.UserShipping;
import com.server.services.UserService;
import com.server.services.UserShippingService;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
    private final UserShippingService userShippingService;
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

    @PostMapping("/user/create")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        URI uri = URI
                .create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/user/create").toUriString());
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

    // user shipping
    @PostMapping("/user/shipping")
    public ResponseEntity<?> addUserShipping(@RequestBody UserShipping userShipping, Principal principal) {
        User user = userService.getUser(principal.getName());
        userShippingService.addUserShipping(
                user,
                userShipping.getUserShippingStreet1(),
                userShipping.getUserShippingStreet2(),
                userShipping.getUserShippingCity(),
                userShipping.getUserShippingState(),
                userShipping.getUserShippingCountry(),
                userShipping.getUserShippingZipCode(),
                userShipping.isUserShippingDefault());

        return ResponseEntity.status(HttpStatus.CREATED).body("user shipping successfully created");
    }

    @PutMapping("/user/shipping")
    public ResponseEntity<UserShipping> updateUserShipping(@RequestBody UserShipping userShipping) {

        UserShipping response = userShippingService.updateUserShipping(
                userShipping.getId(),
                userShipping.getUserShippingStreet1(),
                userShipping.getUserShippingStreet2(),
                userShipping.getUserShippingCity(),
                userShipping.getUserShippingState(),
                userShipping.getUserShippingCountry(),
                userShipping.getUserShippingZipCode(),
                userShipping.isUserShippingDefault());

        return ResponseEntity.ok(response);
    }

}

@Data
class RoleToUserForm {
    private String username;
    private String roleName;
}
