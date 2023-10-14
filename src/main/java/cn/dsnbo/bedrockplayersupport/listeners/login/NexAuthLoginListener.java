package cn.dsnbo.bedrockplayersupport.listeners.login;

import cn.dsnbo.bedrockplayersupport.BedrockPlayerSupport;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.geysermc.floodgate.api.FloodgateApi;
import su.nexmedia.auth.NexAuthAPI;
import su.nexmedia.auth.auth.impl.AuthPlayer;
import su.nexmedia.auth.auth.impl.PlayerState;

/**
 * @author DongShaoNB
 */
public class NexAuthLoginListener implements Listener {
    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        if (FloodgateApi.getInstance().isFloodgatePlayer(player.getUniqueId())) {
            if (AuthPlayer.getOrCreate(player).getState() != PlayerState.LOGGED_IN) {
                if (BedrockPlayerSupport.getInstance().getConfig().getBoolean("login.enable")) {
                    AuthPlayer user = AuthPlayer.getOrCreate(player);
                    user.getData().setPassword("p#9.aFf0+ZApefw", user.getData().getEncryptionType());
                    user.setState(PlayerState.IN_LOGIN);
                    NexAuthAPI.getAuthManager().login(player);
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', BedrockPlayerSupport.getInstance().getConfig().getString("login.auto-message")));
                }
            }
        }
    }
}
