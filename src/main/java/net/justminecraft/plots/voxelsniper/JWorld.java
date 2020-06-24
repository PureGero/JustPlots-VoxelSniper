package net.justminecraft.plots.voxelsniper;

import net.justminecraft.plots.JustPlots;
import net.justminecraft.plots.Plot;
import org.bukkit.*;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.boss.DragonBattle;
import org.bukkit.entity.*;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.Consumer;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

import java.io.File;
import java.util.*;
import java.util.function.Predicate;

public class JWorld implements World {
    private final JPlayer player;

    public JWorld(JPlayer player) {
        this.player = player;
    }

    public JPlayer getPlayer() {
        return player;
    }

    public World getRealWorld() {
        return player.getRealPlayer().getWorld();
    }

    @Override
    public Block getBlockAt(int x, int y, int z) {
        return new JBlock(this, getRealWorld().getBlockAt(x, y, z));
    }

    @Override
    public Block getBlockAt(Location location) {
        return new JBlock(this, getRealWorld().getBlockAt(location));
    }

    @Override
    public int getHighestBlockYAt(int x, int z) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getHighestBlockYAt(Location location) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Block getHighestBlockAt(int x, int z) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Block getHighestBlockAt(Location location) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getHighestBlockYAt(int x, int z, HeightMap heightMap) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getHighestBlockYAt(Location location, HeightMap heightMap) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Block getHighestBlockAt(int x, int z, HeightMap heightMap) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Block getHighestBlockAt(Location location, HeightMap heightMap) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Chunk getChunkAt(int x, int z) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Chunk getChunkAt(Location location) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Chunk getChunkAt(Block block) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isChunkLoaded(Chunk chunk) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Chunk[] getLoadedChunks() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void loadChunk(Chunk chunk) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isChunkLoaded(int x, int z) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isChunkGenerated(int x, int z) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isChunkInUse(int x, int z) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void loadChunk(int x, int z) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean loadChunk(int x, int z, boolean generate) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean unloadChunk(Chunk chunk) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean unloadChunk(int x, int z) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean unloadChunk(int x, int z, boolean save) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean unloadChunkRequest(int x, int z) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean regenerateChunk(int x, int z) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean refreshChunk(int x, int z) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isChunkForceLoaded(int x, int z) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setChunkForceLoaded(int x, int z, boolean forced) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Collection<Chunk> getForceLoadedChunks() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addPluginChunkTicket(int x, int z, Plugin plugin) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removePluginChunkTicket(int x, int z, Plugin plugin) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void removePluginChunkTickets(Plugin plugin) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Collection<Plugin> getPluginChunkTickets(int x, int z) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Map<Plugin, Collection<Chunk>> getPluginChunkTickets() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Item dropItem(Location location, ItemStack item) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Item dropItemNaturally(Location location, ItemStack item) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Arrow spawnArrow(Location location, Vector direction, float speed, float spread) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T extends AbstractArrow> T spawnArrow(Location location, Vector direction, float speed, float spread, Class<T> clazz) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean generateTree(Location location, TreeType type) {
        return generateTree(location, type, null);
    }

    @Override
    public boolean generateTree(Location loc, TreeType type, BlockChangeDelegate delegate) {
        return getRealWorld().generateTree(loc, type, new BlockChangeDelegate() {
            @Override
            public boolean setBlockData(int x, int y, int z, BlockData blockData) {
                if (JustPlots.isPlotWorld(JWorld.this)) {
                    Plot plot = JustPlots.getPlotAt(new Location(JWorld.this, x, y, z));

                    if (plot == null || !plot.isAdded(getPlayer())) {
                        return false; // Not their plot
                    }
                }

                if (delegate == null) {
                    getRealWorld().getBlockAt(x, y, z).setBlockData(blockData);
                    return true;
                } else {
                    return delegate.setBlockData(x, y, z, blockData);
                }
            }

            @Override
            public BlockData getBlockData(int x, int y, int z) {
                return getRealWorld().getBlockAt(x, y, z).getBlockData();
            }

            @Override
            public int getHeight() {
                return getMaxHeight();
            }

            @Override
            public boolean isEmpty(int x, int y, int z) {
                return getRealWorld().getBlockAt(x, y, z).isEmpty();
            }
        });
    }

    @Override
    public Entity spawnEntity(Location loc, EntityType type) {
        throw new UnsupportedOperationException();
    }

    @Override
    public LightningStrike strikeLightning(Location loc) {
        throw new UnsupportedOperationException();
    }

