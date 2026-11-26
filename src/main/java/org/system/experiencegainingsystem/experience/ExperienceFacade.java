package org.system.experiencegainingsystem.experience;

import lombok.RequiredArgsConstructor;
import org.system.experiencegainingsystem.commons.Experience;
import org.system.experiencegainingsystem.commons.Id;
import org.system.experiencegainingsystem.commons.Mob;
import org.system.experiencegainingsystem.commons.Player;
import org.system.experiencegainingsystem.mob.MobFacade;
import org.system.experiencegainingsystem.player.PlayerFacade;

import java.time.Clock;
import java.time.LocalTime;
import java.util.random.RandomGenerator;

@RequiredArgsConstructor
public class ExperienceFacade {

    private final PlayerFacade playerFacade;
    private final MobFacade mobFacade;
    private final RandomGenerator randomGenerator;
    private final Clock clock;

    public Experience gainExperience(Id playerId, Id mobId) {

        Player player = playerFacade.findById(playerId);
        Mob mob = mobFacade.findById(mobId);

        double rankMultiplier = player.getRank().getMultiplier();
        double scrollMultiplier = player.getScrollMultiplier();
        double clockMultiplier = calculateClockMultiplier(clock);

        Experience minValue = mob.getMinExp();
        Experience maxValue = mob.getMaxExp().multiply(rankMultiplier).multiply(scrollMultiplier).multiply(clockMultiplier);

        Experience experienceToGain = randomizeExperience(minValue, maxValue);
        player.gainExperience(experienceToGain);
        playerFacade.save(player);

        return experienceToGain;
    }

    private Experience randomizeExperience(Experience min, Experience max) {
        double lower = min.getExperience();
        double upper = max.getExperience();

        if (lower > upper) {
            throw new IllegalArgumentException("min cannot be greater than max");
        }
        if (Double.compare(lower, upper) == 0) {
            return new Experience(lower);
        }

        double rolled = randomGenerator.nextDouble(lower, upper);
        return new Experience(rolled);
    }

    private double calculateClockMultiplier(Clock clock) {
        LocalTime now = LocalTime.now(clock);

        if(!now.isBefore(LocalTime.of(18,0, 0)) && now.isBefore(LocalTime.of(19,0,0))) {
            return 1.4;
        } else if(!now.isBefore(LocalTime.of(23,0,0)) || now.isBefore(LocalTime.of(6, 0))) {
            return 1.2;
        } else return 1.0;
    }
}
