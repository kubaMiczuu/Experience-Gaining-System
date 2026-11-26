package org.system.experiencegainingsystem.commons;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MobTest {

    @Test
    void shouldCreateMobWithProvidedFields() {
        Id mobId = new Id();
        Name name = new Name("Goblin");
        Experience minExp = new Experience(150);
        Experience maxExp = new Experience(250);

        Mob mob = new Mob(mobId, name, minExp, maxExp);

        assertEquals(mobId, mob.getMobId());
        assertEquals(name, mob.getName());
        assertEquals(minExp, mob.getMinExp());
        assertEquals(maxExp, mob.getMaxExp());
    }

    @Test
    void shouldAllowMobWhenMinExpEqualsMaxExp() {
        Mob mob = new Mob(new Id(), new Name("Slime"), new Experience(15), new Experience(30));

        assertEquals(new Experience(15), mob.getMinExp());
        assertEquals(new Experience(30), mob.getMaxExp());
    }

    @Test
    void shouldThrowExceptionWhenMinExpIsGreaterThanMaxExp() {
        assertThrows(IllegalArgumentException.class, () -> new Mob(new Id(), new Name("Goblin"), new Experience(150), new Experience(100)));
    }

    @Test
    void shouldThrowExceptionWhenRequiredFieldIsNull() {
        Id mobId = new Id();
        Name name = new Name("Goblin");
        Experience minExp = new Experience(150);
        Experience maxExp = new Experience(250);

        assertThrows(IllegalArgumentException.class, () -> new Mob(null, name, minExp, maxExp));
        assertThrows(IllegalArgumentException.class, () -> new Mob(mobId, null, minExp, maxExp));
        assertThrows(IllegalArgumentException.class, () -> new Mob(mobId, name, null, maxExp));
        assertThrows(IllegalArgumentException.class, () -> new Mob(mobId, name, minExp, null));
    }
}
