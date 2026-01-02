package de.voxellabs.bwsimplecloud.listener;

import com.andrei1058.bedwars.api.BedWars;
import com.andrei1058.bedwars.api.arena.GameState;
import com.andrei1058.bedwars.api.events.gameplay.GameStateChangeEvent;
import eu.thesimplecloud.api.CloudAPI;
import eu.thesimplecloud.api.service.ServiceState;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class GameStateChangeListener implements Listener {

    private final BedWars bedWars = Bukkit.getServicesManager().getRegistration(BedWars.class).getProvider();

    @EventHandler
    public void onGameStateChange(GameStateChangeEvent event) {
        if (event.getNewState().equals(GameState.starting)) {
            CloudAPI.getInstance().getCloudServiceManager().getCloudServiceByName(CloudAPI.getInstance().getThisSidesName()).setState(ServiceState.INVISIBLE);
            CloudAPI.getInstance().getCloudServiceManager().getCloudServiceByName(CloudAPI.getInstance().getThisSidesName()).getServiceGroup().startNewService();
            return;
        }
        if (event.getNewState().equals(GameState.restarting)) {
            CloudAPI.getInstance().getCloudServiceManager().getCloudServiceByName(CloudAPI.getInstance().getThisSidesName()).setState(ServiceState.CLOSED);
            CloudAPI.getInstance().getCloudServiceManager().getCloudServiceByName(CloudAPI.getInstance().getThisSidesName()).getServiceGroup().startNewService();
            return;
        }
    }
}
