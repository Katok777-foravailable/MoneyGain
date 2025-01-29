package org.katok.moneyGain.Commands;

import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.katok.moneyGain.MoneyGain.*;
import static org.katok.moneyGain.utils.ConfigUtil.getString;

public class money implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        OfflinePlayer target_player = null;

        if(sender instanceof Player) {
            target_player = (OfflinePlayer) sender;
        }

        if(strings.length == 0) {
            if(checkConsole(sender)) return true;
            sender.sendMessage(String.valueOf(economyManager.economy.getBalance(target_player)));
            return true;
        }
        if(strings.length == 1 || !strings[1].chars().allMatch(Character::isDigit)) {
            sender.sendMessage(getString("economy.youmustwritevalue", message_cfg));
            return true;
        }

        if(strings.length >= 3) {
            target_player = instance.getServer().getOfflinePlayer(strings[2]);
            if (!economyManager.economy.hasAccount(target_player)) {
                sender.sendMessage(getString("other.playerdontexist", message_cfg));
                return true;
            }
        }

        if(strings.length < 3 && checkConsole(sender)) return true;

        int count_of_money = Integer.parseInt(strings[1]);

        switch(strings[0]) {
            case("give"):
                economyManager.economy.depositPlayer(target_player, count_of_money);
                break;
            case("take"):
                economyManager.economy.withdrawPlayer(target_player, count_of_money);
                break;
            case("set"):
                economy_cfg.set(target_player.getUniqueId().toString() + ".value", count_of_money);

                try {
                    economy_cfg.save(economy_file);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                break;
        }

        return true;
    }

    public Boolean checkConsole(CommandSender sender) {
        if(!(sender instanceof Player)) {
            sender.sendMessage(getString("console.cantwritefromconsole", message_cfg));
            return true;
        }
        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        List<String> list = new ArrayList<>();
        if(strings.length == 1) {
            list.add("give");
            list.add("take");
            list.add("set");
            return list;
        }
        return List.of();
    }
}
