package com.ecore.roles.web;

import com.ecore.roles.web.dto.UserDto;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface UsersApi {

    ResponseEntity<List<UserDto>> getAll();

    ResponseEntity<UserDto> getById(UUID id);
}
