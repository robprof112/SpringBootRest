package com.example.q;
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
@Table(name="Account")
public class Account {
	
	@Id @GeneratedValue(strategy=GenerationType.AUTO) Long id;
	 @Column
	 private  String name ;
	 @Column
	 private  Currency currency ;
	 @Column(length=10000000)
	private Money balance;
	 @Column
	private  Boolean treasury ;
	 Account(){
		 this.name="";
		 this.balance= Money.of(0, "USD");
		 this.currency=Currency.getInstance("USD");
		 this.treasury=false;
	 }
    	Account(String name,Money balance, Currency currency,boolean treasury){
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
		Account other = (Account) obj;
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
		return "Account id=" + id + ", name=" + name + ", currency=" + currency + ", balance=" + balance
				+ ", treasury=" + treasury ;
	}
}


