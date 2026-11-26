package org.system.experiencegainingsystem.player;

import org.system.experiencegainingsystem.commons.Id;
import org.system.experiencegainingsystem.commons.Player;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

class InMemoryPlayerRepository implements PlayerRepository {

    private final Map<Id, Player> players = new ConcurrentHashMap<>();

    @Override
    public Optional<Player> findById(Id id) {
        return Optional.ofNullable(players.get(id));
    }

    @Override
    public Player save(Player player) {
        players.put(player.getPlayerId(), player);
        return player;
    }
}
