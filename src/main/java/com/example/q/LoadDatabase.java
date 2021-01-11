package com.example.q;
import java.math.BigDecimal;
import java.util.Currency;

import org.javamoney.moneta.Money;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableAutoConfiguration
class LoadDatabase {

  private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

  @Bean
  CommandLineRunner initDatabase(AccountRepository repository) {

    return args -> {
    	
    	 BigDecimal b = new BigDecimal("1000");
    	 Money m = Money.of(b, "EUR");
    	 m.subtract(m);
      Account acc = new Account("Bilbo Baggins",m,Currency.getInstance("EUR"),false);
      acc.setTreasury(true);
      Account acc1 = new Account("Frodo Baggins",m,Currency.getInstance("USD"),false);
      acc1.setTreasury(true);
      log.info("Preloading " + repository.saveAndFlush(acc));
      log.info("Preloading " + repository.saveAndFlush(acc1));
      
    };
  }
  
}