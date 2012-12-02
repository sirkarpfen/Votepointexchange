package de.guildcraft.guildConomy.commands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.avaje.ebean.EbeanServer;

import de.guildcraft.guildConomy.GCPlugin;
import de.guildcraft.guildConomy.persistence.Account;

public class GCBalanceCommand extends GCSubcommand {

	public GCBalanceCommand(GCPlugin plugin) {
		super(plugin);
		permission = "guildconomy.balance";
	}

	@Override
	public boolean execute(Player player, String[] args) {
		if(args.length > 1) {
			player.sendMessage(ChatColor.RED + "Bitte überprüfe die Argumente.");
			return true;
		}
		
		EbeanServer server = plugin.getDatabase();
		Account account = server.find(Account.class).where().ieq("username", player.getName()).findUnique();
		if(args.length == 1 && args[0].equalsIgnoreCase("-v")) {
			player.sendMessage(ChatColor.GOLD + "[GuildConomy] Votepoints: " + ChatColor.WHITE +
					String.valueOf(account.getVotePoints()));
		}
		
		player.sendMessage(ChatColor.GOLD + "[GuildConomy] Balance: " + ChatColor.WHITE + String.valueOf(account.getTaler()));
		return true;
	}

}
