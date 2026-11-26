package org.system.experiencegainingsystem.commons;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NameTest {

    @Test
    void shouldCreateNameWhenValueIsValid() {
        Name name = new Name("Goblin");

        assertEquals("Goblin", name.getName());
        assertEquals("Goblin", name.toString());
    }

    @Test
    void shouldThrowExceptionWhenNameIsNull() {
        assertThrows(IllegalArgumentException.class, () -> new Name(null));
    }

    @Test
    void shouldThrowExceptionWhenNameIsEmpty() {
        assertThrows(IllegalArgumentException.class, () -> new Name(""));
    }

    @Test
    void shouldThrowExceptionWhenNameIsBlank() {
        assertThrows(IllegalArgumentException.class, () -> new Name("   "));
    }

    @Test
    void shouldCompareNamesThroughEqualsAndHashCode() {
        Name name1 = new Name("Goblin");
        Name name2 = new Name("Goblin");
        Name different = new Name("Skeleton");

        assertEquals(name1, name2);
        assertEquals(name1.hashCode(), name2.hashCode());
        assertNotEquals(name1, different);
    }
}
