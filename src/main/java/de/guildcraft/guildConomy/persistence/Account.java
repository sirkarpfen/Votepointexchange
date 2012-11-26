package de.guildcraft.guildConomy.persistence;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table (name = "guildconomy")
public class Account {
	
	// ---------------------------------------------------------------------------------------------
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column (nullable = false, unique = true)
	private String username;
	@Column
	private double amount;
	
	// ---------------------------------------------------------------------------------------------
	
	public long getId() {
		return id;
	}
	
	public void  setId(long id) {
		this.id = id;
	}
	
	// ---------------------------------------------------------------------------------------------
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	// ---------------------------------------------------------------------------------------------
	
	public double getAmount() {
		return amount;
	}
	
	public void setAmount(double amount) {
		this.amount = amount;
	}
	
}
