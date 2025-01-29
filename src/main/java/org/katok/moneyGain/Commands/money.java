package org.katok.moneyGain.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static org.katok.moneyGain.MoneyGain.economyManager;
import static org.katok.moneyGain.MoneyGain.message_cfg;
import static org.katok.moneyGain.utils.ConfigUtil.getString;

public class money implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if(!(sender instanceof Player player)) {
            sender.sendMessage(getString("console.cantwritefromconsole", message_cfg));
            return true;
        }

        player.sendMessage(String.valueOf(economyManager.economy.getBalance(player)));
        return true;
    }
}
