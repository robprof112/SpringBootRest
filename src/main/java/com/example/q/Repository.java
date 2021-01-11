package com.example.q;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.javamoney.moneta.Money;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface Repository extends JpaRepository<Accounts,Long> {
	
	//@Query("Update Account a ,Account b set a.id=id1,b.id=id2 where a.")
	//List< Account> updateBalance(@Param("id")Long id,BigDecimal money,Currency currency);
	//@Override
	
		
		
	
	//@Query("select a from Account where a.id = ?1 and a.treasury=false")
	//Optional<Account> findById(Long id);
	//@Query("select a from Account where a.name = ?1")
	//Optional<Account> findByName(String name);
	//@Query("update Account set balance from Account a where a.name = ?1")
	//Optional<Account> setBalance(String name);
}
