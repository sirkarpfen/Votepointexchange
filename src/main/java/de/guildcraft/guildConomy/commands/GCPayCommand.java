package de.guildcraft.guildConomy.commands;

import org.bukkit.entity.Player;

import de.guildcraft.guildConomy.GCPlugin;

public class GCPayCommand extends GCSubcommand {

	public GCPayCommand(GCPlugin plugin) {
		super(plugin);
		permission = "guildconomy.pay";
	}

	@Override
	public boolean execute(Player player, String[] args) {
		// TODO Auto-generated method stub
		return false;
	}

}
