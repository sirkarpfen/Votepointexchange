package de.guildcraft.guildConomy.commands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.avaje.ebean.EbeanServer;

import de.guildcraft.guildConomy.GCPlugin;
import de.guildcraft.guildConomy.persistence.Account;

public class GCVotepointsCommand extends GCSubcommand {

	public GCVotepointsCommand(GCPlugin plugin) {
		super(plugin);
		permission = "guildconomy.balance";
	}

	@Override
	public boolean execute(Player player, String[] args) {
		if(args.length != 0) {
			player.sendMessage(ChatColor.RED + "Bitte überprüfe die Schreibweise.");
			return true;
		}
		
		EbeanServer server = plugin.getDatabase();
		Account account = server.find(Account.class).where().ieq("username", player.getName()).findUnique();
		
		player.sendMessage(ChatColor.GOLD + "[GuildConomy] Votepoints: " + ChatColor.WHITE + account.getVotepoints());
		return true;
	}

}
