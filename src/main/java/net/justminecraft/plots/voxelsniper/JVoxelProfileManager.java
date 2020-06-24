package net.justminecraft.plots.voxelsniper;

import com.google.common.collect.Maps;
import com.thevoxelbox.voxelsniper.VoxelProfileManager;
import com.thevoxelbox.voxelsniper.VoxelSniper;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;

public class JVoxelProfileManager extends VoxelProfileManager {
    private final Map<UUID, JSniper> sniperInstances = Maps.newHashMap();

    @Override
    public JSniper getSniperForPlayer(Player player) {
        if (sniperInstances.get(player.getUniqueId()) == null) {
            sniperInstances.put(player.getUniqueId(), new JSniper(VoxelSniper.getInstance(), player));
        }
        return sniperInstances.get(player.getUniqueId());
    }
}
