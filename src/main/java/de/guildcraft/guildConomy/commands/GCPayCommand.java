package de.guildcraft.guildConomy.commands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.avaje.ebean.EbeanServer;

import de.guildcraft.guildConomy.GCPlugin;
import de.guildcraft.guildConomy.persistence.Account;

public class GCPayCommand extends GCSubcommand {

	public GCPayCommand(GCPlugin plugin) {
		super(plugin);
		permission = "guildconomy.pay";
	}

	@Override
	public boolean execute(Player player, String[] args) {
		if(args.length != 2) {
			player.sendMessage(ChatColor.RED + "Bitte überprüfe die Argumente.");
			return true;
		}
		
		EbeanServer server = plugin.getDatabase();
		Account accountWithdraw = server.find(Account.class).where().ieq("username", player.getName()).findUnique();
		Account accountDeposit = server.find(Account.class).where().ieq("username", args[0]).findUnique();
		if(accountDeposit == null) {
			player.sendMessage(ChatColor.RED + "Der Spieler " + ChatColor.GRAY + args[0] + ChatColor.WHITE + " existiert nicht.");
			return true;
		}
		
		double withdrawBalance = accountWithdraw.getTaler();
		double depositBalance = accountDeposit.getTaler();
		double amount = 0.0;
		try {
			amount = Double.parseDouble(args[1]);
		} catch (NumberFormatException e) {
			player.sendMessage(ChatColor.RED + "Bitte nur Zahlen als Betrag eingeben.");
			return true;
		}
		
		withdrawBalance = withdrawBalance - amount;
		depositBalance = depositBalance + amount;
		
		withdrawBalance = Math.round(withdrawBalance*100)/100.0;
		depositBalance = Math.round(depositBalance*100)/100.0;
		
		accountWithdraw.setTaler(withdrawBalance);
		accountDeposit.setTaler(depositBalance);
		
		server.update(accountWithdraw);
		server.update(accountDeposit);
		
		player.sendMessage(ChatColor.GOLD + "[GuildConomy] " + ChatColor.GRAY + "Du hast " + ChatColor.WHITE + args[1] +
				ChatColor.GRAY + " an " + ChatColor.WHITE + args[0] + ChatColor.GRAY + " überwiesen.");
		return true;
	}

}
