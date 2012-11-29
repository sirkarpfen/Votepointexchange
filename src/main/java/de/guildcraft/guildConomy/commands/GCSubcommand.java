package de.guildcraft.guildConomy.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.guildcraft.guildConomy.GCPlugin;

abstract public class GCSubcommand
{
	//---------------------------------------------------------------------------------------------
	
	protected GCPlugin plugin;
	protected String permission = "";
	
	//---------------------------------------------------------------------------------------------
	
	public GCSubcommand(GCPlugin plugin) {
		this.plugin = plugin;
	}
	
	//---------------------------------------------------------------------------------------------
	
	public boolean permissionExecute(CommandSender sender, String[] args) {
		if(!(sender instanceof Player)) {
			sender.sendMessage("Dieses Command kann nur Ingame ausgeführt werden!");
			return true;
		}
		if(permission.isEmpty())
			plugin.getLogger().warning("Did you forget to set a permission?" + this.getClass().getName());
		if(sender.hasPermission(permission) || sender.isOp())
			return execute((Player) sender, args);
		sender.sendMessage(ChatColor.RED + "Du hast nicht die nötigen Rechte für diesen Befehl");
		return true;
	}
	
	//---------------------------------------------------------------------------------------------
	
	abstract public boolean execute(Player player, String[] args);
	
	//---------------------------------------------------------------------------------------------
}
