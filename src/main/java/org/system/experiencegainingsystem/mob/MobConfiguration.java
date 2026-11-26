package org.system.experiencegainingsystem.mob;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MobConfiguration {

    @Bean
    public MobFacade mobFacade() {
        return new MobFacade(new InMemoryMobRepository());
    }
}
