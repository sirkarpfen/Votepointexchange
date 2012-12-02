package de.guildcraft.guildConomy.commands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.avaje.ebean.EbeanServer;

import de.guildcraft.guildConomy.GCPlugin;
import de.guildcraft.guildConomy.persistence.Account;

public class GCGiveCommand extends GCSubcommand {

	public GCGiveCommand(GCPlugin plugin) {
		super(plugin);
		permission = "guildconomy.admin.give";
	}

	@Override
	public boolean execute(Player player, String[] args) {
		if(args.length != 2) {
			player.sendMessage(ChatColor.RED + "Bitte überprüfe die Argumente.");
			return true;
		}
		
		EbeanServer server = plugin.getDatabase();
		Account account = server.find(Account.class).where().ieq("username", args[0]).findUnique();
		if(account == null) {
			player.sendMessage(ChatColor.RED + "Der Spieler " + ChatColor.GRAY + args[0] + ChatColor.WHITE + " existiert nicht.");
			return true;
		}
		
		double balance = account.getTaler();
		double amount = 0.0;
		try {
			amount = Double.parseDouble(args[1]);
		} catch (NumberFormatException e) {
			player.sendMessage(ChatColor.RED + "Bitte nur Zahlen als Betrag eingeben.");
			return true;
		}
		
		balance = balance + amount;
		balance = Math.round(balance*100)/100.0;
		
		account.setTaler(balance);
		
		server.update(account);
		
		player.sendMessage(ChatColor.GOLD + "[GuildConomy] " + ChatColor.GRAY + "Du hast dem Account von " + 
				ChatColor.WHITE + args[1] + ChatColor.GRAY + ", " + ChatColor.WHITE + args[0] + ChatColor.GRAY + " hinzugefügt.");
		
		return true;
	}

}
