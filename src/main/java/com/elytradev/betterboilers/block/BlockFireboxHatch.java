package com.elytradev.betterboilers.block;

import com.elytradev.betterboilers.BetterBoilers;
import com.elytradev.betterboilers.tile.TileEntityBoilerFireboxHatch;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.world.World;

public class BlockFireboxHatch extends BlockTileEntity<TileEntityBoilerFireboxHatch> implements IBoilerBlock {

    protected String name;

    public BlockFireboxHatch() {
        super(Material.ROCK, "firebox_hatch");

        setCreativeTab(BetterBoilers.creativeTab);
    }

    @Override
    public Class<TileEntityBoilerFireboxHatch> getTileEntityClass() {
        return TileEntityBoilerFireboxHatch.class;
    }

    @Override
    public TileEntityBoilerFireboxHatch createTileEntity(World world, IBlockState state) {
        return new TileEntityBoilerFireboxHatch();
    }
}
