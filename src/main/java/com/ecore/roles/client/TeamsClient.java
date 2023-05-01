package com.ecore.roles.client;

import com.ecore.roles.client.model.Team;
import com.ecore.roles.configuration.ClientsConfigurationProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;

@Component
public class TeamsClient {

    private final RestTemplate restTemplate;
    private final ClientsConfigurationProperties clientsConfigurationProperties;

    @Autowired
    public TeamsClient(
            RestTemplate restTemplate,
            ClientsConfigurationProperties clientsConfigurationProperties) {
        this.restTemplate = restTemplate;
        this.clientsConfigurationProperties = clientsConfigurationProperties;
    }

    public ResponseEntity<Team> getTeam(UUID id) {
        return restTemplate.exchange(
                clientsConfigurationProperties.getTeamsApiHost() + "/" + id,
                HttpMethod.GET,
                null,
                Team.class);
    }

    public ResponseEntity<List<Team>> getTeams() {
        return restTemplate.exchange(
                clientsConfigurationProperties.getTeamsApiHost(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {});
    }
}
