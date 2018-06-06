package com.malkomich.football.data.batch.teams.schema;

import com.google.common.collect.Lists;
import com.malkomich.football.data.batch.util.schema.Field;
import com.malkomich.football.data.batch.util.schema.PageSchema;
import com.malkomich.football.data.batch.util.schema.Type;
import lombok.Builder;

import java.util.Collection;

@Builder
public class TeamPageSchema implements PageSchema {
    private static final String BASE_URL = "http://www.resultados-futbol.com/";
    private static final String RANK_ROW_SELECTOR = "#col-resultados";
    /**
     * Selectors
     */
    public static final String NAME_SELECTOR = ".titlebox";
    public static final String FULLNAME_SELECTOR = "tr:nth-child(3) td:nth-child(2)";
    public static final String SHORTNAME_SELECTOR = "tr:nth-child(4) td:nth-child(2)";
    public static final String MANAGER_SELECTOR = "tr:nth-child(6) td:nth-child(2)";
    public static final String PRESIDENT_SELECTOR = "tr:nth-child(7) td:nth-child(2)";
    public static final String FOUNDATION_YEAR_SELECTOR = "tr:nth-child(8) td:nth-child(2)";
    public static final String PLAYERS_NUMBER_SELECTOR = "tr:nth-child(9) td:nth-child(2)";
    public static final String ANNUAL_BUDGET_SELECTOR = "tr:nth-child(10) td:nth-child(2)";
    public static final String STADIUM_NAME_SELECTOR = "tr:nth-child(12) td:nth-child(2)";
    public static final String STADIUM_CAPACITY_SELECTOR = "tr:nth-child(13) td:nth-child(2)";
    public static final String STADIUM_BUILD_SELECTOR = "tr:nth-child(14) td:nth-child(2)";
    public static final String WEB_SELECTOR = "tr:nth-child(19) td:nth-child(2) a";
    public static final String ADDRESS_SELECTOR = "tr:nth-child(23) td:nth-child(2)";
    public static final String PHONE_SELECTOR = "tr:nth-child(24) td:nth-child(2)";

    @Override
    public String getURL(final String endpoint) {
        return BASE_URL.concat(endpoint);
    }

    @Override
    public String containerSelector() {
        return RANK_ROW_SELECTOR;
    }

    @Override
    @SuppressWarnings("checkstyle:methodlength")
    public Collection<Field> fields() {
        return Lists.newArrayList(
            Field.builder().selector(NAME_SELECTOR).build(),
            Field.builder().selector(FULLNAME_SELECTOR).build(),
            Field.builder().selector(SHORTNAME_SELECTOR).build(),
            Field.builder().selector(MANAGER_SELECTOR).build(),
            Field.builder().selector(PRESIDENT_SELECTOR).build(),
            Field.builder().selector(FOUNDATION_YEAR_SELECTOR).build(),
            Field.builder().selector(PLAYERS_NUMBER_SELECTOR).build(),
            Field.builder().selector(ANNUAL_BUDGET_SELECTOR).build(),
            Field.builder().selector(STADIUM_NAME_SELECTOR).build(),
            Field.builder().selector(STADIUM_CAPACITY_SELECTOR).build(),
            Field.builder().selector(STADIUM_BUILD_SELECTOR).build(),
            Field.builder().selector(WEB_SELECTOR).type(Type.HREF).build(),
            Field.builder().selector(ADDRESS_SELECTOR).build(),
            Field.builder().selector(PHONE_SELECTOR).build());
    }
}
