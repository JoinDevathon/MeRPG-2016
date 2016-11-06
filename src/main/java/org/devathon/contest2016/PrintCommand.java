package org.devathon.contest2016;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;

/**
 * @author Jaxon A Brown
 */
public class PrintCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage("Player only!");
            return true;
        }

        if(DevathonPlugin.getInstance().printer == null) {
            sender.sendMessage(ChatColor.RED + "You need a printer first! Do /buildprinter to get one! Try to be in an open space and on the ground." +
                    " 30x30 flat platform is recommended (stand in center).");
            return true;
        }

        if(DevathonPlugin.getInstance().printer.isBusy()) {
            sender.sendMessage(ChatColor.RED + "You're already running a print! Wait for it to finish or restart the server!");
            return true;
        }

        if(args.length != 1) {
            sender.sendMessage(ChatColor.RED + "Make sure you do /print <file.json>");
            return true;
        }

        File file = new File(DevathonPlugin.getInstance().getDataFolder(), args[0]);
        Voxel voxel;
        try {
            voxel = VoxelUtil.loadFromJSON(file);
        } catch(Exception ex) {
            ex.printStackTrace();
            sender.sendMessage(ChatColor.RED + "That file isn't valid. Try torus.json");
            return true;
        }
        if(voxel.getX() > 15 || voxel.getY() > 15 || voxel.getZ() > 15) {
            sender.sendMessage(ChatColor.RED + "The maximum size of the model is 15x15x15");
            return true;
        }
        DevathonPlugin.getInstance().printer.print(voxel);
        return true;
    }
}
