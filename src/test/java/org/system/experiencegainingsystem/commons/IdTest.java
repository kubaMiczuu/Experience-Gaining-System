package org.system.experiencegainingsystem.commons;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class IdTest {

    @Test
    void shouldGenerateNonNullUuidWhenUsingDefaultConstructor() {
        Id id = new Id();
        Id id2 = new Id();

        assertNotNull(id.getId());
        assertNotEquals(id, id2);
    }

    @Test
    void shouldGenerateNonNullUuidWhenProvidedUuidIsNull() {
        Id id = new Id(null);

        assertNotNull(id.getId());
    }

    @Test
    void shouldPreserveUuidWhenProvidedValidUuid() {
        UUID uuid = UUID.randomUUID();
        Id id = new Id(uuid);

        assertEquals(uuid, id.getId());
        assertEquals(uuid.toString(), id.toString());
    }

    @Test
    void shouldCompareIdsThroughEqualsAndHashCode() {
        UUID uuid = UUID.randomUUID();
        Id id1 = new Id(uuid);
        Id id2 = new Id(uuid);
        Id different = new Id();

        assertEquals(id1, id2);
        assertEquals(id1.hashCode(), id2.hashCode());
        assertNotEquals(id1, different);
    }
}
