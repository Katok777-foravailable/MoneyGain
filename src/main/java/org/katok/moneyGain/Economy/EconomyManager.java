package org.katok.moneyGain.Economy;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.RegisteredServiceProvider;

import static org.katok.moneyGain.MoneyGain.instance;

public class EconomyManager {
    public static final String bank_name = "MoneyGain";

    public Economy economy = null;

    public EconomyManager() {
        if (instance.getServer().getPluginManager().getPlugin("Vault") == null) {
            return;
        }
        RegisteredServiceProvider<Economy> vault_class = instance.getServer().getServicesManager().getRegistration(Economy.class);
        if (vault_class == null) {
            return;
        }
        economy = new EconomyService();

        instance.getServer().getPluginManager().registerEvents(new EconomyEvent(economy), instance);
    }

    public Boolean load_successful() {
        return economy != null;
    }
}
