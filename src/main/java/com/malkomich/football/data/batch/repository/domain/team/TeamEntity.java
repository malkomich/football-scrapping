package com.malkomich.football.data.batch.repository.domain.team;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@DataObject
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeamEntity {
    private String name;
    private String fullName;
    private String shortName;
    private ManagementEntity management;
    private StadiumEntity stadium;
    private ContactEntity contact;

    public TeamEntity(final JsonObject json) {
        this.name = json.getString("name");
        this.fullName = json.getString("fullName");
        this.shortName = json.getString("shortName");
        this.management = new ManagementEntity(json.getJsonObject("management"));
        this.stadium = new StadiumEntity(json.getJsonObject("stadium"));
        this.contact = new ContactEntity(json.getJsonObject("contact"));
    }

    public JsonObject toJson() {
        return JsonObject.mapFrom(this);
    }





}
