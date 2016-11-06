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
public class VerticalExtruderSupportModel extends Model {
    public VerticalExtruderSupportModel(Location location) {
        super(location);
    }

    @Override
    public Set<PartData> createModel() {
        Set<PartData> parts = Sets.newHashSet();

        double frameSize = Model.SIZE_NORMAL_HEAD * 17;

        for(double y = Model.SIZE_NORMAL_HEAD; y < frameSize * 2; y += Model.SIZE_NORMAL_HEAD) {
            parts.add(new PartData(-frameSize - Model.SIZE_NORMAL_HEAD / 2, y, 0, 0, 0, false, new ItemStack(Material.STONE, 1, (short) 5)));
            parts.add(new PartData(frameSize + Model.SIZE_NORMAL_HEAD / 2, y, 0, 0, 0, false, new ItemStack(Material.STONE, 1, (short) 5)));
        }

        return parts;
    }
}
