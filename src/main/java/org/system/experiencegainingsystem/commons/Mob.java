package org.system.experiencegainingsystem.commons;

import lombok.Getter;

@Getter
public class Mob {
    private final Id mobId;
    private final Name name;
    private final Experience minExp;
    private final Experience maxExp;

    public Mob(Id mobId, Name name, Experience minExp, Experience maxExp) {
        if (mobId == null || name == null || minExp == null || maxExp == null) {
            throw new IllegalArgumentException("Mob fields cannot be null");
        }
        if (minExp.getExperience() > maxExp.getExperience()) {
            throw new IllegalArgumentException("minExp cannot be greater than maxExp");
        }
        this.mobId = mobId;
        this.name = name;
        this.minExp = minExp;
        this.maxExp = maxExp;
    }
}
