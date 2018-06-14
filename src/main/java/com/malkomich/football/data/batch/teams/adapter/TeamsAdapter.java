package com.malkomich.football.data.batch.teams.adapter;

import com.malkomich.football.data.batch.scrapping.domain.ScrappingFields;
import com.malkomich.football.data.batch.scrapping.domain.ScrappingResponse;
import com.malkomich.football.data.batch.teams.domain.Contact;
import com.malkomich.football.data.batch.teams.domain.Management;
import com.malkomich.football.data.batch.teams.domain.Stadium;
import com.malkomich.football.data.batch.teams.domain.Team;
import com.malkomich.football.data.batch.teams.domain.TeamsResponse;
import com.malkomich.football.data.batch.teams.schema.TeamPageSchema;
import com.malkomich.football.data.batch.util.number.NumberUtils;
import lombok.Builder;
import lombok.val;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Builder
public class TeamsAdapter {

    public TeamsResponse execute(final ScrappingResponse scrappingResponse) {
        return TeamsResponse.builder()
            .teams(adaptTeams(scrappingResponse))
            .build();
    }

    private List<Team> adaptTeams(final ScrappingResponse scrappingResponse) {
        return scrappingResponse.getEntryFields()
            .stream()
            .map(this::adaptTeam)
            .collect(Collectors.toList());
    }

    private Team adaptTeam(final ScrappingFields scrappingFields) {
        val fields = scrappingFields.getFields();
        return Team.builder()
            .name(fields.get(TeamPageSchema.NAME_SELECTOR))
            .fullName(fields.get(TeamPageSchema.FULLNAME_SELECTOR))
            .shortName(fields.get(TeamPageSchema.SHORTNAME_SELECTOR))
            .management(adaptManagement(fields))
            .stadium(adaptStadium(fields))
            .contact(adaptContact(fields))
            .build();
    }

    private Management adaptManagement(final Map<String, String> fields) {
        return Management.builder()
            .manager(fields.get(TeamPageSchema.MANAGER_SELECTOR))
            .president(fields.get(TeamPageSchema.PRESIDENT_SELECTOR))
            .foundationYear(NumberUtils.parseInt(fields.get(TeamPageSchema.FOUNDATION_YEAR_SELECTOR)))
            .playersNumber(NumberUtils.parseInt(fields.get(TeamPageSchema.PLAYERS_NUMBER_SELECTOR)))
            .annualBudget(NumberUtils.parseDouble(fields.get(TeamPageSchema.ANNUAL_BUDGET_SELECTOR)))
            .build();
    }

    private Stadium adaptStadium(final Map<String, String> fields) {
        return Stadium.builder()
            .stadiumName(fields.get(TeamPageSchema.STADIUM_NAME_SELECTOR))
            .stadiumCapacity(NumberUtils.parseInt(fields.get(TeamPageSchema.STADIUM_CAPACITY_SELECTOR)))
            .stadiumBuild(NumberUtils.parseInt(fields.get(TeamPageSchema.STADIUM_BUILD_SELECTOR)))
            .build();
    }

    private Contact adaptContact(final Map<String, String> fields) {
        return Contact.builder()
            .web(fields.get(TeamPageSchema.WEB_SELECTOR))
            .address(fields.get(TeamPageSchema.ADDRESS_SELECTOR))
            .phone(fields.get(TeamPageSchema.PHONE_SELECTOR))
            .build();
    }
}
