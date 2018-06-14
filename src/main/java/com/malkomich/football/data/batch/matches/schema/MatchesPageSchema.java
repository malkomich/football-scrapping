package com.malkomich.football.data.batch.matches.schema;

import com.google.common.collect.Lists;
import com.malkomich.football.data.batch.util.schema.Field;
import com.malkomich.football.data.batch.util.schema.PageSchema;
import com.malkomich.football.data.batch.util.schema.Type;
import lombok.Builder;

import java.util.Collection;

@Builder
public class MatchesPageSchema implements PageSchema {
    private static final String BASE_URL = "http://www.resultados-futbol.com/";
    private static final String RANK_ROW_SELECTOR = "#col-resultados tr";
    /**
     * Selectors
     */
    public static final String DATE_SELECTOR = ".fecha";
    public static final String LOCAL_TEAM_SELECTOR = ".equipo1";
    public static final String VISITOR_TEAM_SELECTOR = ".equipo2";
    public static final String RESULT_SELECTOR = ".rstd a";
    public static final String MATCH_ENDPOINT_SELECTOR = ".rstd a";

    @Override
    public String getURL(final String endpoint) {
        return BASE_URL.concat(endpoint);
    }

    @Override
    public String containerSelector() {
        return RANK_ROW_SELECTOR;
    }

    @Override
    public Collection<Field> fields() {
        return Lists.newArrayList(
            Field.builder().selector(DATE_SELECTOR).build(),
            Field.builder().selector(LOCAL_TEAM_SELECTOR).build(),
            Field.builder().selector(VISITOR_TEAM_SELECTOR).build(),
            Field.builder().selector(RESULT_SELECTOR).build(),
            Field.builder().selector(MATCH_ENDPOINT_SELECTOR).type(Type.HREF).build());
    }
}
