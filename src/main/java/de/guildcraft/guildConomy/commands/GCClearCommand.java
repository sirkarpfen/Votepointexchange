package de.guildcraft.guildConomy.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.avaje.ebean.EbeanServer;

import de.guildcraft.guildConomy.GCPlugin;
import de.guildcraft.guildConomy.persistence.Account;

public class GCClearCommand extends GCSubcommand {

	public GCClearCommand(GCPlugin plugin) {
		super(plugin);
		permission = "guildconomy.admin.clear";
	}

	@Override
	public boolean execute(Player player, String[] args) {
		if(args.length > 2 || args.length < 1) {
			player.sendMessage(ChatColor.RED + "Bitte überprüfe die Argumente.");
			return true;
		}
		
		EbeanServer server = plugin.getDatabase();
		
		if(args.length == 2) {
			if(args[0].equalsIgnoreCase("vp")) {
				this.doVotepointsTransaction(player, args, server);
				return true;
			}
		}
		
		Account account = server.find(Account.class).where().ieq("username", args[0]).findUnique();
		if(account == null) {
			player.sendMessage(ChatColor.RED + "Der Spieler " + ChatColor.GRAY + args[0] + ChatColor.WHITE + " existiert nicht.");
			return true;
		}
		
		Player recipient = Bukkit.getPlayer(args[0]);
		
		account.setTaler(0.0);
		server.update(account);
		
		player.sendMessage(ChatColor.GOLD + "[GuildConomy] " + ChatColor.GRAY + args[0] + "\'s Taler wurden gelöscht.");
		
		if(recipient != null) {
			recipient.sendMessage(ChatColor.GOLD + "[GuildConomy] " + ChatColor.GRAY + "Deine Taler wurden gelöscht.");
		}
		
		return true;
	}
	
	private void doVotepointsTransaction(Player player, String[] args, EbeanServer server) {
		
		Account account = server.find(Account.class).where().ieq("username", args[1]).findUnique();
		if(account == null) {
			player.sendMessage(ChatColor.RED + "Der Spieler " + ChatColor.GRAY + args[1] + ChatColor.WHITE + " existiert nicht.");
			return;
		}
		
		Player recipient = Bukkit.getPlayer(args[1]);
		
		account.setVotepoints(0);
		server.update(account);
		
		player.sendMessage(ChatColor.GOLD + "[GuildConomy] " + ChatColor.GRAY + args[0] + "\'s Votepoints wurden gelöscht.");
		
		if(recipient != null) {
			recipient.sendMessage(ChatColor.GOLD + "[GuildConomy] " + ChatColor.GRAY + "Deine Votepoints wurden gelöscht.");
		}
		
	}

}
