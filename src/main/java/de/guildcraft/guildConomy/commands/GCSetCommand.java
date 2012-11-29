package de.guildcraft.guildConomy.commands;

import org.bukkit.entity.Player;

import de.guildcraft.guildConomy.GCPlugin;

public class GCSetCommand extends GCSubcommand {

	public GCSetCommand(GCPlugin plugin) {
		super(plugin);
		permission = "guildconomy.set";
	}

	@Override
	public boolean execute(Player player, String[] args) {
		// TODO Auto-generated method stub
		return false;
	}

}
