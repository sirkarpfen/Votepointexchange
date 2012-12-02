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
		
		player.sendMessage(ChatColor.GOLD + "GuildConomy command usage: " + ChatColor.AQUA + "<param> = Pflichtfeld " + 
				ChatColor.GRAY + "[param] = Optional");
		
		if(player.hasPermission("guildconomy.admin.*")) {
			player.sendMessage(ChatColor.GOLD + "/money set <Spieler> <Betrag>" + ChatColor.GRAY + " - Setzt den Kontostand auf <amount>.");
			player.sendMessage(ChatColor.GOLD + "/money clear <Spieler>" + ChatColor.GRAY + " - Löscht den gesamten Kontostand.");	
		}
		
		player.sendMessage(ChatColor.GOLD + "/money" + ChatColor.GRAY + " - Zeigt den aktuellen Kontostand an.");
		player.sendMessage(ChatColor.GOLD + "/money pay <Spieler> <Betrag>" + ChatColor.GRAY + " - Bezahlt <Betrag> an <Spieler>");
		player.sendMessage(ChatColor.GOLD + "/money top" + ChatColor.GRAY + " - Zeigt die Money-Top Liste an.");
		
		return true;
	}

}
