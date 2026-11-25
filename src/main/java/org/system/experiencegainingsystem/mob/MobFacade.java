package org.system.experiencegainingsystem.mob;

import lombok.RequiredArgsConstructor;
import org.system.experiencegainingsystem.commons.Id;
import org.system.experiencegainingsystem.commons.Mob;

@RequiredArgsConstructor
public class MobFacade {

    private final MobRepository mobRepository;

    public Mob findById(Id id) {
        return mobRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Mob not found"));
    }

    public Mob save(Mob mob) {
        return mobRepository.save(mob);
    }
}
