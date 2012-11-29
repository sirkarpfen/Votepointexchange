package de.guildcraft.guildConomy.listener;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import com.avaje.ebean.EbeanServer;
import com.vexsoftware.votifier.model.Vote;
import com.vexsoftware.votifier.model.VotifierEvent;

import de.guildcraft.guildConomy.GCPlugin;
import de.guildcraft.guildConomy.persistence.Account;

public class VotifierEventHandler implements Listener {
	
	GCPlugin plugin;
	
	public VotifierEventHandler(GCPlugin plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler(priority=EventPriority.NORMAL)
	public void onVotifierEvent(VotifierEvent e) {
		Vote vote = e.getVote();
		if(vote != null) {
			String playerName = vote.getUsername();
			Player player = plugin.getServer().getPlayer(playerName);
			int votepoints = plugin.getConfig().getInt("votepoints");
			
			EbeanServer server = plugin.getDatabase();
			Account account = server.find(Account.class).where().ieq("username", playerName).findUnique();
			
			account.setVotePoints(account.getVotePoints() + votepoints);
			server.update(account);
			
			player.sendMessage(ChatColor.GREEN + "[GuildConomy] " + 
					ChatColor.WHITE + "Dein Vote ist eingegangen und dir wurden: " + 
					ChatColor.GREEN + votepoints + ChatColor.WHITE + " Votepoints überwiesen.");
		}
	}
}
