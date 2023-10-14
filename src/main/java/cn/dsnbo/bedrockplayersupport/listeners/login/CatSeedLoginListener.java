package cn.dsnbo.bedrockplayersupport.listeners.login;

import cc.baka9.catseedlogin.bukkit.Config;
import cc.baka9.catseedlogin.bukkit.database.Cache;
import cc.baka9.catseedlogin.bukkit.object.LoginPlayer;
import cc.baka9.catseedlogin.bukkit.object.LoginPlayerHelper;
import cn.dsnbo.bedrockplayersupport.BedrockPlayerSupport;
import cc.baka9.catseedlogin.bukkit.CatSeedLoginAPI;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.geysermc.floodgate.api.FloodgateApi;

/**
 * @author DongShaoNB
 */
public class CatSeedLoginListener implements Listener {
    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        if (FloodgateApi.getInstance().isFloodgatePlayer(player.getUniqueId())) {
            if (!CatSeedLoginAPI.isLogin(player.getName())) {
                if (BedrockPlayerSupport.getInstance().getConfig().getBoolean("login.enable")) {
                    // TODO: 等待修复上线有几率回到重生点并提示登录的问题, 但是再次重进可以解决
                    LoginPlayerHelper.add(new LoginPlayer(player.getName(), ""));
                    Cache.refresh(player.getName());
                    if (Config.Settings.AfterLoginBack && Config.Settings.CanTpSpawnLocation) {
                        Config.getOfflineLocation(player).ifPresent(player::teleport);
                    }
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', BedrockPlayerSupport.getInstance().getConfig().getString("login.auto-message")));
                }
            }
        }
    }
}
