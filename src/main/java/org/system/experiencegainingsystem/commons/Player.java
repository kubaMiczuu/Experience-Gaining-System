package org.system.experiencegainingsystem.commons;

import lombok.Getter;

@Getter
public class Player {
    private final Id playerId;
    private final Name name;
    private Experience experience;
    private final Rank rank;
    private final boolean hasScroll;
    private final double scrollMultiplier;

    public Player(Id playerId, Name name, Experience experience, Rank rank, boolean hasScroll) {
        if (playerId == null || name == null || experience == null || rank == null) {
            throw new IllegalArgumentException("Player fields cannot be null");
        }
        this.playerId = playerId;
        this.name = name;
        this.experience = experience;
        this.rank = rank;
        this.hasScroll = hasScroll;

        if(hasScroll) scrollMultiplier = 1.5;
        else scrollMultiplier = 1.0;
    }

    public void gainExperience(Experience experience) {
        if (experience == null) {
            throw new IllegalArgumentException("Experience cannot be null");
        }
        this.experience = this.experience.add(experience);
    }
}
