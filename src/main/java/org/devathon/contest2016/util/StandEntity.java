package org.devathon.contest2016.util;

import net.minecraft.server.v1_10_R1.EntityArmorStand;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_10_R1.CraftWorld;

/**
 * @author Jaxon A Brown
 */
public class StandEntity extends EntityArmorStand {

    public StandEntity(Location location) {
        super(((CraftWorld) location.getWorld()).getHandle());
    }
}
