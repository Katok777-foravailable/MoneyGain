package org.katok.moneyGain.Economy;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class EconomyEvent implements Listener {
    protected Economy economy = null;

    public EconomyEvent(Economy economy) {
        this.economy = economy;
    }

    @EventHandler
    public void onEnter(PlayerJoinEvent e) {
        if(economy == null) return;

        economy.createPlayerAccount(e.getPlayer());
    }
}
