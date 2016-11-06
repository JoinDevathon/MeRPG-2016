package org.devathon.contest2016;

import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;
import org.devathon.contest2016.util.StandEntity;

import java.util.Set;

/**
 * @author Jaxon A Brown
 */
public abstract class Model {
    public static final double SIZE_NORMAL_HEAD = 0.595;
    public static final double SIZE_SMALL_HEAD = 0.4165;

    private Location currentLocation;
    private Set<StandEntity> stands;

    public Model(Location location) {
        this.currentLocation = location;
    }

    public abstract Set<PartData> createModel();

    public void spawn() {
        Preconditions.checkState(stands == null);
        this.stands = Sets.newHashSet();

        createModel().forEach(data -> {
            StandEntity stand = new StandEntity(
                    new Location(currentLocation.getWorld(),
                            currentLocation.getX() + data.x,
                            currentLocation.getY() + data.y,
                            currentLocation.getZ() + data.z,
                            (float) data.yaw, (float) data.pitch),
                    data);
            stand.setItem(data.stack);
            this.stands.add(stand);
        });
    }

    public void destroy() {
        this.stands.forEach(StandEntity::die);
        this.stands = null;
    }

    public void moveTo(Location location) {
        this.currentLocation = location;
        this.stands.forEach(stand -> {
            PartData data = stand.getPartData();
            stand.moveTo(new Location(location.getWorld(),
                    location.getX() + data.x, location.getY() + data.y, location.getZ() + data.z,
                    (float) data.yaw, (float) data.pitch));
        });
    }

    public static class PartData {
        public final double x, y, z, yaw, pitch;
        public final boolean small;
        public final ItemStack stack;

        public PartData(double x, double y, double z, double yaw, double pitch, boolean small, ItemStack stack) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.yaw = yaw;
            this.pitch = pitch;
            this.small = small;
            this.stack = stack;
        }
    }
}
