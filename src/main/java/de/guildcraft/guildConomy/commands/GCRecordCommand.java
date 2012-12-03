package de.guildcraft.guildConomy.commands;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.avaje.ebean.EbeanServer;

import de.guildcraft.guildConomy.GCPlugin;
import de.guildcraft.guildConomy.persistence.Transaction;

public class GCRecordCommand extends GCSubcommand {
	
	private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
	private final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
	private final int defEntries = plugin.getConfig().getInt("general.max_record_entries");
	
	public GCRecordCommand(GCPlugin plugin) {
		super(plugin);
		permission = "guildconomy.record";
	}

	@Override
	public boolean execute(Player player, String[] args) {
		if(args.length != 1) {
			player.sendMessage(ChatColor.RED + "Bitte überprüfe die Argumente.");
			return true;
		}
		
		EbeanServer server = plugin.getDatabase();
		List<Transaction> transListIn = 
				server.find(Transaction.class).where().ieq("recipient", player.getName()).orderBy().desc("date").findList();
		List<Transaction> transListOut = 
				server.find(Transaction.class).where().ieq("depositor", player.getName()).orderBy().desc("date").findList();
		if(args[0].equalsIgnoreCase("in")) {
			if(transListIn == null || transListIn.isEmpty()) {
				player.sendMessage(ChatColor.RED + "Du hast keine eingegangenen Transaktionen");
				return true;
			}
			showIncomingTransactions(player, transListIn);
			return true;
		} else if(args[0].equalsIgnoreCase("out")) {
			if(transListIn == null || transListIn.isEmpty()) {
				player.sendMessage(ChatColor.RED + "Du hast keine ausgegangenen Transaktionen");
				return true;
			}
			showOutgoingTransactions(player, transListOut);
			return true;
		} else {
			player.sendMessage(ChatColor.RED + "Bitte überprüfe die Argumente.");
			return true;
		}
		
	}
	
	private void showIncomingTransactions(Player player, List<Transaction> transList) {
		
		player.sendMessage(ChatColor.GOLD + "-----[ " + ChatColor.WHITE + "Kontoauszüge eingehend" + ChatColor.GOLD + " ]-----");
		
		int recEntries = transList.size() > defEntries ? defEntries : transList.size();
		for(int i = 0; i < recEntries; i++) {
			Transaction transaction = transList.get(i);
			String depositor = transaction.getDepositor();
			double amount = transaction.getPayment();
			Date date = transaction.getDate();
			player.sendMessage(ChatColor.GRAY + String.valueOf(i) + ". " + ChatColor.GOLD + "Sender: " + 
					ChatColor.GRAY + depositor + ChatColor.GOLD + " Betrag: " + ChatColor.GRAY + String.valueOf(amount) + " Taler" +
					ChatColor.GOLD + " Datum: " + ChatColor.GRAY + dateFormat.format(date) + '\n' + "   " + 
					ChatColor.GOLD + "Zeit: " + ChatColor.GRAY + timeFormat.format(date));
		}
	}
	
	private void showOutgoingTransactions(Player player, List<Transaction> transList) {
		
		player.sendMessage(ChatColor.GOLD + "-----[ " + ChatColor.WHITE + "Kontoauszüge ausgehend" + ChatColor.GOLD + " ]-----");
		
		int recEntries = transList.size() > defEntries ? defEntries : transList.size();
		for(int i = 0; i < recEntries; i++) {
			Transaction transaction = transList.get(i);
			String recipient = transaction.getRecipient();
			double amount = transaction.getPayment();
			Date date = transaction.getDate();
			player.sendMessage(ChatColor.GRAY + String.valueOf(i) + ". " + ChatColor.GOLD + "Empfänger: " + 
					ChatColor.GRAY + recipient + ChatColor.GOLD + " Betrag: " + ChatColor.GRAY + String.valueOf(amount) + " Taler" +
					ChatColor.GOLD + " Datum: " + ChatColor.GRAY + dateFormat.format(date) + '\n' + "   " + 
					ChatColor.GOLD + "Zeit: " + ChatColor.GRAY + timeFormat.format(date));
		}
	}

}
