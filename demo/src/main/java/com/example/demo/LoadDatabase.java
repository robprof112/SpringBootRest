package com.example.demo;
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

@Configuration
class LoadDatabase {

  private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

  @Bean
  CommandLineRunner initDatabase(AccountRepository repository) {

    return args -> {
    	
    	 BigDecimal b = new BigDecimal("1000");
    	 Money m = Money.of(b, "EUR");
    	 Money m1 = Money.of(b, "USD");
      Account acc = new Account("Bilbo Baggins",m,Currency.getInstance("EUR"));
      acc.setTreasury(true);
      Account acc1 = new Account("Frodo Baggins",m1,Currency.getInstance("USD"));
      acc1.setTreasury(true);
      Account acc2 = new Account("Frodo Baggins",m1,Currency.getInstance("USD"));
     
      log.info("Preloading " + repository.saveAndFlush(acc));
      log.info("Preloading " + repository.saveAndFlush(acc1));
      log.info("Preloading " + repository.saveAndFlush(acc2));
      
    };
  }
  
}