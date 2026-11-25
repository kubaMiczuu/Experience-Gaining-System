package org.system.experiencegainingsystem.commons;

public enum Rank {
    NORMAL(1.0), VIP(1.5), VIP_PLUS(2.0);

    private final double multiplier;

    Rank(double multiplier) {
        this.multiplier = multiplier;
    }

    public double getMultiplier() {
        return multiplier;
    }
}
