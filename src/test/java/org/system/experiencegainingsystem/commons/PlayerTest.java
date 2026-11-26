package org.system.experiencegainingsystem.commons;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    @Test
    void shouldSetScrollMultiplierToOnePointFiveWhenPlayerHasScroll() {
        Player player = new Player(new Id(), new Name("Hero"), new Experience(0), Rank.NORMAL, true);

        assertTrue(player.isHasScroll());
        assertEquals(1.5, player.getScrollMultiplier());
    }

    @Test
    void shouldSetScrollMultiplierToOneWhenPlayerHasNoScroll() {
        Player player = new Player(new Id(), new Name("Hero"), new Experience(0), Rank.NORMAL, false);

        assertFalse(player.isHasScroll());
        assertEquals(1.0, player.getScrollMultiplier());
    }

    @Test
    void shouldAccumulateExperienceWhenGainingExperienceMultipleTimes() {
        Player player = new Player(new Id(), new Name("Hero"), new Experience(10), Rank.NORMAL, false);

        player.gainExperience(new Experience(25));
        player.gainExperience(new Experience(15));

        assertEquals(new Experience(50), player.getExperience());
    }

    @Test
    void shouldThrowExceptionWhenRequiredFieldIsNull() {
        Id playerId = new Id();
        Name name = new Name("Hero");
        Experience experience = new Experience(0);
        Rank rank = Rank.NORMAL;

        assertThrows(IllegalArgumentException.class, () -> new Player(null, name, experience, rank, false));
        assertThrows(IllegalArgumentException.class, () -> new Player(playerId, null, experience, rank, false));
        assertThrows(IllegalArgumentException.class, () -> new Player(playerId, name, null, rank, false));
        assertThrows(IllegalArgumentException.class, () -> new Player(playerId, name, experience, null, false));
    }

    @Test
    void shouldThrowExceptionWhenGainingNullExperience() {
        Player player = new Player(new Id(), new Name("Hero"), new Experience(0), Rank.NORMAL, false);

        assertThrows(IllegalArgumentException.class, () -> player.gainExperience(null));
    }
}
