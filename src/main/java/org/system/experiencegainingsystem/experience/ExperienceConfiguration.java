package org.system.experiencegainingsystem.experience;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.system.experiencegainingsystem.mob.MobFacade;
import org.system.experiencegainingsystem.player.PlayerFacade;

import java.time.Clock;
import java.util.random.RandomGenerator;

@RequiredArgsConstructor
@Configuration
public class ExperienceConfiguration {

    private final PlayerFacade playerFacade;
    private final MobFacade mobFacade;

    @Bean
    public ExperienceFacade experienceFacade() {
        return new ExperienceFacade(playerFacade, mobFacade, RandomGenerator.getDefault(), Clock.systemDefaultZone());
    }
}
