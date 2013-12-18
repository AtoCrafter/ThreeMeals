package ato.threemeals;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.LivingEvent;

public class PlayerEventHandler {
    /**
     * 肥満による悪影響
     */
    @ForgeSubscribe
    public void fatDebuff(LivingEvent.LivingUpdateEvent event) {
        if (event.entityLiving instanceof EntityPlayerMP) {
            EntityPlayerMP player = (EntityPlayerMP) event.entityLiving;
            if (player.getFoodStats() instanceof HardcoreFoodStats && !player.worldObj.isRemote) {
                HardcoreFoodStats stats = (HardcoreFoodStats) player.getFoodStats();
                if (200 < stats.getFatness()) {
                    int amplifier = (int)((stats.getFatness() - 200) / 50);
                    player.addPotionEffect(new PotionEffect(2, 10 * 20, amplifier));
                }
                if (400 < stats.getFatness()) {
                    player.addPotionEffect(new PotionEffect(8, 10 * 20, 128));
                }
            }
        }
    }
}
