package de.guildcraft.guildConomy;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.persistence.PersistenceException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import de.guildcraft.guildConomy.commands.GCCommand;
import de.guildcraft.guildConomy.listener.GCLoginListener;
import de.guildcraft.guildConomy.listener.VotifierEventHandler;
import de.guildcraft.guildConomy.persistence.Account;
import de.guildcraft.guildConomy.persistence.Transaction;

public class GCPlugin extends JavaPlugin{

	public static Logger log;
	private FileConfiguration config;
	private String currency = "Taler";
	
	@Override
	public void onLoad() {
		setupPersistence();
	}
	
	@Override
	public void onEnable() {
		super.onEnable();
		log = this.getLogger();
		config = this.getConfig();
		if(config.get("general.enable") == null) {
			this.initConfig();
		}
		if(config.getBoolean("general.enable")) {
			this.registerEvents();
			this.getCommand("money").setExecutor(new GCCommand(this));
		} else {
			log.info("Disabled. Check config.yml.");
			this.getServer().getPluginManager().disablePlugin(this);
		}
	}
	
	@Override
	public void onDisable() {
		super.onDisable();
		log.info("Disabled.");
	}
	
	private void initConfig() {
		config.addDefault("general.enable", true);
		config.addDefault("general.startmoney", 40);
		config.addDefault("general.max_record_entries", 5);
		config.addDefault("votepoints.startpoints", 0);
		config.addDefault("votepoints.points_for_each_vote", 5);
		config.options().copyDefaults(true);
		this.saveConfig();
	}

	private void registerEvents() {
		PluginManager manager = this.getServer().getPluginManager();
		manager.registerEvents(new VotifierEventHandler(this), this);
		manager.registerEvents(new GCLoginListener(this), this);
	}
	
	private void setupPersistence() {
		try {
			getDatabase().find(Account.class).findRowCount();
			getLogger().info("database found no installing needed");
		} catch (PersistenceException e) {
			getLogger().info("Installing DB");
			installDDL();
		}
	}
	
	@Override
	public List<Class<?>> getDatabaseClasses() {
		List<Class<?>> classes = new ArrayList<Class<?>>();
		classes.add(Account.class);
		classes.add(Transaction.class);
		return classes;
	}
	
	public String getCurrency() {
		return currency;
	}
	
}
