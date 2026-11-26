package org.system.experiencegainingsystem.player;

import org.system.experiencegainingsystem.commons.Id;
import org.system.experiencegainingsystem.commons.Player;

import java.util.Optional;

interface PlayerRepository {
    Optional<Player> findById(Id id);
    Player save(Player player);
}
