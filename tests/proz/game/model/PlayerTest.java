package proz.game.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class PlayerTest {
    private Player player;
    private Bonus bonus;

    @Before
    public void setUP() {
        player = new Player(0, 0);
    }

    @Test
    public void afterTakingDamageHpIsReducedByOne() {
        int originalLifeCount = player.getLives();
        player.takeDamage();
        int delta = originalLifeCount - player.getLives();
        assertEquals(1, delta);
    }

    @Test
    public void afterGettingShieldBonusShieldIsOn() {
        bonus = new Bonus(0, 0, BonusType.shield);
        player.setBonus(bonus);

        assertTrue(player.isShielded());
    }

    @Test
    public void afterGettingPowerBonusPowerUpIsOn() {
        bonus = new Bonus(0, 0, BonusType.power_UP);
        player.setBonus(bonus);

        assertTrue(player.isPoweredUp());
    }

    @Test
    public void afterPlayerLivesBecomesZeroPlayerVisibilityIsSetToFalse() {
        while (player.getLives() > 0) {
            player.takeDamage();
        }

        assertFalse(player.isVisible());
    }
}
