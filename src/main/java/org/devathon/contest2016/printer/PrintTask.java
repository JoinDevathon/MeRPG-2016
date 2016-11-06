package org.devathon.contest2016.printer;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.devathon.contest2016.Voxel;
import org.devathon.contest2016.VoxelUtil;

import java.util.List;

/**
 * @author Jaxon A Brown
 */
public class PrintTask extends BukkitRunnable {
    private static final double SPEED = 0.35;
    private static final double TOLERANCE = 0.1;

    private Printer printer;
    private PrintState state;
    private int itr = -1;
    private Location target;
    private List<Location> voxels;
    private Inventory inventory;

    public PrintTask(Printer printer, Voxel voxel) {
        this.printer = printer;
        this.state = PrintState.RETURNING_TO_CHEST;
        this.target = this.printer.getIdleLoc().add(0.5, 1, 0.5);
        this.voxels = VoxelUtil.createVoxelList(voxel, printer.getCorner());
        this.inventory = ((Chest) printer.getIdleLoc().getBlock().getState()).getBlockInventory();
    }

    @Override
    public void run() {
        if(state == PrintState.WAITING) {
            ItemStack found = nextItem();
            if(found == null) {
                return;
            }
            state = PrintState.RETURNING_TO_CHEST;
        }
        Location tip = printer.getExtruderLoc();
        if(tip.distanceSquared(target) <= TOLERANCE) {
            if(state == PrintState.RETURNING_TO_CHEST) {
                ItemStack found = nextItem();
                if(found == null) {
                    Bukkit.broadcastMessage(ChatColor.RED + "Printer could not find more blocks in chest. Printing paused.");
                    state = PrintState.WAITING;
                    return;
                }
                printer.setHeldBlock(found);
                state = PrintState.MOVING_TO_TARGET;
                if(++itr >= voxels.size()) {
                    this.cancel();
                    Bukkit.broadcastMessage(ChatColor.GREEN + "Printing done!");
                    printer.task = null;
                    return;
                }
                this.target = voxels.get(itr).clone().add(0.5, 1, 0.5);
                System.out.println("New target (" + itr + "): " + Integer.toHexString(target.hashCode()));
            } else {
                printer.dropHeldBlock(target);
                state = PrintState.RETURNING_TO_CHEST;
                this.target = printer.getIdleLoc().add(0.5, 1, 0.5);
            }
        } else {
            double remainingX = target.getX() - tip.getX();
            double remainingY = target.getY() - tip.getY();
            double remainingZ = target.getZ() - tip.getZ();

            printer.setExtruderLocation(new Location(tip.getWorld(),
                    copySign(Math.min(SPEED, Math.abs(remainingX)), remainingX) + tip.getX(),
                    copySign(Math.min(SPEED, Math.abs(remainingY)), remainingY) + tip.getY(),
                    copySign(Math.min(SPEED, Math.abs(remainingZ)), remainingZ) + tip.getZ()));
        }

    }

    private double copySign(double magnitude, double sign) {
        return magnitude * (magnitude < 0 ? (sign > 0 ? -1 : 1) : (sign > 0 ? 1 : -1));
    }

    private ItemStack nextItem() {
        ItemStack[] contents = inventory.getContents();
        ItemStack found = null;
        for(int i = 0; i < contents.length; i++) {
            ItemStack stack = contents[i];
            if(stack != null && stack.getType() != Material.AIR && stack.getType().isBlock()) {
                ItemStack newStack;
                if(stack.getAmount() > 1) {
                    newStack = stack;
                    newStack.setAmount(stack.getAmount() - 1);
                } else {
                    newStack = null;
                }
                contents[i] = newStack;
                found = stack;
                break;
            }
        }
        inventory.setContents(contents);
        return found;
    }

    private enum PrintState {
        RETURNING_TO_CHEST,
        MOVING_TO_TARGET,
        WAITING;
    }
}
