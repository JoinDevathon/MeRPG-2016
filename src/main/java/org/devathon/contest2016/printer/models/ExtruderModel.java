package org.devathon.contest2016.printer.models;

import com.google.common.collect.Sets;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.devathon.contest2016.Model;

import java.util.Set;

/**
 * @author Jaxon A Brown
 */
public class ExtruderModel extends Model {
    public ExtruderModel(Location location) {
        super(location);
    }

    @Override
    public Set<PartData> createModel() {
        Set<PartData> parts = Sets.newHashSet();

        parts.add(new PartData(Model.SIZE_NORMAL_HEAD / 2, 0, Model.SIZE_NORMAL_HEAD / 2, 0, 0, false, new ItemStack(Material.QUARTZ_BLOCK)));
        parts.add(new PartData(-Model.SIZE_NORMAL_HEAD / 2, 0, Model.SIZE_NORMAL_HEAD / 2, 0, 0, false, new ItemStack(Material.QUARTZ_BLOCK)));
        parts.add(new PartData(Model.SIZE_NORMAL_HEAD / 2, 0, -Model.SIZE_NORMAL_HEAD / 2, 0, 0, false, new ItemStack(Material.QUARTZ_BLOCK)));
        parts.add(new PartData(-Model.SIZE_NORMAL_HEAD / 2, 0, -Model.SIZE_NORMAL_HEAD / 2, 0, 0, false, new ItemStack(Material.QUARTZ_BLOCK)));
        parts.add(new PartData(0, -Model.SIZE_SMALL_HEAD / 2, 0, 0, 0, true, new ItemStack(Material.STONE, 1, (short) 6)));

        return parts;
    }
}