    @Override
    public LightningStrike strikeLightningEffect(Location loc) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Entity> getEntities() {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<LivingEntity> getLivingEntities() {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T extends Entity> Collection<T> getEntitiesByClass(Class<T>... classes) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T extends Entity> Collection<T> getEntitiesByClass(Class<T> cls) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Collection<Entity> getEntitiesByClasses(Class<?>... classes) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Player> getPlayers() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Collection<Entity> getNearbyEntities(Location location, double x, double y, double z) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Collection<Entity> getNearbyEntities(Location location, double x, double y, double z, Predicate<Entity> filter) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Collection<Entity> getNearbyEntities(BoundingBox boundingBox) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Collection<Entity> getNearbyEntities(BoundingBox boundingBox, Predicate<Entity> filter) {
        throw new UnsupportedOperationException();
    }

    @Override
    public RayTraceResult rayTraceEntities(Location start, Vector direction, double maxDistance) {
        throw new UnsupportedOperationException();
    }

    @Override
    public RayTraceResult rayTraceEntities(Location start, Vector direction, double maxDistance, double raySize) {
        throw new UnsupportedOperationException();
    }

    @Override
    public RayTraceResult rayTraceEntities(Location start, Vector direction, double maxDistance, Predicate<Entity> filter) {
        throw new UnsupportedOperationException();
    }

    @Override
    public RayTraceResult rayTraceEntities(Location start, Vector direction, double maxDistance, double raySize, Predicate<Entity> filter) {
        throw new UnsupportedOperationException();
    }

    @Override
    public RayTraceResult rayTraceBlocks(Location start, Vector direction, double maxDistance) {
        throw new UnsupportedOperationException();
    }

    @Override
    public RayTraceResult rayTraceBlocks(Location start, Vector direction, double maxDistance, FluidCollisionMode fluidCollisionMode) {
        throw new UnsupportedOperationException();
    }

    @Override
    public RayTraceResult rayTraceBlocks(Location start, Vector direction, double maxDistance, FluidCollisionMode fluidCollisionMode, boolean ignorePassableBlocks) {
        throw new UnsupportedOperationException();
    }

    @Override
    public RayTraceResult rayTrace(Location start, Vector direction, double maxDistance, FluidCollisionMode fluidCollisionMode, boolean ignorePassableBlocks, double raySize, Predicate<Entity> filter) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getName() {
        return getRealWorld().getName();
    }

    @Override
    public UUID getUID() {
        return getRealWorld().getUID();
    }

    @Override
    public Location getSpawnLocation() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean setSpawnLocation(Location location) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean setSpawnLocation(int x, int y, int z) {
        throw new UnsupportedOperationException();
    }

    @Override
    public long getTime() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setTime(long time) {
        throw new UnsupportedOperationException();
    }

    @Override
    public long getFullTime() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setFullTime(long time) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean hasStorm() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setStorm(boolean hasStorm) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getWeatherDuration() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setWeatherDuration(int duration) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isThundering() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setThundering(boolean thundering) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getThunderDuration() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setThunderDuration(int duration) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean createExplosion(double x, double y, double z, float power) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean createExplosion(double x, double y, double z, float power, boolean setFire) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean createExplosion(double x, double y, double z, float power, boolean setFire, boolean breakBlocks) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean createExplosion(double x, double y, double z, float power, boolean setFire, boolean breakBlocks, Entity source) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean createExplosion(Location loc, float power) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean createExplosion(Location loc, float power, boolean setFire) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean createExplosion(Location loc, float power, boolean setFire, boolean breakBlocks) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean createExplosion(Location loc, float power, boolean setFire, boolean breakBlocks, Entity source) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Environment getEnvironment() {
        throw new UnsupportedOperationException();
    }

    @Override
    public long getSeed() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean getPVP() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setPVP(boolean pvp) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ChunkGenerator getGenerator() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void save() {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<BlockPopulator> getPopulators() {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T extends Entity> T spawn(Location location, Class<T> clazz) throws IllegalArgumentException {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T extends Entity> T spawn(Location location, Class<T> clazz, Consumer<T> function) throws IllegalArgumentException {
        throw new UnsupportedOperationException();
    }

    @Override
    public FallingBlock spawnFallingBlock(Location location, MaterialData data) throws IllegalArgumentException {
        throw new UnsupportedOperationException();
    }

    @Override
    public FallingBlock spawnFallingBlock(Location location, BlockData data) throws IllegalArgumentException {
        throw new UnsupportedOperationException();
    }

    @Override
    public FallingBlock spawnFallingBlock(Location location, Material material, byte data) throws IllegalArgumentException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void playEffect(Location location, Effect effect, int data) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void playEffect(Location location, Effect effect, int data, int radius) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> void playEffect(Location location, Effect effect, T data) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> void playEffect(Location location, Effect effect, T data, int radius) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ChunkSnapshot getEmptyChunkSnapshot(int x, int z, boolean includeBiome, boolean includeBiomeTemp) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setSpawnFlags(boolean allowMonsters, boolean allowAnimals) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean getAllowAnimals() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean getAllowMonsters() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Biome getBiome(int x, int z) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Biome getBiome(int x, int y, int z) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setBiome(int x, int z, Biome bio) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setBiome(int x, int y, int z, Biome bio) {
        throw new UnsupportedOperationException();
    }

    @Override
    public double getTemperature(int x, int z) {
        throw new UnsupportedOperationException();
    }

    @Override
    public double getTemperature(int x, int y, int z) {
        throw new UnsupportedOperationException();
    }

    @Override
    public double getHumidity(int x, int z) {
        throw new UnsupportedOperationException();
    }

    @Override
    public double getHumidity(int x, int y, int z) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getMaxHeight() {
        return getRealWorld().getMaxHeight();
    }

    @Override
    public int getSeaLevel() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean getKeepSpawnInMemory() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setKeepSpawnInMemory(boolean keepLoaded) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isAutoSave() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setAutoSave(boolean value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setDifficulty(Difficulty difficulty) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Difficulty getDifficulty() {
        throw new UnsupportedOperationException();
    }

    @Override
    public File getWorldFolder() {
        throw new UnsupportedOperationException();
    }

    @Override
    public WorldType getWorldType() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean canGenerateStructures() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isHardcore() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setHardcore(boolean hardcore) {
        throw new UnsupportedOperationException();
    }

    @Override
    public long getTicksPerAnimalSpawns() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setTicksPerAnimalSpawns(int ticksPerAnimalSpawns) {
        throw new UnsupportedOperationException();
    }

    @Override
    public long getTicksPerMonsterSpawns() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setTicksPerMonsterSpawns(int ticksPerMonsterSpawns) {
        throw new UnsupportedOperationException();
    }

    @Override
    public long getTicksPerWaterSpawns() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setTicksPerWaterSpawns(int ticksPerWaterSpawns) {
        throw new UnsupportedOperationException();
    }

    @Override
    public long getTicksPerAmbientSpawns() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setTicksPerAmbientSpawns(int ticksPerAmbientSpawns) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getMonsterSpawnLimit() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setMonsterSpawnLimit(int limit) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getAnimalSpawnLimit() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setAnimalSpawnLimit(int limit) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getWaterAnimalSpawnLimit() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setWaterAnimalSpawnLimit(int limit) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getAmbientSpawnLimit() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setAmbientSpawnLimit(int limit) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void playSound(Location location, Sound sound, float volume, float pitch) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void playSound(Location location, String sound, float volume, float pitch) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void playSound(Location location, Sound sound, SoundCategory category, float volume, float pitch) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void playSound(Location location, String sound, SoundCategory category, float volume, float pitch) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String[] getGameRules() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getGameRuleValue(String rule) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean setGameRuleValue(String rule, String value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isGameRule(String rule) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> T getGameRuleValue(GameRule<T> rule) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> T getGameRuleDefault(GameRule<T> rule) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> boolean setGameRule(GameRule<T> rule, T newValue) {
        throw new UnsupportedOperationException();
    }

    @Override
    public WorldBorder getWorldBorder() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void spawnParticle(Particle particle, Location location, int count) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void spawnParticle(Particle particle, double x, double y, double z, int count) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> void spawnParticle(Particle particle, Location location, int count, T data) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> void spawnParticle(Particle particle, double x, double y, double z, int count, T data) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void spawnParticle(Particle particle, Location location, int count, double offsetX, double offsetY, double offsetZ) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void spawnParticle(Particle particle, double x, double y, double z, int count, double offsetX, double offsetY, double offsetZ) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> void spawnParticle(Particle particle, Location location, int count, double offsetX, double offsetY, double offsetZ, T data) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> void spawnParticle(Particle particle, double x, double y, double z, int count, double offsetX, double offsetY, double offsetZ, T data) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void spawnParticle(Particle particle, Location location, int count, double offsetX, double offsetY, double offsetZ, double extra) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void spawnParticle(Particle particle, double x, double y, double z, int count, double offsetX, double offsetY, double offsetZ, double extra) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> void spawnParticle(Particle particle, Location location, int count, double offsetX, double offsetY, double offsetZ, double extra, T data) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> void spawnParticle(Particle particle, double x, double y, double z, int count, double offsetX, double offsetY, double offsetZ, double extra, T data) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> void spawnParticle(Particle particle, Location location, int count, double offsetX, double offsetY, double offsetZ, double extra, T data, boolean force) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> void spawnParticle(Particle particle, double x, double y, double z, int count, double offsetX, double offsetY, double offsetZ, double extra, T data, boolean force) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Location locateNearestStructure(Location origin, StructureType structureType, int radius, boolean findUnexplored) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getViewDistance() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Spigot spigot() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Raid locateNearestRaid(Location location, int radius) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Raid> getRaids() {
        throw new UnsupportedOperationException();
    }

    @Override
    public DragonBattle getEnderDragonBattle() {
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

    @Override
    public void sendPluginMessage(Plugin source, String channel, byte[] message) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Set<String> getListeningPluginChannels() {
        throw new UnsupportedOperationException();
    }
}
