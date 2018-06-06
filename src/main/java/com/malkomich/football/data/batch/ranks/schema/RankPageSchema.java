package com.malkomich.football.data.batch.ranks.schema;

import com.google.common.collect.Lists;
import com.malkomich.football.data.batch.util.schema.Field;
import com.malkomich.football.data.batch.util.schema.PageSchema;
import lombok.Builder;

import java.util.Collection;

@Builder
public class RankPageSchema implements PageSchema {
    private static final String BASE_URL = "http://www.resultados-futbol.com/";
    private static final String RANK_ROW_SELECTOR = "#col-clasificacion table tbody tr";
    /**
     * Selectors
     */
    public static final String POSITION_SELECTOR = ".pos2";
    public static final String TEAM_SELECTOR = ".equipo a";
    public static final String SCORE_SELECTOR = ".pts";
    public static final String PLAYED_MATCHES_SELECTOR = ".pj";
    public static final String DRAW_MATCHES_SELECTOR = ".draw";
    public static final String WON_MATCHES_SELECTOR = ".win";
    public static final String LOST_MATCHES_SELECTOR = ".lose";
    public static final String GOALS_IN_FAVOUR_SELECTOR = ".f";
    public static final String GOALS_AGAINST_SELECTOR = ".c";

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
            Field.builder().selector(POSITION_SELECTOR).build(),
            Field.builder().selector(TEAM_SELECTOR).build(),
            Field.builder().selector(SCORE_SELECTOR).build(),
            Field.builder().selector(PLAYED_MATCHES_SELECTOR).build(),
            Field.builder().selector(DRAW_MATCHES_SELECTOR).build(),
            Field.builder().selector(WON_MATCHES_SELECTOR).build(),
            Field.builder().selector(LOST_MATCHES_SELECTOR).build(),
            Field.builder().selector(GOALS_IN_FAVOUR_SELECTOR).build(),
            Field.builder().selector(GOALS_AGAINST_SELECTOR).build());
    }
}
