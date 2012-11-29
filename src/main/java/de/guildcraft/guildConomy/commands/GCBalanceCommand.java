package de.guildcraft.guildConomy.commands;

import org.bukkit.entity.Player;

import de.guildcraft.guildConomy.GCPlugin;

public class GCBalanceCommand extends GCSubcommand {

	public GCBalanceCommand(GCPlugin plugin) {
		super(plugin);
		permission = "guildconomy.balance";
	}

	@Override
	public boolean execute(Player player, String[] args) {
		// TODO Auto-generated method stub
		return false;
	}

}
