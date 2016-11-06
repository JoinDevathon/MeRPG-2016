package org.devathon.contest2016;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.devathon.contest2016.printer.Printer;

/**
 * @author Jaxon A Brown
 */
public class BuildPrinterCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage("Player only!");
            return true;
        }

        if(DevathonPlugin.getInstance().printer != null) {
            sender.sendMessage(ChatColor.RED + "You've already built a printer! If you want to get rid of it, restart the server");
            return true;
        }

        DevathonPlugin.getInstance().printer = new Printer(((Player) sender).getLocation());
        sender.sendMessage(ChatColor.GREEN + "Printer building... /print <filename> to print a file. To create a file to print, go to" +
                " http://drububu.com/miscellaneous/voxelizer/index.html or use \"/print torus.json\"");
        return true;
    }
}
