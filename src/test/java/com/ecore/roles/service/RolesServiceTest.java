package com.ecore.roles.service;

import com.ecore.roles.exception.ResourceNotFoundException;
import com.ecore.roles.model.Membership;
import com.ecore.roles.model.Role;
import com.ecore.roles.repository.MembershipRepository;
import com.ecore.roles.repository.RoleRepository;
import com.ecore.roles.service.impl.RolesServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static com.ecore.roles.utils.TestData.DEFAULT_MEMBERSHIP;
import static com.ecore.roles.utils.TestData.DEVELOPER_ROLE;
import static com.ecore.roles.utils.TestData.ORDINARY_CORAL_LYNX_TEAM_UUID;
import static com.ecore.roles.utils.TestData.UUID_1;
import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.ReflectionTestUtils.setField;

@ExtendWith(MockitoExtension.class)
class RolesServiceTest {

    public static final String DEFAULT_ROLE = "Developer";
    @InjectMocks
    private RolesServiceImpl rolesService;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private MembershipRepository membershipRepository;

    @Mock
    private MembershipsService membershipsService;

    @Test
    public void shouldCreateRole() {
        Role developerRole = DEVELOPER_ROLE();
        when(roleRepository.save(developerRole)).thenReturn(developerRole);

        Role role = rolesService.create(developerRole);

        assertNotNull(role);
        assertEquals(developerRole, role);
    }

    @Test
    public void shouldFailToCreateRoleWhenRoleIsNull() {
        assertThrows(NullPointerException.class,
                () -> rolesService.create(null));
    }

    @Test
    public void shouldReturnRoleWhenRoleIdExists() {
        Role developerRole = DEVELOPER_ROLE();
        when(roleRepository.findById(developerRole.getId())).thenReturn(Optional.of(developerRole));

        Role role = rolesService.getById(developerRole.getId());

        assertNotNull(role);
        assertEquals(developerRole, role);
    }

    @Test
    public void shouldFailToGetRoleWhenRoleIdDoesNotExist() {
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> rolesService.getById(UUID_1));

        assertEquals(format("Role %s not found", UUID_1), exception.getMessage());
    }

    @Test
    public void shouldGetAllByUserIdAndTeamIdWhenUserIdAndTeamIdAreNotNull() {
        Membership membership = DEFAULT_MEMBERSHIP();

        when(membershipsService.getAllByUserIdAndTeamId(UUID_1, ORDINARY_CORAL_LYNX_TEAM_UUID))
                .thenReturn(List.of(membership));

        List<Role> roles = rolesService.getAllByUserIdAndTeamId(UUID_1, ORDINARY_CORAL_LYNX_TEAM_UUID);

        assertNotNull(roles);
        assertEquals(1, roles.size());
        assertEquals(membership.getRole().getId(), roles.get(0).getId());
    }

    @Test
    public void shouldGetAllByUserIdAndTeamIdWhenUserIdIsNull() {
        Membership membership = DEFAULT_MEMBERSHIP();

        when(membershipsService.getAllByUserIdAndTeamId(null, ORDINARY_CORAL_LYNX_TEAM_UUID))
                .thenReturn(List.of(membership));

        List<Role> roles = rolesService.getAllByUserIdAndTeamId(null, ORDINARY_CORAL_LYNX_TEAM_UUID);

        assertNotNull(roles);
        assertEquals(1, roles.size());
        assertEquals(membership.getRole().getId(), roles.get(0).getId());
    }

    @Test
    public void shouldGetAllByUserIdAndTeamIdWhenTeamIdIsNull() {
        Membership membership = DEFAULT_MEMBERSHIP();

        when(membershipsService.getAllByUserIdAndTeamId(UUID_1, null))
                .thenReturn(List.of(membership));

        List<Role> roles = rolesService.getAllByUserIdAndTeamId(UUID_1, null);

        assertNotNull(roles);
        assertEquals(1, roles.size());
        assertEquals(membership.getRole().getId(), roles.get(0).getId());
    }

    @Test
    public void shouldFailToGetAllByUserIdAndTeamIdWhenItHasNoElement() {
        when(membershipsService.getAllByUserIdAndTeamId(UUID_1, ORDINARY_CORAL_LYNX_TEAM_UUID))
                .thenReturn(List.of());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> rolesService.getAllByUserIdAndTeamId(UUID_1, ORDINARY_CORAL_LYNX_TEAM_UUID));

        assertEquals(format("Team %s not found", ORDINARY_CORAL_LYNX_TEAM_UUID), exception.getMessage());
    }

    @Test
    public void shouldGetDefaultRoleWhenItIsConfigured() {
        Role developerRole = DEVELOPER_ROLE();

        setField(rolesService, "defaultRole", DEFAULT_ROLE);
        when(roleRepository.findByName(DEFAULT_ROLE))
                .thenReturn(Optional.of(developerRole));

        Role defaultRole = rolesService.getDefaultRole();

        assertNotNull(defaultRole);
        assertEquals(developerRole, defaultRole);
    }

    @Test
    public void shouldFailToGetDefaultRoleWhenItIsNotConfigured() {
        setField(rolesService, "defaultRole", DEFAULT_ROLE);
        when(roleRepository.findByName(DEFAULT_ROLE))
                .thenReturn(Optional.empty());

        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> rolesService.getDefaultRole());

        assertEquals("Default role is not configured", exception.getMessage());
    }
}
