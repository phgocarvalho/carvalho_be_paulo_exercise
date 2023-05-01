package com.ecore.roles.service.impl;

import com.ecore.roles.client.UsersClient;
import com.ecore.roles.client.model.User;
import com.ecore.roles.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.util.Optional.ofNullable;

@Service
public class UsersServiceImpl implements UsersService {

    private final UsersClient usersClient;

    @Autowired
    public UsersServiceImpl(UsersClient usersClient) {
        this.usersClient = usersClient;
    }

    public Optional<User> getById(UUID id) {
        return ofNullable(usersClient.getUser(id).getBody());
    }

    public List<User> getAll() {
        return usersClient.getUsers().getBody();
    }
}
