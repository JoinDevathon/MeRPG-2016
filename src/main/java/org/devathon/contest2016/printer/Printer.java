package org.devathon.contest2016.printer;

import org.bukkit.Location;
import org.devathon.contest2016.Model;
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

    public Printer(Location location) {
        location.add(0, Model.SIZE_NORMAL_HEAD, 0);
        this.printerFrameModel = new PrinterFrameModel(location);
        this.verticalExtruderSupportModel = new VerticalExtruderSupportModel(location.clone().add(0, 0, 4));
        this.horizontalExtruderSupportModel = new HorizontalExtruderSupportModel(location.clone().add(0, 16, 4));
        this.extruderModel = new ExtruderModel(location.clone().add(5, 16 - Model.SIZE_NORMAL_HEAD / 2, 4));

        this.printerFrameModel.spawn();
        this.verticalExtruderSupportModel.spawn();
        this.horizontalExtruderSupportModel.spawn();
        this.extruderModel.spawn();
    }
}
