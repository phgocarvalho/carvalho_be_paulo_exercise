package com.ecore.roles.service.impl;

import com.ecore.roles.client.model.Team;
import com.ecore.roles.exception.ResourceExistsException;
import com.ecore.roles.exception.ResourceNotFoundException;
import com.ecore.roles.model.Membership;
import com.ecore.roles.model.Role;
import com.ecore.roles.repository.RoleRepository;
import com.ecore.roles.service.MembershipsService;
import com.ecore.roles.service.RolesService;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Log4j2
@Service
public class RolesServiceImpl implements RolesService {

    private final RoleRepository repository;
    private final MembershipsService membershipsService;

    @Value("${roles.default}")
    private String defaultRole;

    @Autowired
    public RolesServiceImpl(
            RoleRepository repository,
            @Lazy MembershipsService membershipsService) {
        this.repository = repository;
        this.membershipsService = membershipsService;
    }

    @Override
    public Role create(@NonNull Role role) {

        if (repository.findByName(role.getName()).isPresent()) {
            throw new ResourceExistsException(Role.class);
        }

        return repository.save(role);
    }

    @Override
    public Role getById(@NonNull UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Role.class, id));
    }

    @Override
    public List<Role> getAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Role> findByName(String name) {
        return repository.findByName(name);
    }

    @Override
    public void delete(Role role) {
        repository.delete(role);
    }

    @Override
    public List<Role> getAllByUserIdAndTeamId(UUID userId, UUID teamId) {
        List<Membership> memberships = membershipsService.getAllByUserIdAndTeamId(userId, teamId);

        if (memberships.isEmpty()) {
            throw new ResourceNotFoundException(Team.class, teamId);
        }

        return memberships.stream()
                .map(membership -> membership.getRole())
                .collect(Collectors.toList());
    }

    public Role getDefaultRole() {
        return repository.findByName(defaultRole)
                .orElseThrow(() -> new IllegalStateException("Default role is not configured"));
    }
}
