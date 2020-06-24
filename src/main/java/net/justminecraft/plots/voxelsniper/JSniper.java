package net.justminecraft.plots.voxelsniper;

import com.thevoxelbox.voxelsniper.VoxelSniper;
import com.thevoxelbox.voxelsniper.snipe.Sniper;
import org.bukkit.entity.Player;

public class JSniper extends Sniper {

    public JSniper(VoxelSniper plugin, Player player) {
        super(plugin, player);
    }

    @Override
    public JPlayer getPlayer() {
        return JustPlotsVoxelSniper.getJPlayer(super.getPlayer());
    }
}