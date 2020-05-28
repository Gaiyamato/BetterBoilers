package com.elytradev.betterboilers.tile.turbine;

import com.elytradev.betterboilers.BBLog;
import com.elytradev.betterboilers.block.turbine.BlockTurbineController;
import com.elytradev.betterboilers.block.turbine.ITurbineBlock;
import com.elytradev.betterboilers.block.ModBlocks;
import com.elytradev.betterboilers.tile.TileEntityMultiblockController;
import com.elytradev.betterboilers.util.BBConfig;
import com.elytradev.betterboilers.util.ObservableEnergyStorage;
import com.elytradev.concrete.inventory.ConcreteFluidTank;
import com.elytradev.concrete.inventory.ConcreteItemStorage;
import com.elytradev.concrete.inventory.IContainerInventoryHolder;
import com.elytradev.concrete.inventory.ValidatedInventoryView;
import com.google.common.base.Predicates;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.IInventory;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.fluids.FluidStack;

import java.util.List;
import java.util.function.BiPredicate;

public class TileEntityTurbineController extends TileEntityMultiblockController implements ITickable, IContainerInventoryHolder, ITurbinePart {

    public ConcreteFluidTank tankSteam;
    public ObservableEnergyStorage energyStorage;
    public ConcreteItemStorage inv;
    private int chamberBlockCount = 0;
    private int capBlockCount = 0;
    public int rotorCount = 0;
    private static final int RESCAN_TIME = 100;
    private int currentScanTime = 100;
    private int steamPerGen = 40;

    protected int getMaxBlocksPerMultiblock() { return BBConfig.defaultMaxMultiblock; }

    public TileEntityTurbineController getController() {
        return this;
    }

    public void setController(TileEntityTurbineController controller) {
    }

    public TileEntityTurbineController() {
        this.tankSteam = new ConcreteFluidTank(500).withFillValidator((it)->(it.getFluid() == ModBlocks.FLUID_STEAM));
        this.energyStorage = new ObservableEnergyStorage(100_000, 0, BBConfig.turbineOut);
        this.inv = new ConcreteItemStorage(0).withName(ModBlocks.TURBINE_CONTROLLER.getUnlocalizedName() + ".name");
        tankSteam.listen(this::markDirty);
        energyStorage.listen(this::markDirty);
    }

    @Override
    public void update() {
        if (currentScanTime >= RESCAN_TIME) {
            BiPredicate<World, BlockPos> members = (world, pos)->world.getBlockState(pos).getBlock() instanceof ITurbineBlock;
            scanNetwork(members, this::isValid);
            currentScanTime = 0;
        }
        currentScanTime++;

        if (!world.isRemote) {
            if (rotorCount > 0) {
                if (canProcess()) {

                    tankSteam.drain(steamPerGen, true);
                    energyStorage.generateEnergy(2 * steamPerGen, false);
                }
            }
        }
    }

