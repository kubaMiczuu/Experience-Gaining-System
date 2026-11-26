package org.system.experiencegainingsystem;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.system.experiencegainingsystem.commons.Experience;
import org.system.experiencegainingsystem.commons.Id;
import org.system.experiencegainingsystem.commons.Mob;
import org.system.experiencegainingsystem.commons.Name;
import org.system.experiencegainingsystem.commons.Player;
import org.system.experiencegainingsystem.commons.Rank;
import org.system.experiencegainingsystem.experience.ExperienceFacade;
import org.system.experiencegainingsystem.mob.MobFacade;
import org.system.experiencegainingsystem.player.PlayerFacade;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class ExperienceGainingE2ETest {

    private static final double MAX_TIME_MULTIPLIER = 1.4;

    @Autowired
    private ExperienceFacade experienceFacade;

    @Autowired
    private PlayerFacade playerFacade;

    @Autowired
    private MobFacade mobFacade;

    @Test
    void shouldGainAndPersistExperienceAcrossMultipleKillsInFullApplicationContext() {
        Id playerId = new Id();
        Experience initialExp = new Experience(100);
        Player player = new Player(playerId, new Name("Hero"), initialExp, Rank.VIP, true);
        playerFacade.save(player);

        Id slimeId = new Id();
        Id dragonId = new Id();
        mobFacade.save(new Mob(slimeId, new Name("Slime"), new Experience(10), new Experience(20)));
        mobFacade.save(new Mob(dragonId, new Name("Dragon"), new Experience(100), new Experience(200)));

        Experience firstGain = experienceFacade.gainExperience(playerId, slimeId);
        Experience secondGain = experienceFacade.gainExperience(playerId, dragonId);
        Experience thirdGain = experienceFacade.gainExperience(playerId, slimeId);

        assertWithinBounds(firstGain, 10.0, 20.0 * 1.5 * 1.5 * MAX_TIME_MULTIPLIER);
        assertWithinBounds(secondGain, 100.0, 200.0 * 1.5 * 1.5 * MAX_TIME_MULTIPLIER);
        assertWithinBounds(thirdGain, 10.0, 20.0 * 1.5 * 1.5 * MAX_TIME_MULTIPLIER);

        Player persistedPlayer = playerFacade.findById(playerId);
        Experience expectedTotal = initialExp.add(firstGain).add(secondGain).add(thirdGain);

        assertEquals(expectedTotal, persistedPlayer.getExperience());
        assertTrue(persistedPlayer.getExperience().getExperience() >= 220.0);
    }

    private void assertWithinBounds(Experience gained, double minExpected, double maxExpected) {
        double value = gained.getExperience();
        assertTrue(value >= minExpected && value < maxExpected + 0.0001,
                () -> "Expected gained experience in [" + minExpected + ", " + maxExpected + "] but was " + value);
    }
}
