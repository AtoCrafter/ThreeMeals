package ato.threemeals;

import net.minecraft.block.BlockBasePressurePlate;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;

public class BlockBathroomScale extends BlockBasePressurePlate {
    protected BlockBathroomScale(int id) {
        super(id, "planks_oak", Material.piston);
    }

    @Override
    protected int getPlateState(World world, int i, int j, int k) {
        return 0;
    }

    @Override
    protected int getPowerSupply(int i) {
        return 0;
    }

    @Override
    protected int getMetaFromWeight(int i) {
        return 0;
    }
}
