package org.katok.moneyGain.Economy;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.OfflinePlayer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.katok.moneyGain.MoneyGain.*;

public class EconomyService implements Economy {
    public static final EconomyResponse BANKDONTSUPPORT = new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, instance.getName() + " does not support banks");;

    @Override
    public boolean isEnabled() {
        return instance.isEnabled();
    }

    @Override
    public String getName() {
        return instance.getName();
    }

    @Override
    public boolean hasBankSupport() {
        return false;
    }

    @Override
    public int fractionalDigits() {
        return -1;
    }

    @Override
    public String format(double v) {
        return currencyNamePlural() + String.format("%.3f", v);
    }

    @Override
    public String currencyNamePlural() {
        return "$";
    }

    @Override
    public String currencyNameSingular() {
        return "$";
    }

    @Override
    public boolean hasAccount(String s) {
        if(s == null) return false;

        return hasAccount(instance.getServer().getOfflinePlayer(s));
    }

    @Override
    public boolean hasAccount(OfflinePlayer offlinePlayer) {
        if(offlinePlayer == null) return false;

        return economy_cfg.contains(offlinePlayer.getUniqueId().toString());
    }

    @Override
    public boolean hasAccount(String s, String s1) {
        return hasAccount(s);
    }

    @Override
    public boolean hasAccount(OfflinePlayer offlinePlayer, String s) {
        return hasAccount(offlinePlayer);
    }

    @Override
    public double getBalance(String s) {
        if(s == null) return 0;

        return getBalance(instance.getServer().getOfflinePlayer(s));
    }

    @Override
    public double getBalance(OfflinePlayer offlinePlayer) {
        if(offlinePlayer == null) return 0;

        if(!hasAccount(offlinePlayer)) return 0;

        return economy_cfg.getInt(offlinePlayer.getUniqueId().toString() + ".value");
    }

    @Override
    public double getBalance(String s, String s1) {
        return getBalance(s);
    }

    @Override
    public double getBalance(OfflinePlayer offlinePlayer, String s) {
        return getBalance(offlinePlayer);
    }

    @Override
    public boolean has(String s, double v) {
        if(s == null) return false;

        return has(instance.getServer().getOfflinePlayer(s), v);
    }

    @Override
    public boolean has(OfflinePlayer offlinePlayer, double v) {
        if(offlinePlayer == null) return false;

        if(!economy_cfg.contains(offlinePlayer.getUniqueId().toString() + ".value")) return false;

        return economy_cfg.getInt(offlinePlayer.getUniqueId().toString() + ".value") >= v;
    }

    @Override
    public boolean has(String s, String s1, double v) {
        return has(s, v);
    }

    @Override
    public boolean has(OfflinePlayer offlinePlayer, String s, double v) {
        return has(offlinePlayer, v);
    }

    @Override
    public EconomyResponse withdrawPlayer(String s, double v) {
        if(s == null) return new EconomyResponse(0, 0, EconomyResponse.ResponseType.FAILURE, "Player name cannot be null!");

        return withdrawPlayer(instance.getServer().getOfflinePlayer(s), v);
    }

    @Override
    public EconomyResponse withdrawPlayer(OfflinePlayer offlinePlayer, double v) {
        if(offlinePlayer == null) return new EconomyResponse(0, 0, EconomyResponse.ResponseType.FAILURE, "Player name cannot be null!");
        if (v < 0) return new EconomyResponse(0, 0, EconomyResponse.ResponseType.FAILURE, "Cannot withdraw negative value!");
        if(!economy_cfg.contains(offlinePlayer.getUniqueId().toString() + ".value")) return new EconomyResponse(0, 0, EconomyResponse.ResponseType.FAILURE, "User does not exist!");

        economy_cfg.set(offlinePlayer.getUniqueId().toString() + ".value", getBalance(offlinePlayer) - v);

        try {
            economy_cfg.save(economy_file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return new EconomyResponse(v, getBalance(offlinePlayer), EconomyResponse.ResponseType.SUCCESS, null);
    }

    @Override
    public EconomyResponse withdrawPlayer(String s, String s1, double v) {
        return withdrawPlayer(s, v);
    }

    @Override
    public EconomyResponse withdrawPlayer(OfflinePlayer offlinePlayer, String s, double v) {
        return withdrawPlayer(offlinePlayer, v);
    }

    @Override
    public EconomyResponse depositPlayer(String s, double v) {
        if(s == null) return new EconomyResponse(0, 0, EconomyResponse.ResponseType.FAILURE, "Player name can not be null.");

        return depositPlayer(instance.getServer().getOfflinePlayer(s), v);
    }

    @Override
    public EconomyResponse depositPlayer(OfflinePlayer offlinePlayer, double v) {
        if(offlinePlayer == null) return new EconomyResponse(0, 0, EconomyResponse.ResponseType.FAILURE, "Player name cannot be null!");
        if (v < 0) return new EconomyResponse(0, 0, EconomyResponse.ResponseType.FAILURE, "Cannot withdraw negative value!");
        if(!economy_cfg.contains(offlinePlayer.getUniqueId().toString() + ".value")) {
            economy_cfg.set(offlinePlayer.getUniqueId().toString() + ".value", v);
            return new EconomyResponse(v, v, EconomyResponse.ResponseType.SUCCESS, null);
        }

        economy_cfg.set(offlinePlayer.getUniqueId().toString() + ".value", getBalance(offlinePlayer) + v);

        try {
            economy_cfg.save(economy_file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return new EconomyResponse(v, getBalance(offlinePlayer), EconomyResponse.ResponseType.SUCCESS, null);
    }

    @Override
    public EconomyResponse depositPlayer(String s, String s1, double v) {
        return depositPlayer(s, v);
    }

    @Override
    public EconomyResponse depositPlayer(OfflinePlayer offlinePlayer, String s, double v) {
        return depositPlayer(offlinePlayer, v);
    }

    @Override
    public EconomyResponse createBank(String s, String s1) {
        return BANKDONTSUPPORT;
    }

    @Override
    public EconomyResponse createBank(String s, OfflinePlayer offlinePlayer) {
        return BANKDONTSUPPORT;
    }

    @Override
    public EconomyResponse deleteBank(String s) {
        return BANKDONTSUPPORT;
    }

    @Override
    public EconomyResponse bankBalance(String s) {
        return BANKDONTSUPPORT;
    }

    @Override
    public EconomyResponse bankHas(String s, double v) {
        return BANKDONTSUPPORT;
    }

    @Override
    public EconomyResponse bankWithdraw(String s, double v) {
        return BANKDONTSUPPORT;
    }

    @Override
    public EconomyResponse bankDeposit(String s, double v) {
        return BANKDONTSUPPORT;
    }

    @Override
    public EconomyResponse isBankOwner(String s, String s1) {
        return BANKDONTSUPPORT;
    }

    @Override
    public EconomyResponse isBankOwner(String s, OfflinePlayer offlinePlayer) {
        return BANKDONTSUPPORT;
    }

    @Override
    public EconomyResponse isBankMember(String s, String s1) {
        return BANKDONTSUPPORT;
    }

    @Override
    public EconomyResponse isBankMember(String s, OfflinePlayer offlinePlayer) {
        return BANKDONTSUPPORT;
    }

    @Override
    public List<String> getBanks() {
        return new ArrayList<>();
    }

    @Override
    public boolean createPlayerAccount(String s) {
        if(s == null) return false;

        return createPlayerAccount(instance.getServer().getOfflinePlayer(s));
    }

    @Override
    public boolean createPlayerAccount(OfflinePlayer offlinePlayer) {
        if(hasAccount(offlinePlayer)) return false;

        economy_cfg.set(offlinePlayer.getUniqueId().toString() + ".value", 0);

        try {
            economy_cfg.save(economy_file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return true;
    }

    @Override
    public boolean createPlayerAccount(String s, String s1) {
        return createPlayerAccount(s);
    }

    @Override
    public boolean createPlayerAccount(OfflinePlayer offlinePlayer, String s) {
        return createPlayerAccount(offlinePlayer);
    }
}
