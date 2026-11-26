package org.system.experiencegainingsystem.mob;

import org.system.experiencegainingsystem.commons.Id;
import org.system.experiencegainingsystem.commons.Mob;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

class InMemoryMobRepository implements MobRepository {

    private final Map<Id, Mob> mobs = new ConcurrentHashMap<>();

    @Override
    public Optional<Mob> findById(Id id) {
        return Optional.ofNullable(mobs.get(id));
    }

    @Override
    public Mob save(Mob mob) {
        mobs.put(mob.getMobId(), mob);
        return mob;
    }
}
