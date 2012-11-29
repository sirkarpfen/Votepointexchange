package de.guildcraft.guildConomy.commands;

import org.bukkit.entity.Player;

import de.guildcraft.guildConomy.GCPlugin;

public class GCDeleteCommand extends GCSubcommand {

	public GCDeleteCommand(GCPlugin plugin) {
		super(plugin);
		permission = "guildconomy.delete";
	}

	@Override
	public boolean execute(Player player, String[] args) {
		// TODO Auto-generated method stub
		return false;
	}

}
