package org.devathon.contest2016.util;

import net.minecraft.server.v1_10_R1.*;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_10_R1.CraftWorld;
import org.bukkit.entity.ArmorStand;
import org.bukkit.inventory.ItemStack;
import org.devathon.contest2016.Model;

import java.lang.reflect.Field;
import java.util.Set;

/**
 * @author Jaxon A Brown
 */
public class StandEntity extends EntityArmorStand {
    private static Field cField;
    private static Field bBField;

    static {
        try {
            cField = EntityTracker.class.getDeclaredField("c");
            cField.setAccessible(true);
            bBField = EntityArmorStand.class.getDeclaredField("bB");
            bBField.setAccessible(true);
        } catch(Exception ex) {
            ex.printStackTrace();
            //TODO
        }
    }

    private boolean small;
    private Model.PartData partData;

    private StandTracker tracker;

    public StandEntity(Location location, Model.PartData partData) {
        super(((CraftWorld) location.getWorld()).getHandle());

        this.partData = partData;

        this.setBasePlate(false);
        this.setArms(false);
        this.setInvisible(true);
        this.setSilent(true);
        this.setNoGravity(true);
        this.setCustomNameVisible(false);
        this.setSmall(this.small = partData.small);

        moveTo(location);

        try {
            bBField.set(this, 2039583);
        } catch(Exception ex) {
            ex.printStackTrace();
            //TODO
        }

        this.world.getChunkAt(MathHelper.floor(locX / 16.0D), MathHelper.floor(locZ / 16.0D)).a(this);
        this.world.entityList.add(this);
        tracker = new StandTracker(this);
        try {
            ((Set) cField.get(((WorldServer) this.world).getTracker())).add(tracker);
        } catch(Exception ex) {
            ex.printStackTrace();
            //TODO
        }
    }

    public void setItem(ItemStack stack) {
        ((ArmorStand) getBukkitEntity()).setHelmet(stack);
    }

    public void moveTo(Location location) {
        this.setPosition(location.getX(), location.getY() - (small ? .68 : 1.38), location.getZ());
        this.setHeadPose(new Vector3f(location.getPitch(), location.getYaw(), 0));
    }

    public Model.PartData getPartData() {
        return this.partData;
    }

    @Override
    public void die() {
        super.die();
        tracker.broadcast(new PacketPlayOutEntityDestroy(getId()));
    }
}
