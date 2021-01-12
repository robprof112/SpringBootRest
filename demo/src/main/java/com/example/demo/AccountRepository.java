package com.example.demo;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.javamoney.moneta.Money;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
@Repository

public interface AccountRepository extends JpaRepository<Accounts,Long> {
	@Modifying
	@Query("Update Accounts  set balance= :balance where id= :id")
	@Transactional
	 void updateBalance(@Param("id")Long id,@Param("balance")Money balance);
	//@Override
	
		
		
	
	//@Query("select a from Accounts where a.id = ?1 and a.treasury=false")
	//Optional<Accounts> findById(Long id);
	//@Query("select a from Accounts where a.name = ?1")
	//Optional<Accounts> findByName(String name);
	//@Query("update Accounts set balance from Accounts a where a.name = ?1")
	//Optional<Accounts> setBalance(String name);
}
