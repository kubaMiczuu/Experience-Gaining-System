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
        this.playerId = playerId;
        this.name = name;
        this.experience = experience;
        this.rank = rank;
        this.hasScroll = hasScroll;

        if(hasScroll) scrollMultiplier = 1.5;
        else scrollMultiplier = 1.0;
    }

    public void gainExperience(Experience experience) {
        this.experience = this.experience.add(experience);
    }
}
