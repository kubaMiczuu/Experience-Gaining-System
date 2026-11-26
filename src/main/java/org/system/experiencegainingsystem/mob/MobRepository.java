package org.system.experiencegainingsystem.mob;

import org.system.experiencegainingsystem.commons.Id;
import org.system.experiencegainingsystem.commons.Mob;

import java.util.Optional;

interface MobRepository {
    Optional<Mob> findById(Id id);
    Mob save(Mob mob);
}