    public boolean isValid(World world, List<BlockPos> blocks) {
        int maxY = 0;
        int minY = world.getActualHeight()-1;
        int maxX = Integer.MIN_VALUE;
        int minX = Integer.MAX_VALUE;
        int maxZ = Integer.MIN_VALUE;
        int minZ = Integer.MAX_VALUE;
        int validBlockCount = 0;
        int capCount = 0;
        int scanRotorCount = 0;
        //find the bounds of the multiblock
        for(BlockPos pos : blocks){
            maxY = Math.max(pos.getY(), maxY);
            minY = Math.min(pos.getY(), minY);
            maxX = Math.max(pos.getX(), maxX);
            minX = Math.min(pos.getX(), minX);
            maxZ = Math.max(pos.getZ(), maxZ);
            minZ = Math.min(pos.getZ(), minZ);
        }
        //controller must be on the top layer
        if (this.pos.getY() == maxY) {
            status = "msg.bb.badTurbineController";
            return false;
        }

        for (BlockPos pos : blocks) {
            //can only have one controller
            if (world.getTileEntity(pos) instanceof TileEntityMultiblockController) {
                if (pos != this.getPos()) {
                    status = "msg.bb.tooManyControllers";
                    return false;
                }
                validBlockCount++;
            }
            //chamber blocks (including pressure valves and power taps) must not be on the top layer
            if (world.getBlockState(pos).getBlock() == ModBlocks.CHAMBER
                    || world.getBlockState(pos).getBlock() == ModBlocks.PRESSURE_VALVE
                    || world.getBlockState(pos).getBlock() == ModBlocks.POWER_TAP) {
                if (pos.getY() == maxY) {
                    status = "msg.bb.badChamber";
                    return false;
                }
                validBlockCount++;
            }
            //gaskets must be in a solid ring around the outside of the top layer (TODO: support for circular gaskets?)
            if (world.getBlockState(pos).getBlock() == ModBlocks.GASKET) {
                if (pos.getY() != maxY) {
                    status = "msg.bb.badGasket";
                    return false;
                }
                if (!(pos.getX() == maxX
                        || pos.getX() == minX
                        || pos.getZ() == maxZ
                        || pos.getZ() == minZ)) {
                    status = "msg.bb.badGasket";
                    return false;
                }
                validBlockCount++;
            }
            //caps must be on top layer
            if (world.getBlockState(pos).getBlock() == ModBlocks.CAP) {
                if (pos.getY() != maxY) {
                    status = "msg.bb.badCap";
                    return false;
                }
                capCount++;
                validBlockCount++;
            }
            //rotors must be on top layer
            if (world.getBlockState(pos).getBlock() == ModBlocks.ROTOR) {
                if (pos.getY() != maxY) {
                    status = "msg.bb.badCap";
                    return false;
                }
                scanRotorCount++;
                validBlockCount++;
            }
        }
        //verify that the gasket is in a full ring
        for (int i = minX; i <= maxX; i++) {
            BlockPos pos1 = new BlockPos(i, maxY, maxZ);
            BlockPos pos2 = new BlockPos(i, maxY, minZ);
            if (world.getBlockState(pos1).getBlock() != ModBlocks.GASKET) {
                status = "msg.bb.badGasket";
                return false;
            }
            if (world.getBlockState(pos2).getBlock() != ModBlocks.GASKET) {
                status = "msg.bb.badGasket";
                return false;
            }
        }
        for (int i = minZ; i <= maxZ; i++) {
            BlockPos pos1 = new BlockPos(maxX, maxY, i);
            BlockPos pos2 = new BlockPos(minX, maxY, i);
            if (world.getBlockState(pos1).getBlock() != ModBlocks.GASKET) {
                status = "msg.bb.badGasket";
                return false;
            }
            if (world.getBlockState(pos2).getBlock() != ModBlocks.GASKET) {
                status = "msg.bb.badGasket";
                return false;
            }
        }
        //can't have more than half the cap be rotors
        if (scanRotorCount > capCount) {
            status = "msg.bb.tooManyRotors";
            return false;
        }
        //must be at least the minimum size
        if (validBlockCount < BBConfig.defaultMinMultiblock) {
            status = "msg.bb.tooSmall";
            BBLog.info(validBlockCount);
            return false;
        }
        //ok!
        return true;
    }

    @Override
    public void onAssemble(World world, List<BlockPos> blocks) {
        chamberBlockCount = 0;
        capBlockCount = 0;
        rotorCount = 0;
        for (BlockPos pos : blocks) {
            if (world.getBlockState(pos).getBlock() == ModBlocks.CHAMBER
                    || world.getBlockState(pos).getBlock() == ModBlocks.PRESSURE_VALVE
                    || world.getBlockState(pos).getBlock() == ModBlocks.TURBINE_CONTROLLER) {
                chamberBlockCount++;
            }
            if ( world.getBlockState(pos).getBlock() == ModBlocks.CAP
                    || world.getBlockState(pos).getBlock() == ModBlocks.POWER_TAP) {
                capBlockCount++;
            }
            if (world.getBlockState(pos).getBlock() == ModBlocks.ROTOR) {
                capBlockCount++;
                rotorCount++;
            }
            TileEntity te = world.getTileEntity(pos);
            if (te != null && te instanceof TileEntityTurbinePart) {
                ((TileEntityTurbinePart)te).setController(this);
            }
        }
        //curve for diminishing returns on amount of rotors, based around rotorBaseCount
        steamPerGen = (int)(Math.log10(rotorCount*(9/BBConfig.rotorBaseCount+0.5)) * BBConfig.steamBaseUse);
//        BBLog.info("Rotor count: "+rotorCount);
//        BBLog.info("Log result: "+Math.log10(rotorCount*(9/BBConfig.rotorBaseCount+0.5)));
//        BBLog.info("Steam use: "+steamPerGen);
        tankSteam.setCapacity(500*chamberBlockCount);
        markDirty();
    }

