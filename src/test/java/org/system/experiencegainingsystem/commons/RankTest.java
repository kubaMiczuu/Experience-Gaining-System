package org.system.experiencegainingsystem.commons;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RankTest {

    @Test
    void shouldReturnOneAsMultiplierForNormalRank() {
        assertEquals(1.0, Rank.NORMAL.getMultiplier());
    }

    @Test
    void shouldReturnOnePointFiveAsMultiplierForVipRank() {
        assertEquals(1.5, Rank.VIP.getMultiplier());
    }

    @Test
    void shouldReturnTwoAsMultiplierForVipPlusRank() {
        assertEquals(2.0, Rank.VIP_PLUS.getMultiplier());
    }
}
