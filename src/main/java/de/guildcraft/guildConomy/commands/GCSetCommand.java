package de.guildcraft.guildConomy.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.avaje.ebean.EbeanServer;

import de.guildcraft.guildConomy.GCPlugin;
import de.guildcraft.guildConomy.persistence.Account;

public class GCSetCommand extends GCSubcommand {

	public GCSetCommand(GCPlugin plugin) {
		super(plugin);
		permission = "guildconomy.admin.set";
	}

	@Override
	public boolean execute(Player player, String[] args) {
		
		if(args.length > 3 || args.length < 2) {
			player.sendMessage(ChatColor.RED + "Bitte überprüfe die Argumente.");
			return true;
		}
		
		EbeanServer server = plugin.getDatabase();
		
		if(args.length == 3) {
			if(args[0].equalsIgnoreCase("vp")) {
				this.doVotepointsTransaction(player, args, server);
				return true;
			}
		}
		
		Account account = server.find(Account.class).where().ieq("username", args[0]).findUnique();
		if(account == null) {
			player.sendMessage(ChatColor.RED + "Der Spieler " + ChatColor.GRAY + args[0] + ChatColor.RED + " existiert nicht.");
			return true;
		}
		
		Player recipient = Bukkit.getPlayer(args[0]);
		
		double amount = 0;
		try {
			amount = Math.round(Double.parseDouble(args[1])*100)/100.0;
		} catch (NumberFormatException e) {
			player.sendMessage(ChatColor.RED + "Bitte nur Zahlen als Betrag eingeben.");
			return true;
		}
		
		if(amount < 0) {
			player.sendMessage(ChatColor.RED + "Bitte keine negativen Werte eingeben.");
			return true;
		}
		
		account.setTaler(amount);
		server.update(account);
		
		player.sendMessage(ChatColor.GOLD + "[GuildConomy] " + ChatColor.GRAY + args[0] + "\'s Account wurde auf " +
				ChatColor.WHITE + String.valueOf(amount) + " Taler " + ChatColor.GRAY + "gesetzt.");
		
		if(recipient != null) {
			recipient.sendMessage(ChatColor.GOLD + "[GuildConomy] " + ChatColor.GRAY + "Dein Konto wurde auf " + 
					ChatColor.WHITE + String.valueOf(amount) + " Taler " + ChatColor.GRAY + "gesetzt.");
		}
		
		return true;
	}
	
	private void doVotepointsTransaction(Player player, String[] args, EbeanServer server) {
		
		Account account = server.find(Account.class).where().ieq("username", args[1]).findUnique();
		if(account == null) {
			player.sendMessage(ChatColor.RED + "Der Spieler " + ChatColor.GRAY + args[1] + ChatColor.RED + " existiert nicht.");
			return;
		}
		
		Player recipient = Bukkit.getPlayer(args[1]);
		
		int vp = 0;
		try {
			vp = Integer.parseInt(args[2]);
		} catch (NumberFormatException e) {
			player.sendMessage(ChatColor.RED + "Bitte nur ganze Zahlen als Betrag eingeben.");
			return;
		}
		
		if(vp < 0) {
			player.sendMessage(ChatColor.RED + "Bitte keine negativen Werte eingeben.");
			return;
		}
		
		account.setVotepoints(vp);
		server.update(account);
		
		player.sendMessage(ChatColor.GOLD + "[GuildConomy] " + ChatColor.GRAY + args[1] + "\'s Account wurde auf " +
				ChatColor.WHITE + String.valueOf(vp) + " Votepoints " + ChatColor.GRAY + "gesetzt.");
		
		if(recipient != null) {
			recipient.sendMessage(ChatColor.GOLD + "[GuildConomy] " + ChatColor.GRAY + "Dein Konto wurde auf " + 
					ChatColor.WHITE + String.valueOf(vp) + " Votepoints " + ChatColor.GRAY + "gesetzt.");
		}
		
	}

}
