package org.devathon.contest2016;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;

/**
 * @author Jaxon A Brown
 */
public class TestCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        Player player = (Player) commandSender;

        VoxelUtil.place(VoxelUtil.loadFromJSON(new File(DevathonPlugin.getInstance().getDataFolder(), "torus.json")), player.getLocation());
        player.sendMessage("Done!");

        return true;
    }
}
