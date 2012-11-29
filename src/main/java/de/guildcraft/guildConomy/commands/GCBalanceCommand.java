package de.guildcraft.guildConomy.commands;

import org.bukkit.entity.Player;

import com.avaje.ebean.EbeanServer;

import de.guildcraft.guildConomy.GCPlugin;
import de.guildcraft.guildConomy.persistence.Account;

public class GCBalanceCommand extends GCSubcommand {

	public GCBalanceCommand(GCPlugin plugin) {
		super(plugin);
		permission = "guildconomy.balance";
	}

	@Override
	public boolean execute(Player player, String[] args) {
		EbeanServer server = plugin.getDatabase();
		Account account = server.find(Account.class).where().ieq("username", player.getName()).findUnique();
		player.sendMessage(String.valueOf(account.getTaler()));
		return true;
	}

}
