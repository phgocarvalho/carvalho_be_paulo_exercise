package com.ecore.roles.service;

import com.ecore.roles.client.model.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UsersService {

    Optional<User> getById(UUID id);

    List<User> getAll();
}
