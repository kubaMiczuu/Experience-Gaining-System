package org.system.experiencegainingsystem.player;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PlayerConfiguration {

    @Bean
    public PlayerFacade playerFacade() {
        return new PlayerFacade(new InMemoryPlayerRepository());
    }
}
