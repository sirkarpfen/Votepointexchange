package de.guildcraft.guildConomy.persistence;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table (name = "guildconomy_activity")
public class Transaction {
	
	// ---------------------------------------------------------------------------------------------
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column (nullable = false)
	private String depositor;
	@Column
	private double payment;
	@Column
	private String recipient;
	@Column
	private Date date;
	
	// ---------------------------------------------------------------------------------------------
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	// ---------------------------------------------------------------------------------------------
	
	public String getDepositor() {
		return depositor;
	}
	
	public void setDepositor(String depositor) {
		this.depositor = depositor;
	}
	
	// ---------------------------------------------------------------------------------------------
	
	public double getPayment() {
		return payment;
	}
	
	public void setPayment(double payment) {
		this.payment = payment;
	}
	
	// ---------------------------------------------------------------------------------------------
	
	public String getRecipient() {
		return recipient;
	}
	
	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}
	
	// ---------------------------------------------------------------------------------------------
	
	public Date getDate() {
		return date;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
	
	
}
