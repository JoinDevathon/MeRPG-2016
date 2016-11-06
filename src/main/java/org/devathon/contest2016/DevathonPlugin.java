package org.devathon.contest2016;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.devathon.contest2016.printer.Printer;

public class DevathonPlugin extends JavaPlugin implements Listener {
    private static DevathonPlugin instance;
    public static DevathonPlugin getInstance() { return instance; }

    public Printer printer;

    @Override
    public void onEnable() {
        instance = this;

        getCommand("buildprinter").setExecutor(new BuildPrinterCommand());
        getCommand("print").setExecutor(new PrintCommand());

        saveResource("torus.json", false);

        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        event.getPlayer().sendMessage(ChatColor.GREEN + "Welcome! To use the 3D printer, type /buildprinter while in a large " +
                "(30x30) open and flat space. Be on the ground. Next, open the chest (you might need to access it from underneath)" +
                " and put whatever type of BLOCK you want the printer to build with. Add a few stacks. Next, run /print <filename.json>." +
                " You can also use a demo file, torus.json. \"/print torus.json\"");
        event.getPlayer().sendMessage(ChatColor.RED + "Note that this plugin is by no means a finished product, I'm just too tired to" +
                " finish it tonight. If you want to reset the printer, I recommend restarting the server (the armor stands will" +
                " dissapear). Have fun!");
    }
}

