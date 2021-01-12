package com.example.demo;
import java.math.BigDecimal;
import java.util.Currency;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import org.javamoney.moneta.Money;
@Entity
@Table(name="Accounts")
public class Accounts {
	Accounts(){super();}
	@Id @GeneratedValue(strategy=GenerationType.AUTO)private Long id;
	 @Column
	 private  String name ;
	 @Column
	 private  Currency currency ;
	 @Column(length=10000000)
	private Money balance;
	 @Column
	private  Boolean treasury ;

    	Accounts(String name,Money balance, Currency currency){
    		this.treasury=false;
			this.name=name;
			this.currency=currency;
			this.balance = balance.abs();
		}
	
	 public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public Currency getCurrency() {
		return currency;
	}


	public void setCurrency(Currency currency) {
		this.currency = currency;
	}


	public Money getBalance() {
		return balance;
	}


	public void setBalance(Money balance) {
		this.balance = balance;
	}


	public Boolean getTreasury() {
		return treasury;
	}


	public void setTreasury(Boolean treasury) {
		this.treasury = treasury;
	}
	


	 

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Accounts other = (Accounts) obj;
		if (balance == null) {
			if (other.balance != null)
				return false;
		} else if (!balance.equals(other.balance))
			return false;
		if (currency == null) {
			if (other.currency != null)
				return false;
		} else if (!currency.equals(other.currency))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (treasury == null) {
			if (other.treasury != null)
				return false;
		} else if (!treasury.equals(other.treasury))
			return false;
		return true;
	}

	  @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((balance == null) ? 0 : balance.hashCode());
		result = prime * result + ((currency == null) ? 0 : currency.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((treasury == null) ? 0 : treasury.hashCode());
		return result;
	}

	  @Override
	public String toString() {
		return "Accounts id=" + id + ", name=" + name + ", currency=" + currency + ", balance=" + balance
				+ ", treasury=" + treasury ;
	}
}


