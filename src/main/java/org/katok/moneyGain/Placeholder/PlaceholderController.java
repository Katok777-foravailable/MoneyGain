package org.katok.moneyGain.Placeholder;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;
import org.katok.moneyGain.Placeholder.Placeholders.money;
import org.reflections.Reflections;
import org.reflections.scanners.Scanner;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;
import java.util.function.Function;

import static org.katok.moneyGain.MoneyGain.instance;

public class PlaceholderController extends PlaceholderExpansion {
    protected HashMap<String, Function<OfflinePlayer, String>> placeholders = new HashMap<>();

    public PlaceholderController(String path) {
        Reflections reflections = new Reflections(path);

        Set<Class<? extends PlaceholderExample>> allClasses = reflections.getSubTypesOf(PlaceholderExample.class);

        for(Class<? extends PlaceholderExample> placeholderClass: allClasses) {
            try {
                PlaceholderExample placeholder = placeholderClass.newInstance();
                placeholders.put(placeholder.getString().toLowerCase(), placeholder::getValue);
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
