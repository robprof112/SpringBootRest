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
  CommandLineRunner initDatabase(Repository repository) {

    return args -> {
    	
    	 BigDecimal b = new BigDecimal("1000");
    	 Money m = Money.of(b, "EUR");
    	 m.subtract(m);
      Accounts acc = new Accounts("Bilbo Baggins",m,Currency.getInstance("EUR"),false);
      acc.setTreasury(true);
      Accounts acc1 = new Accounts("Frodo Baggins",m,Currency.getInstance("USD"),false);
      acc1.setTreasury(true);
      log.info("Preloading " + repository.saveAndFlush(acc));
      log.info("Preloading " + repository.saveAndFlush(acc1));
      
    };
  }
  
}