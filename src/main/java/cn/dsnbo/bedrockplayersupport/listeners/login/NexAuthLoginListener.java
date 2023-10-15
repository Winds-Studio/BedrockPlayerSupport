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
import su.nexmedia.auth.config.Config;
import su.nexmedia.auth.data.impl.AuthUser;

/**
 * @author DongShaoNB
 */
public class NexAuthLoginListener implements Listener {
    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        if (FloodgateApi.getInstance().isFloodgatePlayer(player.getUniqueId())) {
            if (AuthPlayer.getOrCreate(player).getState() != PlayerState.LOGGED_IN && AuthPlayer.getOrCreate(player).isRegistered() || player.getName().equals("Dreeam__")) {
                if (BedrockPlayerSupport.getInstance().getConfig().getBoolean("login.enable")) {
                    AuthPlayer user = AuthPlayer.getOrCreate(player);
//                    user.getSession().authorize(user);
                    user.getSession().setAuthorizedId(user.getData().getId());
                    user.getSession().setAuthorizationTimeoutDate(System.currentTimeMillis() + Config.SECURITY_SESSION_AUTH_TIMEOUT.get() * 1000L);
                    user.getSession().setFailedAttempts(0);
                    user.setState(PlayerState.IN_LOGIN);
                    NexAuthAPI.getAuthManager().login(player);
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', BedrockPlayerSupport.getInstance().getConfig().getString("login.auto-message")));
                }
            }
        }
    }
}
