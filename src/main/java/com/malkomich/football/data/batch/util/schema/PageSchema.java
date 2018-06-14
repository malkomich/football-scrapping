package com.malkomich.football.data.batch.util.schema;

import java.util.Collection;

public interface PageSchema {
    String getURL(final String endpoint);
    String containerSelector();
    Collection<Field> fields();
}
