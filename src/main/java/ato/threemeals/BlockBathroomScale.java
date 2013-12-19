package ato.threemeals;

import net.minecraft.block.BlockBasePressurePlate;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import java.util.List;

public class BlockBathroomScale extends BlockBasePressurePlate {
    protected BlockBathroomScale(int id) {
        super(id, "emerald_block", Material.iron);
        setHardness(0.5F);
        setStepSound(soundMetalFootstep);
    }

    @Override
    protected int getPlateState(World world, int i, int j, int k) {
        float weight = 0;

        List<Entity> list = world.getEntitiesWithinAABB(EntityPlayer.class, this.getSensitiveAABB(i, j, k));
        if (list != null && !list.isEmpty()) {
            for (Entity entity : list) {
                if (entity instanceof EntityPlayer) {
                    weight += ((HardcoreFoodStats)((EntityPlayer) entity).getFoodStats()).getFatness();
                }
            }
        }

        return (int) Math.min(weight / 10, 15);
    }

    @Override
    protected int getPowerSupply(int i) {
        return i;
    }

    @Override
    protected int getMetaFromWeight(int i) {
        return i;
    }
}
