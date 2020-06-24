package net.justminecraft.plots.voxelsniper;

import org.bukkit.Chunk;
import org.bukkit.ChunkSnapshot;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.Plugin;

import java.util.Collection;

public class JChunk implements Chunk {
    private final JWorld world;
    private final Chunk chunk;

    public JChunk(JWorld world, Chunk block) {
        this.world = world;
        this.chunk = block;
    }

    @Override
    public int getX() {
        return chunk.getX();
    }

    @Override
    public int getZ() {
        return chunk.getZ();
    }

    @Override
    public World getWorld() {
        return world;
    }

    @Override
    public Block getBlock(int x, int y, int z) {
        return world.getBlockAt(x, y, z);
    }

    @Override
    public ChunkSnapshot getChunkSnapshot() {
        throw new UnsupportedOperationException();
    }

    @Override
    public ChunkSnapshot getChunkSnapshot(boolean includeMaxblocky, boolean includeBiome, boolean includeBiomeTempRain) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Entity[] getEntities() {
        int i, j = 0;
        Entity[] entities = chunk.getEntities();

        for (i = 0; i < entities.length; i ++) {
            if (!world.getBlockAt(entities[i].getLocation()).canModifiy()) {
                System.arraycopy(entities, j + 1, entities, j, entities.length - j - i);
            } else {
                j ++;
            }
        }

        Entity[] newEntities = new Entity[j];
        System.arraycopy(entities, 0, newEntities, 0, newEntities.length);
        return newEntities;
    }

    @Override
    public BlockState[] getTileEntities() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isLoaded() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean load(boolean generate) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean load() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean unload(boolean save) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean unload() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isSlimeChunk() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isForceLoaded() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setForceLoaded(boolean forced) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addPluginChunkTicket(Plugin plugin) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removePluginChunkTicket(Plugin plugin) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Collection<Plugin> getPluginChunkTickets() {
        throw new UnsupportedOperationException();
    }

    @Override
    public long getInhabitedTime() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setInhabitedTime(long ticks) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean contains(BlockData block) {
        throw new UnsupportedOperationException();
    }
}
