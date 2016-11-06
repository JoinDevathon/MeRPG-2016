package org.devathon.contest2016;

import org.bukkit.plugin.java.JavaPlugin;

public class DevathonPlugin extends JavaPlugin {
    private static DevathonPlugin instance;
    public static DevathonPlugin getInstance() { return instance; }

    @Override
    public void onEnable() {
        instance = this;

        getCommand("test").setExecutor(new TestCommand());
        getCommand("test1").setExecutor(new TestCommand1());
    }

    @Override
    public void onDisable() {

    }
}

