package org.system.experiencegainingsystem.experience;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.AdditionalMatchers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.system.experiencegainingsystem.commons.*;
import org.system.experiencegainingsystem.mob.MobFacade;
import org.system.experiencegainingsystem.player.PlayerFacade;

import java.time.Clock;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.random.RandomGenerator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExperienceFacadeTest {

    @Mock
    private PlayerFacade playerFacade;

    @Mock
    private MobFacade mobFacade;

    @Mock
    private RandomGenerator randomGenerator;

    private ExperienceFacade createFacadeAt(int hour, int minute, int second) {
        Clock fixedClock = Clock.fixed(
                LocalDate.of(2026, 1, 1)
                        .atTime(hour, minute, second)
                        .atZone(ZoneId.of("UTC"))
                        .toInstant(), ZoneId.of("UTC")
        );

        return new ExperienceFacade(playerFacade, mobFacade, randomGenerator, fixedClock);
    }

    @Test
    void shouldNotMultiplyUpperBoundWhenPlayerHasNormalRankAndNoScroll() {
        ExperienceFacade facade = createFacadeAt(12, 1, 1);
        Mob mob = new Mob(new Id(), new Name("Slime"), new Experience(15), new Experience(30));
        Player player = new Player(new Id(), new Name("Hero"), new Experience(0), Rank.NORMAL, false);

        when(mobFacade.findById(mob.getMobId())).thenReturn(mob);
        when(playerFacade.findById(player.getPlayerId())).thenReturn(player);
        when(randomGenerator.nextDouble(15.0, 30.0)).thenReturn(20.0);

        Experience expGained = facade.gainExperience(player.getPlayerId(), mob.getMobId());

        assertEquals(new Experience(20), expGained);
        assertEquals(new Experience(20), player.getExperience());

        verify(randomGenerator).nextDouble(15.0, 30.0);
        verify(playerFacade).save(player);
    }

    @Test
    void shouldMultiplyUpperBoundByOnePointFiveWhenPlayerHasVipRank() {
        ExperienceFacade facade = createFacadeAt(12, 1, 1);
        Mob mob = new Mob(new Id(), new Name("Slime"), new Experience(15), new Experience(30));
        Player player = new Player(new Id(), new Name("Hero"), new Experience(0), Rank.VIP, false);

        when(mobFacade.findById(mob.getMobId())).thenReturn(mob);
        when(playerFacade.findById(player.getPlayerId())).thenReturn(player);
        when(randomGenerator.nextDouble(15.0, 45.0)).thenReturn(40.0);

        Experience expGained = facade.gainExperience(player.getPlayerId(), mob.getMobId());

        assertEquals(new Experience(40), expGained);
        assertEquals(new Experience(40), player.getExperience());

        verify(randomGenerator).nextDouble(15.0, 45.0);
        verify(playerFacade).save(player);
    }

    @Test
    void shouldMultiplyUpperBoundByTwoWhenPlayerHasVipPlusRank() {
        ExperienceFacade facade = createFacadeAt(12, 1, 1);
        Mob mob = new Mob(new Id(), new Name("Slime"), new Experience(15), new Experience(30));
        Player player = new Player(new Id(), new Name("Hero"), new Experience(0), Rank.VIP_PLUS, false);

        when(mobFacade.findById(mob.getMobId())).thenReturn(mob);
        when(playerFacade.findById(player.getPlayerId())).thenReturn(player);
        when(randomGenerator.nextDouble(15.0, 60.0)).thenReturn(50.0);

        Experience expGained = facade.gainExperience(player.getPlayerId(), mob.getMobId());

        assertEquals(new Experience(50), expGained);
        assertEquals(new Experience(50), player.getExperience());

        verify(randomGenerator).nextDouble(15.0, 60.0);
        verify(playerFacade).save(player);
    }

    @Test
    void shouldMultiplyUpperBoundByOnePointFiveWhenPlayerHasScroll() {
        ExperienceFacade facade = createFacadeAt(12, 1, 1);
        Mob mob = new Mob(new Id(), new Name("Slime"), new Experience(15), new Experience(30));
        Player player = new Player(new Id(), new Name("Hero"), new Experience(0), Rank.NORMAL, true);

        when(mobFacade.findById(mob.getMobId())).thenReturn(mob);
        when(playerFacade.findById(player.getPlayerId())).thenReturn(player);
        when(randomGenerator.nextDouble(15.0, 45.0)).thenReturn(40.0);

        Experience expGained = facade.gainExperience(player.getPlayerId(), mob.getMobId());

        assertEquals(new Experience(40), expGained);
        assertEquals(new Experience(40), player.getExperience());

        verify(randomGenerator).nextDouble(15.0, 45.0);
        verify(playerFacade).save(player);
    }

    @ParameterizedTest
    @CsvSource({
            "17, 59, 59, 30.0",
            "18,  0,  0, 42.0",
            "18, 30,  0, 42.0",
            "18, 59, 59, 42.0",
            "19,  0,  0, 30.0",
            "22, 59, 59, 30.0",
            "23,  0,  0, 36.0",
            " 0,  0,  0, 36.0",
            " 1, 30,  0, 36.0",
            " 5, 59, 59, 36.0",
            " 6,  0,  0, 30.0"
    })
    void shouldApplyCorrectClockMultiplierAtBoundaryTimes(int hour, int minute, int second, double expectedMax) {
        ExperienceFacade facade = createFacadeAt(hour, minute, second);
        Mob mob = new Mob(new Id(), new Name("Slime"), new Experience(15), new Experience(30));
        Player player = new Player(new Id(), new Name("Hero"), new Experience(0), Rank.NORMAL, false);

        when(mobFacade.findById(mob.getMobId())).thenReturn(mob);
        when(playerFacade.findById(player.getPlayerId())).thenReturn(player);
        when(randomGenerator.nextDouble(eq(15.0), AdditionalMatchers.eq(expectedMax, 0.0001))).thenReturn(25.0);

        Experience expGained = facade.gainExperience(player.getPlayerId(), mob.getMobId());

        assertEquals(new Experience(25), expGained);
        assertEquals(new Experience(25), player.getExperience());

        verify(randomGenerator).nextDouble(eq(15.0), AdditionalMatchers.eq(expectedMax, 0.0001));
        verify(playerFacade).save(player);
    }

    @Test
    void shouldStackRankScrollAndClockMultipliersOnUpperBound() {
        ExperienceFacade facade = createFacadeAt(18, 30, 0);
        Mob mob = new Mob(new Id(), new Name("Slime"), new Experience(15), new Experience(30));
        Player player = new Player(new Id(), new Name("Hero"), new Experience(0), Rank.VIP_PLUS, true);

        when(mobFacade.findById(mob.getMobId())).thenReturn(mob);
        when(playerFacade.findById(player.getPlayerId())).thenReturn(player);
        when(randomGenerator.nextDouble(eq(15.0), AdditionalMatchers.eq(126.0, 0.0001))).thenReturn(100.0);

        Experience expGained = facade.gainExperience(player.getPlayerId(), mob.getMobId());

        assertEquals(new Experience(100), expGained);
        assertEquals(new Experience(100), player.getExperience());

        verify(randomGenerator).nextDouble(eq(15.0), AdditionalMatchers.eq(126.0, 0.0001));
        verify(playerFacade).save(player);
    }

    @Test
    void shouldReturnMinExpWithoutRollingWhenMinEqualsMax() {
        ExperienceFacade facade = createFacadeAt(12, 0, 0);
        Mob mob = new Mob(new Id(), new Name("Slime"), new Experience(30), new Experience(30));
        Player player = new Player(new Id(), new Name("Hero"), new Experience(0), Rank.NORMAL, false);

        when(mobFacade.findById(mob.getMobId())).thenReturn(mob);
        when(playerFacade.findById(player.getPlayerId())).thenReturn(player);

        Experience expGained = facade.gainExperience(player.getPlayerId(), mob.getMobId());

        assertEquals(new Experience(30), expGained);
        assertEquals(new Experience(30), player.getExperience());

        verifyNoInteractions(randomGenerator);
        verify(playerFacade).save(player);
    }

    @Test
    void shouldRollExperienceWhenMobMinEqualsMaxButMultipliersIncreaseUpperBound() {
        ExperienceFacade facade = createFacadeAt(12, 0, 0);
        Mob mob = new Mob(new Id(), new Name("Slime"), new Experience(30), new Experience(30));
        Player player = new Player(new Id(), new Name("Hero"), new Experience(0), Rank.VIP, false);

        when(mobFacade.findById(mob.getMobId())).thenReturn(mob);
        when(playerFacade.findById(player.getPlayerId())).thenReturn(player);
        when(randomGenerator.nextDouble(30.0, 45.0)).thenReturn(38.0);

        Experience expGained = facade.gainExperience(player.getPlayerId(), mob.getMobId());

        assertEquals(new Experience(38), expGained);
        assertEquals(new Experience(38), player.getExperience());

        verify(randomGenerator).nextDouble(30.0, 45.0);
        verify(playerFacade).save(player);
    }
}
