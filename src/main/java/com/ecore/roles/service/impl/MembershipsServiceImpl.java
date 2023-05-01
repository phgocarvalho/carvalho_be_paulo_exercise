package com.ecore.roles.service.impl;

import com.ecore.roles.client.model.Team;
import com.ecore.roles.exception.ResourceExistsException;
import com.ecore.roles.exception.ResourceNotFoundException;
import com.ecore.roles.model.Membership;
import com.ecore.roles.repository.MembershipRepository;
import com.ecore.roles.service.MembershipsService;
import com.ecore.roles.service.RolesService;
import com.ecore.roles.service.TeamsService;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Log4j2
@Service
public class MembershipsServiceImpl implements MembershipsService {

    private final MembershipRepository repository;
    private final RolesService roleService;
    private final TeamsService teamsService;

    @Autowired
    public MembershipsServiceImpl(
            MembershipRepository repository,
            RolesService roleService,
            TeamsService teamsService) {
        this.repository = repository;
        this.roleService = roleService;
        this.teamsService = teamsService;
    }

    @Override
    public Membership assignRoleAndCreate(@NonNull Membership membership) {
        UUID roleId = membership.getRoleIdOrElseThrow();

        if (repository.findByUserIdAndTeamId(membership.getUserId(), membership.getTeamId())
                .isPresent()) {
            throw new ResourceExistsException(Membership.class);
        }

        roleService.getById(roleId);
        getTeamOrElseThrow(membership.getTeamId())
                .validateTeamMember(membership);

        return repository.save(membership);
    }

    private Team getTeamOrElseThrow(UUID teamId) {
        return teamsService.getById(teamId)
                .orElseThrow(() -> new ResourceNotFoundException(Team.class, teamId));
    }

    @Override
    public List<Membership> getAllByRoleId(@NonNull UUID roleId) {
        return repository.findByRoleId(roleId);
    }

    @Override
    public List<Membership> getAllByUserIdAndTeamId(UUID userId, UUID teamId) {
        return repository.findAllByUserIdAndTeamId(userId, teamId);
    }
}
