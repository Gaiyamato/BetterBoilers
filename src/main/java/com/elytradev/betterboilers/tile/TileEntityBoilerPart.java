package com.elytradev.betterboilers.tile;

import com.elytradev.betterboilers.BBLog;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public abstract class TileEntityBoilerPart extends TileEntity implements IMultiblockPart {
    private TileEntityBoilerController controller;
    private Vec3i controllerPos;

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        if (controllerPos != null) {
            compound.setInteger("ControllerOffsetX", controllerPos.getX());
            compound.setInteger("ControllerOffsetY", controllerPos.getY());
            compound.setInteger("ControllerOffsetZ", controllerPos.getZ());
        }
        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        if (compound.hasKey("ControllerOffsetX")) {
            controllerPos = new Vec3i(
                    compound.getInteger("ControllerOffsetX"),
                    compound.getInteger("ControllerOffsetY"),
                    compound.getInteger("ControllerOffsetZ"));
        }
    }

    public boolean hasController() {
        return getController() != null;
    }

    public TileEntityBoilerController getController() {
        if (!hasWorld()) return null;
        if (controller != null && controller.isInvalid()) controller = null;
        if (controller == null && controllerPos != null) {
            BlockPos pos = getPos().add(controllerPos);
            TileEntity te = getWorld().getTileEntity(pos);
            if (te instanceof TileEntityBoilerController) {
                controller = (TileEntityBoilerController)te;
            } else {
                controllerPos = null;
                BBLog.debug("The network member at {}, {}, {} failed to find its controller", getPos().getX(), getPos().getY(), getPos().getZ());
            }
        }
        return controller;
    }

    @Override
    public void setController(TileEntityBoilerController controller) {
        if (!hasWorld()) return;
        if (controller == null) {
            controllerPos = null;
        } else {
            controllerPos = controller.getPos().subtract(getPos());
        }
        this.controller = controller;
    }

    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
        return oldState.getBlock() != newState.getBlock();
    }

    public void handleNeighborChange(IBlockAccess world, BlockPos pos, BlockPos neighbor) {

    }
}
