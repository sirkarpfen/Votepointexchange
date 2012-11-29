package de.guildcraft.guildConomy.commands;

import org.bukkit.entity.Player;

import de.guildcraft.guildConomy.GCPlugin;

public class GCHelpCommand extends GCSubcommand {

	public GCHelpCommand(GCPlugin plugin) {
		super(plugin);
		permission = "guildconomy.help";
	}

	@Override
	public boolean execute(Player player, String[] args) {
		// TODO Auto-generated method stub
		return false;
	}

}
