package org.katok.moneyGain.Permissions;

import net.milkbowl.vault.permission.Permission;
import org.bukkit.plugin.RegisteredServiceProvider;

import static org.katok.moneyGain.MoneyGain.instance;

public class PermissionManager {
    public Permission permission = null;

    public PermissionManager() {
        if (instance.getServer().getPluginManager().getPlugin("Vault") == null) {
            return;
        }
        RegisteredServiceProvider<Permission> rsp = instance.getServer().getServicesManager().getRegistration(Permission.class);
        if (rsp == null) {
            return;
        }
        permission = rsp.getProvider();
    }

    public Boolean load_successful() {
        return permission != null;
    }
}
