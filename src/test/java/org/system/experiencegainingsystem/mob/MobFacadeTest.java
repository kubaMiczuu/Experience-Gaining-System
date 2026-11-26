package org.system.experiencegainingsystem.mob;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.system.experiencegainingsystem.commons.Experience;
import org.system.experiencegainingsystem.commons.Id;
import org.system.experiencegainingsystem.commons.Mob;
import org.system.experiencegainingsystem.commons.Name;

import static org.junit.jupiter.api.Assertions.*;

class MobFacadeTest {

    private MobFacade mobFacade;

    @BeforeEach
    void setUp() {
        mobFacade = new MobConfiguration().mobFacade();
    }

    @Test
    void shouldSaveAndFindMobById() {
        Id id = new Id();
        Mob mob = new Mob(id, new Name("Slime"), new Experience(15), new Experience(30));

        mobFacade.save(mob);
        Mob foundMob = mobFacade.findById(id);

        assertEquals(mob, foundMob);
    }

    @Test
    void shouldOverwriteExistingMobWhenSavingWithSameId() {
        Id id = new Id();
        Mob mob = new Mob(id, new Name("Slime"), new Experience(15), new Experience(30));
        mobFacade.save(mob);

        Mob newMob = new Mob(id, new Name("Goblin"), new Experience(150), new Experience(250));
        mobFacade.save(newMob);

        Mob foundMob = mobFacade.findById(id);

        assertEquals(new Name("Goblin"), foundMob.getName());
        assertEquals(new Experience(150), foundMob.getMinExp());
        assertEquals(new Experience(250), foundMob.getMaxExp());
    }

    @Test
    void shouldThrowExceptionWhenMobNotFound() {
        assertThrows(IllegalArgumentException.class, () -> mobFacade.findById(new Id()));
    }

    @Test
    void shouldThrowExceptionWhenFindingMobWithNullId() {
        assertThrows(IllegalArgumentException.class, () -> mobFacade.findById(null));

    }

    @Test
    void shouldThrowExceptionWhenSavingNullMob() {
        assertThrows(IllegalArgumentException.class, () -> mobFacade.save(null));
    }
}
