package com.ecore.roles.web.rest;

import com.ecore.roles.service.UsersService;
import com.ecore.roles.web.UsersApi;
import com.ecore.roles.web.dto.UserDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.ecore.roles.web.dto.UserDto.fromModel;

@RestController
@RequestMapping(value = "/v1/users")
public class UsersRestController implements UsersApi {

    private final UsersService service;

    public UsersRestController(UsersService service) {
        this.service = service;
    }

    @Override
    @GetMapping(
            produces = {"application/json"})
    public ResponseEntity<List<UserDto>> getAll() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.getAll().stream()
                        .map(UserDto::fromModel)
                        .collect(Collectors.toList()));
    }

    @Override
    @GetMapping(
            path = "/{id}",
            produces = {"application/json"})
    public ResponseEntity<UserDto> getById(
            @PathVariable UUID id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(fromModel(service.getById(id).get()));
    }
}
