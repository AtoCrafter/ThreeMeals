package ato.threemeals;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.FoodStats;
import net.minecraft.world.World;

public class HardcoreFoodStats extends FoodStats {
    /**
     * 腹が減るスピード、バニラからの倍率
     * Config から変更可能
     */
    public static float FOODEXHAUSTION_SPEED;
    /**
     * 時間経過で腹が減るスピード
     * Config から変更可能
     */
    public static float HARAHERING_SPEED;
    /**
     * 常に難易度がハードモードで空腹度管理にするか
     * Config から変更可能
     */
    public static boolean ALWAYS_HARDMODE;
    /**
     * 肥満度
     * 多いほど、死ににくく燃費が悪くなる
     * [0, 100]: 健康
     * (100, 200]: ぽっちゃり, 燃費が悪い
     * (200, 400]: 肥満, 移動速度低下
     * (400, ): 超肥満, 常時ジャンプできない
     */
    protected float fatness = 0;
    /**
     * 飢餓度 (%)
     * 多いほど、脂肪がつきやすくなる
     */
    protected float starveness = 100;

    @Override
    public void addStats(int foodLevel, float foodSaturationModifier) {
        // 食事による肥満度の増加
        fatness += foodLevel * (starveness / 100) / 10;                         // 栄養の約 10% は常に脂肪生成に回される
        fatness += Math.max(0, getFoodLevel() + foodLevel - 20) * (starveness / 100);   // 満腹度の超過分が脂肪になる
        fatness += Math.max(0, getSaturationLevel() + foodLevel * foodSaturationModifier * 2
                - Math.min(getFoodLevel() + foodLevel, 20)) * (starveness / 100);       // 隠し満腹度の超過分が脂肪になる

        super.addStats(foodLevel, foodSaturationModifier);
    }

    @Override
    public void onUpdate(EntityPlayer entityPlayer) {
        if (ALWAYS_HARDMODE) {
            World world = entityPlayer.worldObj;
            int difficultyBackup = world.difficultySetting;
            world.difficultySetting = 3;
            super.onUpdate(entityPlayer);
            world.difficultySetting = difficultyBackup;
        } else {
            super.onUpdate(entityPlayer);
        }
        harahering();
        // 飢餓度の時間経過による減少
        starveness *= 0.99999;  // 半減期が約 1 時間 (= 3 日)
    }

    @Override
    public void readNBT(NBTTagCompound nbttc) {
        if (nbttc.hasKey("fatness")) {
            fatness = nbttc.getFloat("fatness");
        }
        if (nbttc.hasKey("starveness")) {
            starveness = nbttc.getFloat("starveness");
        }
        super.readNBT(nbttc);
    }

    @Override
    public void writeNBT(NBTTagCompound nbttc) {
        nbttc.setFloat("fatness", fatness);
        nbttc.setFloat("starveness", starveness);
        super.writeNBT(nbttc);
    }

    /**
     * 時間経過腹減りモード
     * 何もしなくても時間経過で腹が減る
     */
    private void harahering() {
        float haraheri = HARAHERING_SPEED / 1000;
        if (getFoodLevel() < 5) {
            // 脂肪による飢餓の回避
            double hungry = Math.min((5 - getFoodLevel()) * 0.25, 1);
            fatness = (float) Math.max(0, fatness - haraheri * hungry);
            if (fatness > 0) haraheri *= 1 - hungry;
            starveness = (float) Math.min(starveness + Math.pow(hungry, 2) / 1000, 100);
        }
        addExhaustion(haraheri);
    }

    @Override
    public void addExhaustion(float exhaustion) {
        // 運動による脂肪の燃焼
        fatness = (float) Math.max(0, fatness - Math.pow(exhaustion, 2) * 2);
        // ハードコア腹減りモード
        // FOODEXHAUSTION_SPEED 倍の速度で腹が減る
        exhaustion *= FOODEXHAUSTION_SPEED;
        // 肥満度による腹減り速度増加
        exhaustion *= Math.max(fatness / 100, 1);

        super.addExhaustion(exhaustion);
    }

    public float getStarveness() {
        return starveness;
    }

    public float getFatness() {
        return fatness;
    }
}
