package com.example.demo;
import java.util.Currency;
import javax.money.CurrencyUnit;
import javax.money.Monetary;

import org.javamoney.moneta.Money;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		Money oneEuro = Money.of(-1,"EUR");
		
		System.out.println(oneEuro);
		
		SpringApplication.run(DemoApplication.class, args);
	}

}
