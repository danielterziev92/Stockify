package com.stockify.catalog.product.infrastructure.jooq;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Table;

import java.util.UUID;

import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.table;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AttributeTables {
    public static final Table<Record> ATTRIBUTE_KEY = table("attribute_key");
    public static final Table<Record> ATTRIBUTE_VALUE = table("attribute_value");

    public static final Field<UUID> KEY_ID = field("attribute_key.id", UUID.class);
    public static final Field<String> KEY_NAME = field("attribute_key.name", String.class);

    public static final Field<UUID> VALUE_ID = field("attribute_value.id", UUID.class);
    public static final Field<String> VALUE_VALUE = field("attribute_value.value", String.class);
    public static final Field<String> VALUE_ABBR = field("attribute_value.abbreviation", String.class);
    public static final Field<UUID> VALUE_KEY_ID = field("attribute_value.key_id", UUID.class);
}
