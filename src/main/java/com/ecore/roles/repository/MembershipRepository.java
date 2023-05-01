package com.ecore.roles.repository;

import com.ecore.roles.model.Membership;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface MembershipRepository extends JpaRepository<Membership, UUID> {

    Optional<Membership> findByUserIdAndTeamId(UUID userId, UUID teamId);

    List<Membership> findByRoleId(UUID roleId);

    @Query(value = "SELECT m FROM Membership m " +
            "WHERE (:userId IS NULL OR m.userId = :userId) AND (:teamId IS NULL OR m.teamId = :teamId)")
    List<Membership> findAllByUserIdAndTeamId(@Param("userId") UUID userId, @Param("teamId") UUID teamId);
}
