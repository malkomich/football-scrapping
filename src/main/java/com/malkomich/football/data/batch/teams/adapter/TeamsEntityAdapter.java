package com.malkomich.football.data.batch.teams.adapter;

import com.malkomich.football.data.batch.repository.domain.team.ContactEntity;
import com.malkomich.football.data.batch.repository.domain.team.ManagementEntity;
import com.malkomich.football.data.batch.repository.domain.team.StadiumEntity;
import com.malkomich.football.data.batch.repository.domain.team.TeamEntity;
import com.malkomich.football.data.batch.teams.domain.Contact;
import com.malkomich.football.data.batch.teams.domain.Management;
import com.malkomich.football.data.batch.teams.domain.Stadium;
import com.malkomich.football.data.batch.teams.domain.Team;
import com.malkomich.football.data.batch.teams.domain.TeamsResponse;
import lombok.Builder;

import java.util.List;
import java.util.stream.Collectors;

@Builder
public class TeamsEntityAdapter {

    public List<TeamEntity> execute(final TeamsResponse teamsResponse) {
        return teamsResponse.getTeams()
            .stream()
            .map(this::execute)
            .collect(Collectors.toList());
    }

    private TeamEntity execute(final Team team) {
        return TeamEntity.builder()
            .name(team.getName())
            .fullName(team.getFullName())
            .shortName(team.getShortName())
            .management(adaptManagement(team.getManagement()))
            .stadium(adaptStadium(team.getStadium()))
            .contact(adaptContact(team.getContact()))
            .build();
    }

    private ContactEntity adaptContact(final Contact contact) {
        return ContactEntity.builder()
            .address(contact.getAddress())
            .web(contact.getWeb())
            .phone(contact.getPhone())
            .build();
    }

    private StadiumEntity adaptStadium(final Stadium stadium) {
        return StadiumEntity.builder()
            .stadiumName(stadium.getStadiumName())
            .stadiumBuild(stadium.getStadiumBuild())
            .stadiumCapacity(stadium.getStadiumCapacity())
            .build();
    }

    private ManagementEntity adaptManagement(final Management management) {
        return ManagementEntity.builder()
            .foundationYear(management.getFoundationYear())
            .manager(management.getManager())
            .president(management.getPresident())
            .playersNumber(management.getPlayersNumber())
            .annualBudget(management.getAnnualBudget())
            .build();
    }
}
