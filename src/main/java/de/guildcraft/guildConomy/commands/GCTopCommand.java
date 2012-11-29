package de.guildcraft.guildConomy.commands;

import org.bukkit.entity.Player;

import de.guildcraft.guildConomy.GCPlugin;

public class GCTopCommand extends GCSubcommand {

	public GCTopCommand(GCPlugin plugin) {
		super(plugin);
		permission = "guildconomy.top";
	}

	@Override
	public boolean execute(Player player, String[] args) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
