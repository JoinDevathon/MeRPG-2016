package org.devathon.contest2016;

import org.bukkit.plugin.java.JavaPlugin;

public class DevathonPlugin extends JavaPlugin {
    public static final double SIZE_NORMAL_HEAD = 0.4165;//TODO I think....

    private static DevathonPlugin instance;
    public static DevathonPlugin getInstance() { return instance; }

    @Override
    public void onEnable() {
        instance = this;

        getCommand("test").setExecutor(new TestCommand());
    }

    @Override
    public void onDisable() {

    }
}

