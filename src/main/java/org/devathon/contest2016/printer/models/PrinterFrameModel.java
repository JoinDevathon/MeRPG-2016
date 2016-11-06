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
public class PrinterFrameModel extends Model {
    public PrinterFrameModel(Location location) {
        super(location);
    }

    @Override
    public Set<PartData> createModel() {
        Set<PartData> parts = Sets.newHashSet();

        double frameSize = Model.SIZE_NORMAL_HEAD * 17;

        //Bottom X frame
        for(double x = -frameSize; x <= frameSize; x += Model.SIZE_NORMAL_HEAD) {
            parts.add(new PartData(x, 0, -frameSize, 0, 0, false, new ItemStack(Material.IRON_BLOCK)));
            parts.add(new PartData(x, 0, -frameSize - Model.SIZE_NORMAL_HEAD, 0, 0, false, new ItemStack(Material.IRON_BLOCK)));
            parts.add(new PartData(x, -Model.SIZE_NORMAL_HEAD, -frameSize, 0, 0, false, new ItemStack(Material.IRON_BLOCK)));
            parts.add(new PartData(x, -Model.SIZE_NORMAL_HEAD, -frameSize - Model.SIZE_NORMAL_HEAD, 0, 0, false, new ItemStack(Material.IRON_BLOCK)));
        }
        for(double x = -frameSize; x <= frameSize; x += Model.SIZE_NORMAL_HEAD) {
            parts.add(new PartData(x, 0, frameSize, 0, 0, false, new ItemStack(Material.IRON_BLOCK)));
            parts.add(new PartData(x, 0, frameSize + Model.SIZE_NORMAL_HEAD, 0, 0, false, new ItemStack(Material.IRON_BLOCK)));
            parts.add(new PartData(x, -Model.SIZE_NORMAL_HEAD, frameSize, 0, 0, false, new ItemStack(Material.IRON_BLOCK)));
            parts.add(new PartData(x, -Model.SIZE_NORMAL_HEAD, frameSize + Model.SIZE_NORMAL_HEAD, 0, 0, false, new ItemStack(Material.IRON_BLOCK)));
        }

        //Bottom Z frame
        for(double z = -frameSize + Model.SIZE_NORMAL_HEAD; z < frameSize; z += Model.SIZE_NORMAL_HEAD) {
            parts.add(new PartData(-frameSize, 0, z, 0, 0, false, new ItemStack(Material.IRON_BLOCK)));
            parts.add(new PartData(-frameSize - Model.SIZE_NORMAL_HEAD, 0, z, 0, 0, false, new ItemStack(Material.IRON_BLOCK)));
            parts.add(new PartData(-frameSize, -Model.SIZE_NORMAL_HEAD, z, 0, 0, false, new ItemStack(Material.IRON_BLOCK)));
            parts.add(new PartData(-frameSize - Model.SIZE_NORMAL_HEAD, -Model.SIZE_NORMAL_HEAD, z, 0, 0, false, new ItemStack(Material.IRON_BLOCK)));
        }
        for(double z = -frameSize + Model.SIZE_NORMAL_HEAD; z < frameSize; z += Model.SIZE_NORMAL_HEAD) {
            parts.add(new PartData(frameSize, 0, z, 0, 0, false, new ItemStack(Material.IRON_BLOCK)));
            parts.add(new PartData(frameSize + Model.SIZE_NORMAL_HEAD, 0, z, 0, 0, false, new ItemStack(Material.IRON_BLOCK)));
            parts.add(new PartData(frameSize, -Model.SIZE_NORMAL_HEAD, z, 0, 0, false, new ItemStack(Material.IRON_BLOCK)));
            parts.add(new PartData(frameSize + Model.SIZE_NORMAL_HEAD, -Model.SIZE_NORMAL_HEAD, z, 0, 0, false, new ItemStack(Material.IRON_BLOCK)));
        }

        //Vertical frame
        for(double y = Model.SIZE_NORMAL_HEAD; y < frameSize * 2; y += Model.SIZE_NORMAL_HEAD) {
            parts.add(new PartData(-frameSize, y, -frameSize, 0, 0, false, new ItemStack(Material.IRON_BLOCK)));
            parts.add(new PartData(-frameSize - Model.SIZE_NORMAL_HEAD, y, -frameSize - Model.SIZE_NORMAL_HEAD, 0, 0, false, new ItemStack(Material.IRON_BLOCK)));
            parts.add(new PartData(-frameSize, y, -frameSize - Model.SIZE_NORMAL_HEAD, 0, 0, false, new ItemStack(Material.IRON_BLOCK)));
            parts.add(new PartData(-frameSize - Model.SIZE_NORMAL_HEAD, y, -frameSize, 0, 0, false, new ItemStack(Material.IRON_BLOCK)));
        }
        for(double y = Model.SIZE_NORMAL_HEAD; y < frameSize * 2; y += Model.SIZE_NORMAL_HEAD) {
            parts.add(new PartData(-frameSize, y, frameSize, 0, 0, false, new ItemStack(Material.IRON_BLOCK)));
            parts.add(new PartData(-frameSize - Model.SIZE_NORMAL_HEAD, y, frameSize + Model.SIZE_NORMAL_HEAD, 0, 0, false, new ItemStack(Material.IRON_BLOCK)));
            parts.add(new PartData(-frameSize, y, frameSize + Model.SIZE_NORMAL_HEAD, 0, 0, false, new ItemStack(Material.IRON_BLOCK)));
            parts.add(new PartData(-frameSize - Model.SIZE_NORMAL_HEAD, y, frameSize, 0, 0, false, new ItemStack(Material.IRON_BLOCK)));
        }
        for(double y = Model.SIZE_NORMAL_HEAD; y < frameSize * 2; y += Model.SIZE_NORMAL_HEAD) {
            parts.add(new PartData(frameSize, y, -frameSize, 0, 0, false, new ItemStack(Material.IRON_BLOCK)));
            parts.add(new PartData(frameSize + Model.SIZE_NORMAL_HEAD, y, -frameSize - Model.SIZE_NORMAL_HEAD, 0, 0, false, new ItemStack(Material.IRON_BLOCK)));
            parts.add(new PartData(frameSize, y, -frameSize - Model.SIZE_NORMAL_HEAD, 0, 0, false, new ItemStack(Material.IRON_BLOCK)));
            parts.add(new PartData(frameSize + Model.SIZE_NORMAL_HEAD, y, -frameSize, 0, 0, false, new ItemStack(Material.IRON_BLOCK)));
        }
        for(double y = Model.SIZE_NORMAL_HEAD; y < frameSize * 2; y += Model.SIZE_NORMAL_HEAD) {
            parts.add(new PartData(frameSize, y, frameSize, 0, 0, false, new ItemStack(Material.IRON_BLOCK)));
            parts.add(new PartData(frameSize + Model.SIZE_NORMAL_HEAD, y, frameSize + Model.SIZE_NORMAL_HEAD, 0, 0, false, new ItemStack(Material.IRON_BLOCK)));
            parts.add(new PartData(frameSize, y, frameSize + Model.SIZE_NORMAL_HEAD, 0, 0, false, new ItemStack(Material.IRON_BLOCK)));
            parts.add(new PartData(frameSize + Model.SIZE_NORMAL_HEAD, y, frameSize, 0, 0, false, new ItemStack(Material.IRON_BLOCK)));
        }

        //Top X frame
        for(double x = -frameSize; x <= frameSize; x += Model.SIZE_NORMAL_HEAD) {
            parts.add(new PartData(x, frameSize * 2, -frameSize, 0, 0, false, new ItemStack(Material.IRON_BLOCK)));
            parts.add(new PartData(x, frameSize * 2, -frameSize - Model.SIZE_NORMAL_HEAD, 0, 0, false, new ItemStack(Material.IRON_BLOCK)));
            parts.add(new PartData(x, frameSize * 2 + Model.SIZE_NORMAL_HEAD, -frameSize, 0, 0, false, new ItemStack(Material.IRON_BLOCK)));
            parts.add(new PartData(x, frameSize * 2 + Model.SIZE_NORMAL_HEAD, -frameSize - Model.SIZE_NORMAL_HEAD, 0, 0, false, new ItemStack(Material.IRON_BLOCK)));
        }
        for(double x = -frameSize; x <= frameSize; x += Model.SIZE_NORMAL_HEAD) {
            parts.add(new PartData(x, frameSize * 2, frameSize, 0, 0, false, new ItemStack(Material.IRON_BLOCK)));
            parts.add(new PartData(x, frameSize * 2, frameSize + Model.SIZE_NORMAL_HEAD, 0, 0, false, new ItemStack(Material.IRON_BLOCK)));
            parts.add(new PartData(x, frameSize * 2 + Model.SIZE_NORMAL_HEAD, frameSize, 0, 0, false, new ItemStack(Material.IRON_BLOCK)));
            parts.add(new PartData(x, frameSize * 2 + Model.SIZE_NORMAL_HEAD, frameSize + Model.SIZE_NORMAL_HEAD, 0, 0, false, new ItemStack(Material.IRON_BLOCK)));
        }

        //Top Z frame
        for(double z = -frameSize + Model.SIZE_NORMAL_HEAD; z < frameSize; z += Model.SIZE_NORMAL_HEAD) {
            parts.add(new PartData(-frameSize, frameSize * 2, z, 0, 0, false, new ItemStack(Material.IRON_BLOCK)));
            parts.add(new PartData(-frameSize - Model.SIZE_NORMAL_HEAD, frameSize * 2, z, 0, 0, false, new ItemStack(Material.IRON_BLOCK)));
            parts.add(new PartData(-frameSize, frameSize * 2 + Model.SIZE_NORMAL_HEAD, z, 0, 0, false, new ItemStack(Material.IRON_BLOCK)));
            parts.add(new PartData(-frameSize - Model.SIZE_NORMAL_HEAD, frameSize * 2 + Model.SIZE_NORMAL_HEAD, z, 0, 0, false, new ItemStack(Material.IRON_BLOCK)));
        }
        for(double z = -frameSize + Model.SIZE_NORMAL_HEAD; z < frameSize; z += Model.SIZE_NORMAL_HEAD) {
            parts.add(new PartData(frameSize, frameSize * 2, z, 0, 0, false, new ItemStack(Material.IRON_BLOCK)));
            parts.add(new PartData(frameSize + Model.SIZE_NORMAL_HEAD, frameSize * 2, z, 0, 0, false, new ItemStack(Material.IRON_BLOCK)));
            parts.add(new PartData(frameSize, frameSize * 2 + Model.SIZE_NORMAL_HEAD, z, 0, 0, false, new ItemStack(Material.IRON_BLOCK)));
            parts.add(new PartData(frameSize + Model.SIZE_NORMAL_HEAD, frameSize * 2 + Model.SIZE_NORMAL_HEAD, z, 0, 0, false, new ItemStack(Material.IRON_BLOCK)));
        }

        return parts;
    }
}
