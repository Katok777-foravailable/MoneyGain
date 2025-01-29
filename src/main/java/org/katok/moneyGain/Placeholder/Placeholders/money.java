package org.katok.moneyGain.Placeholder.Placeholders;

import org.bukkit.OfflinePlayer;
import org.katok.moneyGain.Placeholder.PlaceholderExample;

import static org.katok.moneyGain.MoneyGain.economyManager;

public class money implements PlaceholderExample {
    @Override
    public String getString() {
        return "money";
    }

    @Override
    public String getValue(OfflinePlayer offlinePlayer) {
        return String.valueOf(economyManager.economy.getBalance(offlinePlayer));
    }
}
