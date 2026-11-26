package org.system.experiencegainingsystem.player;

import lombok.RequiredArgsConstructor;
import org.system.experiencegainingsystem.commons.Id;
import org.system.experiencegainingsystem.commons.Player;

@RequiredArgsConstructor
public class PlayerFacade {
    private final PlayerRepository playerRepository;

    public Player findById(Id id) {
        if (id == null) {
            throw new IllegalArgumentException("Player id cannot be null");
        }
        return playerRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Player not found"));
    }

    public Player save(Player player) {
        if (player == null) {
            throw new IllegalArgumentException("Player cannot be null");
        }
        return playerRepository.save(player);
    }
}
