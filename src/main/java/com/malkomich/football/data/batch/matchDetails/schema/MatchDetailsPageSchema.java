package com.malkomich.football.data.batch.matchDetails.schema;

import com.google.common.collect.Lists;
import com.malkomich.football.data.batch.util.schema.Field;
import com.malkomich.football.data.batch.util.schema.PageSchema;
import lombok.Builder;

import java.util.Collection;

@Builder
public class MatchDetailsPageSchema implements PageSchema {
    private static final String BASE_URL = "http://www.resultados-futbol.com/";
    private static final String RANK_ROW_SELECTOR = "#box-tabla tbody tr";
    /**
     * Selectors
     */
    public static final String LOCAL_VALUE = "td:nth-child(1)";
    public static final String STATISTIC_NAME = "td:nth-child(2) h6";
    public static final String VISITOR_VALUE = "td:nth-child(1)";

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
            Field.builder().selector(LOCAL_VALUE).build(),
            Field.builder().selector(STATISTIC_NAME).build(),
            Field.builder().selector(VISITOR_VALUE).build());
    }
}
