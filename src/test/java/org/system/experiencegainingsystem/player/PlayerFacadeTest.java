package org.system.experiencegainingsystem.player;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.system.experiencegainingsystem.commons.Experience;
import org.system.experiencegainingsystem.commons.Id;
import org.system.experiencegainingsystem.commons.Name;
import org.system.experiencegainingsystem.commons.Player;
import org.system.experiencegainingsystem.commons.Rank;

import static org.junit.jupiter.api.Assertions.*;

class PlayerFacadeTest {

    private PlayerFacade playerFacade;

    @BeforeEach
    void setUp() {
        playerFacade = new PlayerConfiguration().playerFacade();
    }

    @Test
    void shouldSaveAndFindPlayerById() {
        Id id = new Id();
        Player player = new Player(id, new Name("Hero"), new Experience(0), Rank.NORMAL, false);

        playerFacade.save(player);
        Player foundPlayer = playerFacade.findById(id);

        assertEquals(id, foundPlayer.getPlayerId());
        assertEquals(new Name("Hero"), foundPlayer.getName());
        assertEquals(new Experience(0), foundPlayer.getExperience());
        assertEquals(Rank.NORMAL, foundPlayer.getRank());
        assertFalse(foundPlayer.isHasScroll());
    }

    @Test
    void shouldUpdateExistingPlayerWhenSavingWithSameId() {
        Id id = new Id();
        Player player = new Player(id, new Name("Hero"), new Experience(0), Rank.NORMAL, false);
        playerFacade.save(player);

        Player updatedPlayer = new Player(id, new Name("Legend"), new Experience(100), Rank.VIP, true);
        playerFacade.save(updatedPlayer);

        Player foundPlayer = playerFacade.findById(id);

        assertEquals(new Name("Legend"), foundPlayer.getName());
        assertEquals(new Experience(100), foundPlayer.getExperience());
        assertEquals(Rank.VIP, foundPlayer.getRank());
        assertTrue(foundPlayer.isHasScroll());
    }

    @Test
    void shouldThrowExceptionWhenPlayerNotFound() {
        assertThrows(IllegalArgumentException.class, () -> playerFacade.findById(new Id()));
    }

    @Test
    void shouldThrowExceptionWhenFindingPlayerWithNullId() {
        assertThrows(IllegalArgumentException.class, () -> playerFacade.findById(null));
    }

    @Test
    void shouldThrowExceptionWhenSavingNullPlayer() {
        assertThrows(IllegalArgumentException.class, () -> playerFacade.save(null));
    }
}
