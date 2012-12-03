package de.guildcraft.guildConomy.commands;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.avaje.ebean.EbeanServer;

import de.guildcraft.guildConomy.GCPlugin;
import de.guildcraft.guildConomy.persistence.Account;

public class GCTopCommand extends GCSubcommand {

	public GCTopCommand(GCPlugin plugin) {
		super(plugin);
		permission = "guildconomy.top";
	}

	@Override
	public boolean execute(Player player, String[] args) {
		
		if(args.length != 0) {
			player.sendMessage(ChatColor.RED + "Zu viele Argumente.");
			return true;
		}
		
		EbeanServer server = plugin.getDatabase();
		List<Account> topList = server.find(Account.class).orderBy().desc("taler").findList();
		if(topList.isEmpty()) {
			player.sendMessage(ChatColor.RED + "Die Top-Liste ist leer.");
			return true;
		}
		
		player.sendMessage(ChatColor.GOLD + "-----[ " + ChatColor.WHITE + "Die reichsten Spieler" + ChatColor.GOLD + " ]-----");
		for(int i = 0; i < 5; i++) {
			if(i > (topList.size() - 1)) {
				continue;
			}
			Account account = topList.get(i);
			String username = account.getUsername();
			String taler = String.valueOf(account.getTaler());
			player.sendMessage((i+1) + ". " + ChatColor.GOLD + username + ChatColor.WHITE + " - " + ChatColor.GRAY + taler);
		}
		
		return true;
	}
	
}
