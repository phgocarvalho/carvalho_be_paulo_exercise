package com.ecore.roles.service;

import com.ecore.roles.model.Role;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RolesService {

    Role create(Role role);

    Role getById(UUID id);

    List<Role> getAll();

    Optional<Role> findByName(String name);

    void delete(Role role);

    List<Role> getAllByUserIdAndTeamId(UUID userId, UUID teamId);
}
