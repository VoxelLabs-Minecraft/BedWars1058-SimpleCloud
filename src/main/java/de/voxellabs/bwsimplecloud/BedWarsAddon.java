package de.voxellabs.bwsimplecloud;

import de.voxellabs.bwsimplecloud.listener.GameStateChangeListener;
import eu.thesimplecloud.api.CloudAPI;
import lombok.Getter;
import org.bstats.bukkit.Metrics;
import org.bstats.charts.SingleLineChart;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Logger;

@Getter
public final class BedWarsAddon extends JavaPlugin {

    @Getter private static BedWarsAddon instance;

    private final Logger logger = Bukkit.getLogger();

    private final Integer pluginId = 28643;
    private Metrics metrics;

    @Override
    public void onLoad() {
        instance = this;

        logger.info("*-------Bedwars1058 SimpleCloud-------*");
        logger.info("*        Plugin loading...         *");
        logger.info("*------1.0.0-RELEASE by Shvquu------*");
        String ServiceName = CloudAPI.getInstance().getThisSidesName();
        logger.info("Service name: " + ServiceName);
        try {
            File file = new File("plugins/BedWars1058/config.yml");
            Scanner reader = new Scanner(file);
            StringBuilder data_parse = new StringBuilder();
            while (reader.hasNextLine()) {
                String data = reader.nextLine();
                if (data.contains("  server-id: "))
                    data = "  server-id: " + ServiceName;
                data_parse.append(data).append("\n");
            }
            reader.close();
            logger.info("Config file read");
            try {
                FileWriter writer = new FileWriter("plugins/BedWars1058/config.yml");
                writer.write(data_parse.toString());
                writer.close();
                logger.info("Successfully wrote config file");
            } catch (IOException e) {
                logger.severe("An error occurred!" + e.getMessage());
            }
        } catch (FileNotFoundException e) {
            logger.severe("Config file doesn't exist!" + e.getMessage());
        }
    }

    @Override
    public void onEnable() {
        metrics = new Metrics(this, pluginId);

        metrics.addCustomChart(new SingleLineChart("players", () -> Bukkit.getOnlinePlayers().size()));

        if (Bukkit.getPluginManager().getPlugin("BedWars1058") == null) {
            logger.severe("BedWars1058 was not found. Disable plugin...");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        Bukkit.getPluginManager().registerEvents(new GameStateChangeListener(), this);
    }

    @Override
    public void onDisable() {
        logger.info("*-------Bedwars1058 SimpleCLoud-------*");
        logger.info("*          Plugin disabled         *");
        logger.info("*------1.0.0-RELEASE by Shvquu------*");
    }
}
