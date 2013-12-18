package ato.threemeals;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.FoodStats;

public class HardcoreFoodStats extends FoodStats {
    /**
     * 腹が減るスピード、バニラからの倍率
     */
    public static final float FOODEXHAUSTION_SPEED = 3;
    /**
     * 時間経過で腹が減るスピード
     */
    public static final float HARAHERING_SPEED = 1;

    @Override
    public void onUpdate(EntityPlayer entityPlayer) {
        super.onUpdate(entityPlayer);
        harahering();
    }

    /**
     * 時間経過腹減りモード
     * 何もしなくても時間経過で腹が減る
     */
    private void harahering() {
        float exhaustion = HARAHERING_SPEED / FOODEXHAUSTION_SPEED / 10000;
        if (getFoodLevel() < 5 ) {
            exhaustion /= Math.pow(5 - getFoodLevel(), 2);
        }
        addExhaustion(exhaustion);
    }

    @Override
    public void addExhaustion(float par1) {
        // ハードコア腹減りモード
        // FOODEXHAUSTION_SPEED 倍の速度で腹が減る
        super.addExhaustion(par1 * FOODEXHAUSTION_SPEED);
    }
}
