package ato.threemeals;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.LivingEvent;

public class PlayerEventHandler {
    private float fatness;
    private float starveness;

    /**
     * 肥満による悪影響
     */
    @ForgeSubscribe
    public void fatDebuff(LivingEvent.LivingUpdateEvent event) {
        if (event.entityLiving instanceof EntityPlayerMP) {
            EntityPlayerMP player = (EntityPlayerMP) event.entityLiving;
            if (player.getFoodStats() instanceof HardcoreFoodStats && !player.worldObj.isRemote) {
                HardcoreFoodStats stats = (HardcoreFoodStats) player.getFoodStats();
                fatness = stats.getFatness();
                starveness = stats.getStarveness();

                if (200 < stats.getFatness()) {
                    int amplifier = (int) ((stats.getFatness() - 200) / 50);
                    player.addPotionEffect(new PotionEffect(2, 10 * 20, amplifier));
                }
                if (400 < stats.getFatness()) {
                    player.addPotionEffect(new PotionEffect(8, 10 * 20, 128));
                }
            }
        }
    }

    /**
     * 空腹度に関する情報を表示
     */
    @ForgeSubscribe
    public void renderThreeMealsInfo(RenderGameOverlayEvent.Text event) {
        if (Minecraft.getMinecraft().gameSettings.showDebugInfo) {
            event.left.add("Fatness: " + fatness);
            event.left.add("Starveness: " + starveness);
        }
    }
}
