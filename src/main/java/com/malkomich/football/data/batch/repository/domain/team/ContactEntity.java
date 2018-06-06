package com.malkomich.football.data.batch.repository.domain.team;

import io.vertx.core.json.JsonObject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContactEntity {
    private String web;
    private String address;
    private String phone;

    public ContactEntity(final JsonObject json) {
        this.web = json.getString("web");
        this.address = json.getString("address");
        this.phone = json.getString("phone");
    }
}
