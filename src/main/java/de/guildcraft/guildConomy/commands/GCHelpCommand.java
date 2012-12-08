package de.guildcraft.guildConomy.commands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import de.guildcraft.guildConomy.GCPlugin;

public class GCHelpCommand extends GCSubcommand {

	public GCHelpCommand(GCPlugin plugin) {
		super(plugin);
		permission = "guildconomy.help";
	}

	@Override
	public boolean execute(Player player, String[] args) {
		
		if(args.length != 0) {
			player.sendMessage(ChatColor.RED + "Bitte überprüfe die Schreibweise.");
			return true;
		}
		
		player.sendMessage(ChatColor.GOLD + "GuildConomy command usage: " + ChatColor.AQUA + "<param> = Pflichtfeld " + 
				ChatColor.GRAY + "[param] = Optional");
		
		if(player.hasPermission("guildconomy.admin.*")) {
			player.sendMessage(ChatColor.GOLD + "/money set <Spieler> <Betrag>" + ChatColor.GRAY + " - Setzt den Kontostand auf <amount>.");
			player.sendMessage(ChatColor.GOLD + "/money clear <Spieler>" + ChatColor.GRAY + " - Löscht den gesamten Kontostand.");
			player.sendMessage(ChatColor.GOLD + "/money give <Spieler>" + ChatColor.GRAY + " - Gibt dem Spieler Taler.");
			player.sendMessage(ChatColor.GOLD + "/money take <Spieler>" + ChatColor.GRAY + " - Nimmt dem Spieler Taler.");
			player.sendMessage(ChatColor.GOLD + "/money check <Spieler>" + ChatColor.GRAY + " - Zeigt den Kontostand des Spielers an.");
		}
		
		player.sendMessage(ChatColor.GOLD + "/money" + ChatColor.GRAY + " - Zeigt den aktuellen Kontostand an.");
		player.sendMessage(ChatColor.GOLD + "/money vp" + ChatColor.GRAY + " - Zeigt die aktuellen Votepoints an.");
		player.sendMessage(ChatColor.GOLD + "/money help" + ChatColor.GRAY + " - Zeigt diese Hilfeseite an.");
		player.sendMessage(ChatColor.GOLD + "/money pay <Spieler> <Betrag>" + ChatColor.GRAY + " - Bezahlt <Betrag> an <Spieler>");
		player.sendMessage(ChatColor.GOLD + "/money top" + ChatColor.GRAY + " - Zeigt die Money-Top Liste an.");
		player.sendMessage(ChatColor.GOLD + "/money record <in|out>" + ChatColor.GRAY + " - Zeigt die Money-Top Liste an.");
		
		return true;
	}

}
