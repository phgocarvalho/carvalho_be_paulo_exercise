package com.ecore.roles.service.impl;

import com.ecore.roles.client.TeamsClient;
import com.ecore.roles.client.model.Team;
import com.ecore.roles.service.TeamsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.util.Optional.ofNullable;

@Service
public class TeamsServiceImpl implements TeamsService {

    private final TeamsClient teamsClient;

    @Autowired
    public TeamsServiceImpl(TeamsClient teamsClient) {
        this.teamsClient = teamsClient;
    }

    public Optional<Team> getById(UUID id) {
        return ofNullable(teamsClient.getTeam(id).getBody());
    }

    public List<Team> getAll() {
        return teamsClient.getTeams().getBody();
    }
}
