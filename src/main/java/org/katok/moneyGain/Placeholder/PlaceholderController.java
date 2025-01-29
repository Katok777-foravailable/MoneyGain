package org.katok.moneyGain.Placeholder;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;
import org.katok.moneyGain.Placeholder.Placeholders.money;

import java.util.HashMap;
import java.util.Set;
import java.util.function.Function;

import static org.katok.moneyGain.MoneyGain.instance;

public class PlaceholderController extends PlaceholderExpansion {
    protected HashMap<String, Function<OfflinePlayer, String>> placeholders = new HashMap<>();

    public PlaceholderController(String path) {
        money money = new money();
        placeholders.put(money.getString(), money::getValue);
    }

    @Override
    public @NotNull String getIdentifier() {
        return instance.getName();
    }

    @Override
    public @NotNull String getAuthor() {
        return instance.getPluginMeta().getAuthors().get(0);
    }

    @Override
    public @NotNull String getVersion() {
        return instance.getPluginMeta().getVersion();
    }

    @Override
    public String onRequest(OfflinePlayer player, @NotNull String params) {
        if(!placeholders.containsKey(params.toLowerCase())) return null;

        return placeholders.get(params.toLowerCase()).apply(player);
    }
}
