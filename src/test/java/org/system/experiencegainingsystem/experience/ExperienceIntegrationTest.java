package org.system.experiencegainingsystem.experience;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalMatchers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.system.experiencegainingsystem.commons.Experience;
import org.system.experiencegainingsystem.commons.Id;
import org.system.experiencegainingsystem.commons.Mob;
import org.system.experiencegainingsystem.commons.Name;
import org.system.experiencegainingsystem.commons.Player;
import org.system.experiencegainingsystem.commons.Rank;
import org.system.experiencegainingsystem.mob.MobConfiguration;
import org.system.experiencegainingsystem.mob.MobFacade;
import org.system.experiencegainingsystem.player.PlayerConfiguration;
import org.system.experiencegainingsystem.player.PlayerFacade;

import java.time.Clock;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.random.RandomGenerator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExperienceIntegrationTest {

    private PlayerFacade playerFacade;
    private MobFacade mobFacade;

    @Mock
    private RandomGenerator randomGenerator;

    @BeforeEach
    void setUp() {
        playerFacade = new PlayerConfiguration().playerFacade();
        mobFacade = new MobConfiguration().mobFacade();
    }

    private ExperienceFacade createFacadeAt(int hour, int minute, int second) {
        Clock fixedClock = Clock.fixed(
                LocalDate.of(2026, 1, 1)
                        .atTime(hour, minute, second)
                        .atZone(ZoneId.of("UTC"))
                        .toInstant(),
                ZoneId.of("UTC")
        );
        return new ExperienceFacade(playerFacade, mobFacade, randomGenerator, fixedClock);
    }

    @Test
    void shouldCalculateAndPersistGainedExperienceForPlayer() {
        ExperienceFacade experienceFacade = createFacadeAt(12, 0, 0);
        Id playerId = new Id();
        Id mobId = new Id();

        playerFacade.save(new Player(playerId, new Name("Hero"), new Experience(10), Rank.VIP, true));
        mobFacade.save(new Mob(mobId, new Name("Slime"), new Experience(15), new Experience(30)));

        when(randomGenerator.nextDouble(eq(15.0), AdditionalMatchers.eq(67.5, 0.0001))).thenReturn(50.0);

        Experience gained = experienceFacade.gainExperience(playerId, mobId);
        Player persisted = playerFacade.findById(playerId);

        assertEquals(new Experience(50.0), gained);
        assertEquals(new Experience(60.0), persisted.getExperience());
    }

    @Test
    void shouldAccumulateExperienceAcrossMultipleMobKills() {
        ExperienceFacade experienceFacade = createFacadeAt(18, 30, 0);
        Id playerId = new Id();
        Id slimeId = new Id();
        Id dragonId = new Id();

        playerFacade.save(new Player(playerId, new Name("Hero"), new Experience(0), Rank.NORMAL, false));
        mobFacade.save(new Mob(slimeId, new Name("Slime"), new Experience(10), new Experience(20)));
        mobFacade.save(new Mob(dragonId, new Name("Dragon"), new Experience(100), new Experience(200)));

        when(randomGenerator.nextDouble(eq(10.0), AdditionalMatchers.eq(28.0, 0.0001))).thenReturn(22.0);
        when(randomGenerator.nextDouble(eq(100.0), AdditionalMatchers.eq(280.0, 0.0001))).thenReturn(210.0);

        Experience firstGain = experienceFacade.gainExperience(playerId, slimeId);
        Experience secondGain = experienceFacade.gainExperience(playerId, dragonId);

        assertEquals(new Experience(22.0), firstGain);
        assertEquals(new Experience(210.0), secondGain);
        assertEquals(new Experience(232.0), playerFacade.findById(playerId).getExperience());
    }

    @Test
    void shouldThrowExceptionWhenPlayerDoesNotExist() {
        ExperienceFacade experienceFacade = createFacadeAt(12, 0, 0);
        Mob mob = new Mob(new Id(), new Name("Slime"), new Experience(10), new Experience(20));
        mobFacade.save(mob);

        assertThrows(IllegalArgumentException.class, () -> experienceFacade.gainExperience(new Id(), mob.getMobId()));
    }

    @Test
    void shouldThrowExceptionWhenMobDoesNotExist() {
        ExperienceFacade experienceFacade = createFacadeAt(12, 0, 0);
        Player player = new Player(new Id(), new Name("Hero"), new Experience(10), Rank.NORMAL, false);
        playerFacade.save(player);

        assertThrows(IllegalArgumentException.class, () -> experienceFacade.gainExperience(player.getPlayerId(), new Id()));
        assertEquals(new Experience(10), playerFacade.findById(player.getPlayerId()).getExperience());
    }
}
