package de.guildcraft.guildConomy.commands;

import java.util.Date;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.avaje.ebean.EbeanServer;

import de.guildcraft.guildConomy.GCPlugin;
import de.guildcraft.guildConomy.persistence.Account;
import de.guildcraft.guildConomy.persistence.Transaction;

public class GCPayCommand extends GCSubcommand {

	public GCPayCommand(GCPlugin plugin) {
		super(plugin);
		permission = "guildconomy.pay";
	}

	@Override
	public boolean execute(Player player, String[] args) {
		if(args.length > 3 || args.length < 2) {
			player.sendMessage(ChatColor.RED + "Bitte überprüfe die Argumente.");
			return true;
		}
		
		EbeanServer server = plugin.getDatabase();
		
		if(args.length == 3) {
			if(args[0].equalsIgnoreCase("vp")) {
				this.doVotepointsTransaction(player, args, server);
				return true;
			}
		}
		
		Account accountWithdraw = server.find(Account.class).where().ieq("username", player.getName()).findUnique();
		Account accountDeposit = server.find(Account.class).where().ieq("username", args[0]).findUnique();
		Transaction transaction = server.createEntityBean(Transaction.class);
		if(accountDeposit == null) {
			player.sendMessage(ChatColor.RED + "Der Spieler " + ChatColor.GRAY + args[0] + ChatColor.WHITE + " existiert nicht.");
			return true;
		}
		
		Player recipient = Bukkit.getPlayer(args[0]);
		
		double withdrawBalance = accountWithdraw.getTaler();
		double depositBalance = accountDeposit.getTaler();
		double amount = 0.0;
		try {
			amount = Double.parseDouble(args[1]);
		} catch (NumberFormatException e) {
			player.sendMessage(ChatColor.RED + "Bitte nur Zahlen als Betrag eingeben.");
			return true;
		}
		
		withdrawBalance = withdrawBalance - amount;
		depositBalance = depositBalance + amount;
		
		amount = Math.round(amount*100)/100.0;
		withdrawBalance = Math.round(withdrawBalance*100)/100.0;
		depositBalance = Math.round(depositBalance*100)/100.0;
		
		accountWithdraw.setTaler(withdrawBalance);
		accountDeposit.setTaler(depositBalance);
		
		transaction.setDepositor(player.getName());
		transaction.setRecipient(args[0]);
		transaction.setPayment(amount);
		transaction.setDate(new Date());
		
		server.update(accountWithdraw);
		server.update(accountDeposit);
		server.save(transaction);
		
		player.sendMessage(ChatColor.GOLD + "[GuildConomy] " + ChatColor.GRAY + "Du hast " + ChatColor.WHITE + 
				String.valueOf(amount) + " Taler" + ChatColor.GRAY + " an " + ChatColor.WHITE + args[0] + 
				ChatColor.GRAY + " überwiesen.");
		
		if(recipient != null) {
			recipient.sendMessage(ChatColor.GOLD + "[GuildConomy] " + ChatColor.WHITE + player.getName() + 
					ChatColor.GRAY + " hat dir " + ChatColor.WHITE + String.valueOf(amount) + " Taler " + 
					ChatColor.GRAY + "überwiesen.");
		}
		
		return true;
	}
	
	private void doVotepointsTransaction(Player player, String[] args, EbeanServer server) {
		
		Account accountWithdraw = server.find(Account.class).where().ieq("username", player.getName()).findUnique();
		Account accountDeposit = server.find(Account.class).where().ieq("username", args[1]).findUnique();
		if(accountDeposit == null) {
			player.sendMessage(ChatColor.RED + "Der Spieler " + ChatColor.GRAY + args[1] + ChatColor.WHITE + " existiert nicht.");
			return;
		}
		
		Player recipient = Bukkit.getPlayer(args[0]);
		
		int vpWithdrawBalance = accountWithdraw.getVotepoints();
		int vpDepositBalance = accountDeposit.getVotepoints();
		int vp = 0;
		try {
			vp = Integer.parseInt(args[2]);
		} catch (NumberFormatException e) {
			player.sendMessage(ChatColor.RED + "Bitte nur ganze Zahlen als Betrag eingeben.");
			return;
		}
		
		vpWithdrawBalance = vpWithdrawBalance - vp;
		vpDepositBalance = vpDepositBalance + vp;
		
		accountWithdraw.setVotepoints(vpWithdrawBalance);
		accountDeposit.setVotepoints(vpDepositBalance);
		
		server.update(accountWithdraw);
		server.update(accountDeposit);
		
		player.sendMessage(ChatColor.GOLD + "[GuildConomy] " + ChatColor.GRAY + "Du hast " + ChatColor.WHITE + 
				String.valueOf(vp) + " Votepoints" + ChatColor.GRAY + " an " + ChatColor.WHITE + args[1] + 
				ChatColor.GRAY + " überwiesen.");
		
		if(recipient != null) {
			recipient.sendMessage(ChatColor.GOLD + "[GuildConomy] " + ChatColor.WHITE + player.getName() + 
					ChatColor.GRAY + " hat dir " + ChatColor.WHITE + String.valueOf(vp) + " Votepoints " + 
					ChatColor.GRAY + "überwiesen.");
		}
		
	}

}
