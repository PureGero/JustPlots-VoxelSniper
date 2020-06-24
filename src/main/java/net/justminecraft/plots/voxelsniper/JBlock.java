package net.justminecraft.plots.voxelsniper;

import net.justminecraft.plots.JustPlots;
import net.justminecraft.plots.Plot;
import org.bukkit.*;
import org.bukkit.block.*;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

import java.util.Collection;
import java.util.List;

public class JBlock implements Block {
    private final JWorld world;
    private final Block block;

    public JBlock(JWorld world, Block block) {
        this.world = world;
        this.block = block;
    }

    private boolean canModifiy() {
        if (JustPlots.isPlotWorld(block.getWorld())) {
            Plot plot = JustPlots.getPlotAt(block.getLocation());

            if (plot == null || !plot.isAdded(world.getPlayer())) {
                return false; // Not their plot
            }
        }

        return true;
    }

    @Override
    public byte getData() {
        throw new UnsupportedOperationException();
    }

    @Override
    public BlockData getBlockData() {
        if (canModifiy()) {
            return block.getBlockData();
        } else {
            return Material.AIR.createBlockData();
        }
    }

    @Override
    public Block getRelative(int modX, int modY, int modZ) {
        return new JBlock(world, block.getRelative(modX, modY, modY));
    }

    @Override
    public Block getRelative(BlockFace face) {
        return new JBlock(world, block.getRelative(face));
    }

    @Override
    public Block getRelative(BlockFace face, int distance) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Material getType() {
        if (canModifiy()) {
            return block.getType();
        } else {
            return Material.AIR;
        }
    }

    @Override
    public byte getLightLevel() {
        throw new UnsupportedOperationException();
    }

    @Override
    public byte getLightFromSky() {
        throw new UnsupportedOperationException();
    }

    @Override
    public byte getLightFromBlocks() {
        throw new UnsupportedOperationException();
    }

    @Override
    public JWorld getWorld() {
        return world;
    }

    @Override
    public int getX() {
        return block.getX();
    }

    @Override
    public int getY() {
        return block.getY();
    }

    @Override
    public int getZ() {
        return block.getZ();
    }

    @Override
    public Location getLocation() {
        Location location = block.getLocation();
        location.setWorld(getWorld());
        return location;
    }

    @Override
    public Location getLocation(Location loc) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Chunk getChunk() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setBlockData(BlockData data) {
        setBlockData(data, true);
    }

    @Override
    public void setBlockData(BlockData data, boolean applyPhysics) {
        if (canModifiy()) {
            block.setBlockData(data, applyPhysics);
        }
    }

    @Override
    public void setType(Material type) {
        setType(type, true);
    }

    @Override
    public void setType(Material type, boolean applyPhysics) {
        setBlockData(type.createBlockData(), applyPhysics);
    }

    @Override
    public BlockFace getFace(Block block) {
        if (block instanceof JBlock) {
            block = ((JBlock) block).block;
        }

        return this.block.getFace(block);
    }

    @Override
    public BlockState getState() {
        return block.getState();
    }

    @Override
    public Biome getBiome() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setBiome(Biome bio) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isBlockPowered() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isBlockIndirectlyPowered() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isBlockFacePowered(BlockFace face) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isBlockFaceIndirectlyPowered(BlockFace face) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getBlockPower(BlockFace face) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getBlockPower() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isEmpty() {
        if (canModifiy()) {
            return block.isEmpty();
        } else {
            return true;
        }
    }

    @Override
    public boolean isLiquid() {
        throw new UnsupportedOperationException();
    }

    @Override
    public double getTemperature() {
        throw new UnsupportedOperationException();
    }

    @Override
    public double getHumidity() {
        throw new UnsupportedOperationException();
    }

    @Override
    public PistonMoveReaction getPistonMoveReaction() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean breakNaturally() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean breakNaturally(ItemStack tool) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Collection<ItemStack> getDrops() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Collection<ItemStack> getDrops(ItemStack tool) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Collection<ItemStack> getDrops(ItemStack tool, Entity entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isPassable() {
        throw new UnsupportedOperationException();
    }

    @Override
    public RayTraceResult rayTrace(Location start, Vector direction, double maxDistance, FluidCollisionMode fluidCollisionMode) {
        throw new UnsupportedOperationException();
    }

    @Override
    public BoundingBox getBoundingBox() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setMetadata(String metadataKey, MetadataValue newMetadataValue) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<MetadataValue> getMetadata(String metadataKey) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean hasMetadata(String metadataKey) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void removeMetadata(String metadataKey, Plugin owningPlugin) {
        throw new UnsupportedOperationException();
    }
}
