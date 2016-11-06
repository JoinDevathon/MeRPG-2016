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
public class HorizontalExtruderSupportModel extends Model {
    public HorizontalExtruderSupportModel(Location location) {
        super(location);
    }

    @Override
    public Set<PartData> createModel() {
        Set<PartData> parts = Sets.newHashSet();

        double frameSize = Model.SIZE_NORMAL_HEAD * 17;

        for(double x = -frameSize + Model.SIZE_NORMAL_HEAD; x < frameSize; x += Model.SIZE_NORMAL_HEAD) {
            parts.add(new PartData(x, 0, 0, 0, 0, false, new ItemStack(Material.STONE, 1, (short) 5)));
        }

        return parts;
    }
}
