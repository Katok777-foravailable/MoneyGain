package org.katok.moneyGain.Placeholder;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;
import org.reflections.Reflections;

import java.util.HashMap;
import java.util.Set;
import java.util.function.Function;

import static org.katok.moneyGain.MoneyGain.instance;

public class PlaceholderController extends PlaceholderExpansion {
    protected HashMap<String, Function<OfflinePlayer, String>> placeholders = new HashMap<>();

    public PlaceholderController(String path) {
        Reflections reflections = new org.reflections.Reflections(path);

        Set<Class<? extends PlaceholderExample>> allClasses = reflections.getSubTypesOf(PlaceholderExample.class);

        for(Class<? extends PlaceholderExample> placeholderClass: allClasses) {
            try {
                PlaceholderExample placeholder = placeholderClass.newInstance();
                placeholders.put(placeholder.getString().toLowerCase(), placeholder::getValue); // понимаю что говнокод, зато удобно
            } catch (InstantiationException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
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
