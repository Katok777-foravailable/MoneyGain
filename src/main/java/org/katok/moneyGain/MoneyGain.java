package org.katok.moneyGain;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;
import org.katok.moneyGain.Commands.money;
import org.katok.moneyGain.Economy.EconomyManager;
import org.katok.moneyGain.Placeholder.PlaceholderController;

import java.io.File;
import java.util.logging.Logger;

import static org.katok.moneyGain.utils.ConfigUtil.getInt;

public final class MoneyGain extends JavaPlugin {
    public static MoneyGain instance;
    public static EconomyManager economyManager;
    public static Logger logger;
//    public static PermissionManager permissionManager;

    public static FileConfiguration config;

    public static File economy_file;
    public static YamlConfiguration economy_cfg;

    public static File message_file;
    public static YamlConfiguration message_cfg;

    @Override
    public void onEnable() {
        instance = this;
        logger = getLogger();
        economyManager = new EconomyManager();
//        permissionManager = new PermissionManager();

        // конфиги
        saveDefaultConfig();
        config = getConfig();

        economy_cfg = new YamlConfiguration();
        economy_file = new File(instance.getDataFolder(), "economy.yml");

        if(!economy_file.exists()) instance.saveResource("economy.yml", false);

        try {
            economy_cfg.load(economy_file);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        message_cfg = new YamlConfiguration();
        message_file = new File(instance.getDataFolder(), "message.yml");

        if(!message_file.exists()) instance.saveResource("message.yml", false);

        try {
            message_cfg.load(message_file);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // загрузка зависимостей
        if (!Bukkit.getPluginManager().isPluginEnabled("Vault")) {
            logger.severe("Vault не найден! Он требуется для работы плагина."); //
            Bukkit.getPluginManager().disablePlugin(this);
        }
        if (!Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            logger.severe("PlaceholderAPI не найден! Он требуется для работы плагина."); //
            Bukkit.getPluginManager().disablePlugin(this);
        }

        // загрузка экономики
        if(!economyManager.load_successful()) {
            logger.severe("НЕ УДАЛОСЬ ЗАГРУЗИТЬ ЭКОНОМИКУ");
            getServer().getPluginManager().disablePlugin(this);
        }

        getServer().getServicesManager().register(Economy.class, economyManager.economy, instance, ServicePriority.Normal); // регистрация экономики плагина в vault

        // регистрация плейсхолдеров
        new PlaceholderController("org.katok.moneyGain.Placeholder.Placeholders").register();

        // регистрация команд
        getCommand("money").setExecutor(new money());

        // запуск тасков
        Bukkit.getScheduler().runTaskTimer(instance, new Runnable() {
            @Override
            public void run() {
                for(Player player: Bukkit.getOnlinePlayers()) {
                    for(PermissionAttachmentInfo permission: player.getEffectivePermissions()) {
                        if(permission.getPermission().startsWith("moneygain.multiplier.")) {
                            float multiplier = Float.parseFloat(permission.getPermission().substring("moneygain.multiplier.".length()));

                            economyManager.economy.depositPlayer(player, getInt("money_count") * multiplier);
                            break;
                        }
                    }
                }
            }
        }, 300 * 20, 300 * 20);
    }
}
