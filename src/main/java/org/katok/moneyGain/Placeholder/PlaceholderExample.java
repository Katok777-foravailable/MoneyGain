package org.katok.moneyGain.Placeholder;

import org.bukkit.OfflinePlayer;

public interface PlaceholderExample { // было сказано, надо чтобы плагин легко расширялся..)
    abstract public String getString();
    abstract public String getValue(OfflinePlayer offlinePlayer);
}