    @Override
    public void onDisassemble(World world, List<BlockPos> blocks) {
        for (BlockPos pos : blocks) {
            TileEntity te = world.getTileEntity(pos);
            if (te instanceof TileEntityTurbinePart) {
                ((TileEntityTurbinePart) te).setController(null);
            }
        }
        rotorCount = 0;
        markDirty();
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        NBTTagCompound tag = super.writeToNBT(compound);
        tag.setInteger("ChamberCount", chamberBlockCount);
        tag.setInteger("CapCount", capBlockCount);
        tag.setInteger("RotorCount", rotorCount);
        tag.setTag("SteamTank", tankSteam.writeToNBT(new NBTTagCompound()));
        NBTBase energyTag = CapabilityEnergy.ENERGY.getStorage().writeNBT(CapabilityEnergy.ENERGY, energyStorage, null);
        tag.setTag("energy", energyTag);
        return tag;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        chamberBlockCount = compound.getInteger("ChamberCount");
        capBlockCount = compound.getInteger("CapCount");
        rotorCount = compound.getInteger("TurbineCount");
        tankSteam.setCapacity(500 * capBlockCount);
        tankSteam.readFromNBT(compound.getCompoundTag("SteamTank"));
        NBTBase energyTag = compound.getTag("energy");
        if (energyTag!=null) {
            try {
                CapabilityEnergy.ENERGY.getStorage().readNBT(CapabilityEnergy.ENERGY, energyStorage, null, energyTag);
            } catch (Throwable t) {}
        }
    }

    @Override
    public NBTTagCompound getUpdateTag() {
        return writeToNBT(new NBTTagCompound());
    }

    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        return new SPacketUpdateTileEntity(getPos(), 0, getUpdateTag());
    }

    @Override
    public void handleUpdateTag(NBTTagCompound tag) {
        readFromNBT(tag);
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        handleUpdateTag(pkt.getNbtCompound());
    }

    @Override
    public void markDirty() {
        super.markDirty();
        // again, I've copy-pasted this like 12 times, should probably go into Concrete
        if (!hasWorld() || getWorld().isRemote) return;
        WorldServer ws = (WorldServer)getWorld();
        Chunk c = getWorld().getChunkFromBlockCoords(getPos());
        SPacketUpdateTileEntity packet = new SPacketUpdateTileEntity(getPos(), 0, getUpdateTag());
        for (EntityPlayerMP player : getWorld().getPlayers(EntityPlayerMP.class, Predicates.alwaysTrue())) {
            if (ws.getPlayerChunkMap().isPlayerWatchingChunk(player, c.x, c.z)) {
                player.connection.sendPacket(packet);
            }
        }
    }

    private boolean canProcess() {
        FluidStack tankDrained = tankSteam.drain(steamPerGen, false);
        int powerGen = energyStorage.generateEnergy(2*steamPerGen, true);
        return (tankDrained != null && powerGen == 2*steamPerGen);
    }

    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
        if (oldState.getBlock()==newState.getBlock()) return false;
        else return super.shouldRefresh(world, pos, oldState, newState);
    }

    @Override
    public IInventory getContainerInventory() {
        ValidatedInventoryView view = new ValidatedInventoryView(inv);
        if(world.isRemote) {
            return view;
        }
        else {
            return view.withField(0, () -> energyStorage.getEnergyStored())
                    .withField(1, () -> energyStorage.getMaxEnergyStored())
                    .withField(2, () -> 2*steamPerGen);
        }
    }

    public ConcreteFluidTank getTankSteam() {
        return tankSteam;
    }

    public ObservableEnergyStorage getEnergyStorage() {
        return energyStorage;
    }

    public void setControllerStatus(ControllerStatus state, String status) {
        errorReason = new TextComponentTranslation(status);
        if (state == ControllerStatus.ERRORED) {
            world.setBlockState(this.getPos(), ModBlocks.TURBINE_CONTROLLER.getDefaultState().withProperty(BlockTurbineController.ACTIVE, false));
        } else {
            world.setBlockState(this.getPos(), ModBlocks.TURBINE_CONTROLLER.getDefaultState().withProperty(BlockTurbineController.ACTIVE, true));
        }
    }
}

