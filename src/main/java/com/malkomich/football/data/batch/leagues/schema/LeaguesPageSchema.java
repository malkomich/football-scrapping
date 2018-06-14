package com.malkomich.football.data.batch.leagues.schema;

import com.google.common.collect.Lists;
import com.malkomich.football.data.batch.util.schema.Field;
import com.malkomich.football.data.batch.util.schema.PageSchema;
import com.malkomich.football.data.batch.util.schema.Type;
import lombok.Builder;

import java.util.Collection;

@Builder
public class LeaguesPageSchema implements PageSchema {
    public static final String BASE_URL = "http://www.resultados-futbol.com/";

    private static final String LEAGUES_URL = BASE_URL.concat("ligas/");
    private static final String LEAGUE_ROW_SELECTOR = "#cnt_leagues tr.fila";
    /**
     * Selectors
     */
    public static final String NAME_SELECTOR = ".league_name";
    public static final String URL_SELECTOR = ".league_name a";

    @Override
    public String getURL(final String endpoint) {
        return LEAGUES_URL.concat(endpoint);
    }

    @Override
    public String containerSelector() {
        return LEAGUE_ROW_SELECTOR;
    }

    @Override
    public Collection<Field> fields() {
        return Lists.newArrayList(
            Field.builder().selector(NAME_SELECTOR).build(),
            Field.builder().selector(URL_SELECTOR).type(Type.HREF).build());
    }
}
