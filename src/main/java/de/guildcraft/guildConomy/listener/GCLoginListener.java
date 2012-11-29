package de.guildcraft.guildConomy.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.avaje.ebean.EbeanServer;

import de.guildcraft.guildConomy.GCPlugin;
import de.guildcraft.guildConomy.persistence.Account;

public class GCLoginListener implements Listener{
	
	GCPlugin plugin;
	
	public GCLoginListener(GCPlugin plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onPlayerLogin(PlayerJoinEvent e) {
		
		Player player = e.getPlayer();
		EbeanServer server = plugin.getDatabase();
		Account account = server.find(Account.class).where().ieq("username", player.getName()).findUnique();
		
		if(account == null) {
			account = server.createEntityBean(Account.class);
			account.setUsername(player.getName());
			account.setTaler(plugin.getConfig().getDouble("general.startmoney"));
			account.setVotePoints(0);
			server.save(account);
		}
	}
	
}
