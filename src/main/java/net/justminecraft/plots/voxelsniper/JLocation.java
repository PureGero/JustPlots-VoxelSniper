package net.justminecraft.plots.voxelsniper;

import org.bukkit.Location;
import org.bukkit.World;

public class JLocation extends Location {
    public JLocation(JWorld world, double x, double y, double z) {
        super(world, x, y, z);
    }

    public JLocation(JWorld world, double x, double y, double z, float yaw, float pitch) {
        super(world, x, y, z, yaw, pitch);
    }

    public JLocation(JWorld world, Location location) {
        super(world, location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
    }

    @Override
    public World getWorld() {
        // Return the bukkit world if we're comparing distances

        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();

        for (StackTraceElement e : stackTraceElements) {
            if (e.getMethodName().equals("distanceSquared")) {
                return ((JWorld) super.getWorld()).getRealWorld();
            }
        }

        return super.getWorld();
    }
}
