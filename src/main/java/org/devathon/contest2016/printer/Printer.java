package org.devathon.contest2016.printer;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.devathon.contest2016.DevathonPlugin;
import org.devathon.contest2016.Model;
import org.devathon.contest2016.Voxel;
import org.devathon.contest2016.printer.models.ExtruderModel;
import org.devathon.contest2016.printer.models.HorizontalExtruderSupportModel;
import org.devathon.contest2016.printer.models.PrinterFrameModel;
import org.devathon.contest2016.printer.models.VerticalExtruderSupportModel;
import org.devathon.contest2016.util.StandEntity;

/**
 * @author Jaxon A Brown
 */
public class Printer {
    private PrinterFrameModel printerFrameModel;
    private VerticalExtruderSupportModel verticalExtruderSupportModel;
    private HorizontalExtruderSupportModel horizontalExtruderSupportModel;
    private ExtruderModel extruderModel;
    private StandEntity heldBlock;

    private Location extruderLoc;
    private Location idleLoc;
    private Location corner;
    PrintTask task;

    public Printer(Location location) {
        this.idleLoc = location.clone().add(10, 0, 0).getBlock().getLocation();
        this.idleLoc.getBlock().setType(Material.CHEST);
        this.corner = location.clone().add(-9, 0, -9);

        location.add(0, Model.SIZE_NORMAL_HEAD, 0);
        this.printerFrameModel = new PrinterFrameModel(location);
        this.verticalExtruderSupportModel = new VerticalExtruderSupportModel(location.clone().add(0, 0, 4));
        this.horizontalExtruderSupportModel = new HorizontalExtruderSupportModel(location.clone().add(0, 16, 4));
        this.extruderModel = new ExtruderModel(location.clone().add(5, 16 - Model.SIZE_SMALL_HEAD / 2, 4));

        this.printerFrameModel.spawn();
        this.verticalExtruderSupportModel.spawn();
        this.horizontalExtruderSupportModel.spawn();
        this.extruderModel.spawn();

        setExtruderLocation(idleLoc.clone().add(0, 1, 0));
    }

    public void setExtruderLocation(Location tip) {
        this.extruderLoc = tip;
        double zDelta = tip.getZ() - printerFrameModel.getLocation().getZ();
        this.verticalExtruderSupportModel.moveTo(printerFrameModel.getLocation().add(0, 0, zDelta));
        this.horizontalExtruderSupportModel.moveTo(printerFrameModel.getLocation()
                .add(0, tip.getY() - printerFrameModel.getLocation().getY() + Model.SIZE_SMALL_HEAD, zDelta));
        this.extruderModel.moveTo(tip.clone().add(0, Model.SIZE_SMALL_HEAD / 2, 0));
        if(this.heldBlock != null) {
            this.heldBlock.moveTo(tip.clone().add(0, -Model.SIZE_NORMAL_HEAD, 0));
        }
    }

    public Location getExtruderLoc() {
        return extruderLoc.clone();
    }

    public void setHeldBlock(ItemStack stack) {
        if(this.heldBlock != null) {
            heldBlock.setItem(null);
            heldBlock.die();
            heldBlock = null;
        }

        if(stack == null) {
            return;
        }

        this.heldBlock = new StandEntity(this.extruderModel.getLocation()
                .add(0, -Model.SIZE_SMALL_HEAD / 2 - Model.SIZE_NORMAL_HEAD, 0), new Model.PartData(0, 0, 0, 0, 0, false, stack));
        this.heldBlock.setItem(stack);
    }

    public void dropHeldBlock(Location target) {
        if(heldBlock != null) {
            target.clone().add(0, -1, 0).getBlock().setType(heldBlock.getPartData().stack.getType());
            setHeldBlock(null);
        }
    }

    public Location getIdleLoc() {
        return idleLoc.clone();
    }

    public Location getCorner() {
        return corner.clone();
    }

    public void print(Voxel voxel) {
        (this.task = new PrintTask(this, voxel)).runTaskTimer(DevathonPlugin.getInstance(), 0, 1);
    }

    public boolean isBusy() {
        return task != null;
    }
}
