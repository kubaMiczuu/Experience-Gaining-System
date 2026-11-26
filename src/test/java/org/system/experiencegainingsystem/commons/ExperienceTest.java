package org.system.experiencegainingsystem.commons;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExperienceTest {

    @Test
    void shouldThrowExceptionWhenNegativeValue() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Experience(-1);
        });
    }

    @Test
    void shouldReturnNewExperienceInstanceWhenMultiplying() {
        Experience experience = new Experience(10);
        Experience result = experience.multiply(10);

        assertEquals(new Experience(100), result);
        assertNotSame(experience, result);
        assertEquals(new Experience(10), experience);
    }

    @Test
    void shouldThrowExceptionWhenMultiplyingWithNegativeValue() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Experience(10).multiply(-1);
        });
    }

    @Test
    void shouldReturnSummedExperienceValue() {
        Experience experience = new Experience(10);
        Experience experience2 = new Experience(20);

        assertEquals(new Experience(30), experience.add(experience2));
    }

    @Test
    void shouldThrowExceptionWhenAddingNullExperience() {
        assertThrows(IllegalArgumentException.class, () -> new Experience(10).add(null));
    }

    @Test
    void shouldCompareExperienceValuesThroughEqualsAndHashcode() {
        Experience exp1 = new Experience(10);
        Experience exp2 = new Experience(10);
        Experience different = new Experience(20);

        assertEquals(exp1, exp2);
        assertEquals(exp1.hashCode(), exp2.hashCode());
        assertNotEquals(exp1, different);
    }

    @Test
    void shouldReturnExperienceValueAsString() {
        assertEquals("10.0", new Experience(10).toString());
        assertEquals("12.5", new Experience(12.5).toString());
    }
}
