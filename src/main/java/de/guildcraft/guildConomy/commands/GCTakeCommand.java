package de.guildcraft.guildConomy.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.avaje.ebean.EbeanServer;

import de.guildcraft.guildConomy.GCPlugin;
import de.guildcraft.guildConomy.persistence.Account;

public class GCTakeCommand extends GCSubcommand {

	public GCTakeCommand(GCPlugin plugin) {
		super(plugin);
		permission = "guildconomy.admin.take";
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
			player.sendMessage(ChatColor.RED + "Der Spieler " + ChatColor.GRAY + args[0] + " existiert nicht.");
			return true;
		}
		
		Player recipient = Bukkit.getPlayer(args[0]);
		
		double balance = account.getTaler();
		double amount = 0.0;
		try {
			amount = Math.round(Double.parseDouble(args[1])*100)/100;
		} catch (NumberFormatException e) {
			player.sendMessage(ChatColor.RED + "Bitte nur Zahlen als Betrag eingeben.");
			return true;
		}
		
		if(amount < 0) {
			player.sendMessage(ChatColor.RED + "Bitte keine negativen Werte eingeben.");
			return true;
		}
		
		balance = balance - amount;
		
		if(balance < 0) {
			player.sendMessage(ChatColor.RED + "Du willst dem Spieler mehr abziehen, als er besitzt.");
			return true;
		}
		
		balance = Math.round(balance*100)/100.0;
		
		account.setTaler(balance);
		
		server.update(account);
		
		player.sendMessage(ChatColor.GOLD + "[GuildConomy] " + ChatColor.GRAY + "Du hast dem Account von " + 
				ChatColor.WHITE + args[0] + ChatColor.GRAY + ", " + ChatColor.WHITE + String.valueOf(amount) + " Taler" +
				ChatColor.GRAY + " abgezogen.");
		
		if(recipient != null) {
			recipient.sendMessage(ChatColor.GOLD + "[GuildConomy] " + ChatColor.GRAY + "Dir wurden " +
					ChatColor.WHITE + String.valueOf(amount) +" Taler " + ChatColor.GRAY + "abgezogen.");
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
		
		int vpBalance = account.getVotepoints();
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
		
		vpBalance = vpBalance - vp;
		
		if(vpBalance < 0) {
			player.sendMessage(ChatColor.RED + "Du willst dem Spieler mehr abziehen, als er besitzt.");
			return;
		}
		
		account.setVotepoints(vpBalance);
		server.update(account);
		
		player.sendMessage(ChatColor.GOLD + "[GuildConomy] " + ChatColor.GRAY + "Du hast dem Account von " + 
				ChatColor.WHITE + args[1] + ChatColor.GRAY + ", " + ChatColor.WHITE + String.valueOf(vp) + " Votepoints" +
				ChatColor.GRAY + " abgezogen.");
		
		if(recipient != null) {
			recipient.sendMessage(ChatColor.GOLD + "[GuildConomy] " + ChatColor.GRAY + "Dir wurden " + 
					ChatColor.WHITE + String.valueOf(vp) + " Votepoints " + ChatColor.GRAY + "abgezogen.");
		}
		
	}

}
