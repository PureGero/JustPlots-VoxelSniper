package net.justminecraft.plots.voxelsniper;

import com.thevoxelbox.voxelsniper.VoxelProfileManager;
import com.thevoxelbox.voxelsniper.VoxelSniper;
import com.thevoxelbox.voxelsniper.event.SniperBrushSizeChangedEvent;
import com.thevoxelbox.voxelsniper.snipe.SnipeData;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;
import java.util.HashMap;

public class JustPlotsVoxelSniper extends JavaPlugin implements Listener {

    private static HashMap<Player, JPlayer> playerCache = new HashMap<>();

    public static int MAX_BRUSH_SIZE = 25;

    public static JPlayer getJPlayer(Player player) {
        return playerCache.computeIfAbsent(player, JPlayer::new);
    }

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);

        saveDefaultConfig();
        getConfig().options().copyDefaults(true);
        MAX_BRUSH_SIZE = getConfig().getInt("max-brush-size");

        // Override the voxel profile manager
        injectVoxelProfileManager();

        // Let us handle the interact event ;)
        PlayerInteractEvent.getHandlerList().unregister(VoxelSniper.getInstance());
    }

    private void injectVoxelProfileManager() {
        try {
            Field instance = VoxelProfileManager.class.getDeclaredField("instance");
            instance.setAccessible(true);
            instance.set(null, new JVoxelProfileManager());
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onQuit(PlayerQuitEvent event) {
        playerCache.remove(event.getPlayer());
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (!player.hasPermission("voxelsniper.sniper")) {
            return;
        }

        try {
            JSniper sniper = (JSniper) VoxelProfileManager.getInstance().getSniperForPlayer(player);
            JBlock clickedBlock = event.getClickedBlock() == null ? null : new JBlock(sniper.getPlayer().getWorld(), event.getClickedBlock());
            if (sniper.isEnabled() && sniper.snipe(event.getAction(), event.getMaterial(), clickedBlock, event.getBlockFace())) {
                event.setCancelled(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @EventHandler
    public void onSniperBrushSizeChange(SniperBrushSizeChangedEvent event) {
        if (event.getNewSize() > MAX_BRUSH_SIZE && MAX_BRUSH_SIZE > 0) {
            event.getSniper().getPlayer().sendMessage(ChatColor.RED + "[JustPlots-VoxelSniper] The maximum brush size is " + MAX_BRUSH_SIZE);

            String currentToolId = event.getSniper().getCurrentToolId();
            SnipeData snipeData = event.getSniper().getSnipeData(currentToolId);
            snipeData.setBrushSize(MAX_BRUSH_SIZE);
        }
    }
}
