package org.system.experiencegainingsystem.player;

import lombok.RequiredArgsConstructor;
import org.system.experiencegainingsystem.commons.Id;
import org.system.experiencegainingsystem.commons.Player;

@RequiredArgsConstructor
public class PlayerFacade {
    private final PlayerRepository playerRepository;

    public Player findById(Id id) {
        return playerRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Player not found"));
    }

    public Player save(Player player) {
        return playerRepository.save(player);
    }
}
