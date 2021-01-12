package com.example.demo;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Currency;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import javax.money.Monetary;
import javax.money.MonetaryAmount;
import javax.money.NumberValue;
import javax.money.convert.CurrencyConversion;
import javax.money.convert.MonetaryConversions;

import org.javamoney.moneta.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
class AccountController {
	@Autowired
    private AccountRepository repository;
	AccountController(AccountRepository repository) {
	    this.repository = repository;
	  }
	@GetMapping("Accounts{id}")
	Optional<Accounts> findByID(@RequestParam(name="id",required=false,defaultValue="1")String id) 
	{
		return repository.findById(Long.parseLong(id));
	}
	@PostMapping("Accounts{id,name,balance,currency}")
	Accounts createOrModify(@RequestParam(name="id",required=false,defaultValue="1")String id,
						   @RequestParam(name="name",required=false,defaultValue="q")String name,
						   @RequestParam(name="balance",required=false,defaultValue="1000")String balance,
						   @RequestParam(name="currency",required=false,defaultValue="USD")String currency
						   
			){
		
		if(repository.findById(Long.parseLong(id)).isEmpty()==true ) 
		{ return repository.save(new Accounts(name,Money.of(new BigDecimal(balance), currency),Currency.getInstance(currency)));}
		else {
			return repository.findById(Long.parseLong(id)).map(acc->{
			acc.setName(name);
			acc.setBalance(Money.of(new BigDecimal(balance), currency));
			acc.setCurrency(Currency.getInstance(currency));
			 return repository.save(acc);
		}).get();}
	
		
		
	}
	@PutMapping(
			  value = "Accounts{id,id2,amount,currency}", 
			  produces = "application/json"
			  )
	
	List<Accounts> motherlode(//@RequestBody(required=false) Accounts accountz,
							@RequestParam(name="id",required=false,defaultValue="1")String iid,
							@RequestParam(name="id2",required=false,defaultValue="2")String iid2,
							
							@RequestParam(name="amount",required=false,defaultValue="2")String aamount,
							@RequestParam(name="currency",required=false,defaultValue="USD")String currency)
	
	{
		
		System.out.println(iid+" "+aamount+" "+iid2+" "+currency);
		BigDecimal amount = new BigDecimal(aamount);Long id = Long.parseLong(iid);
		Long id2 = Long.parseLong(iid2);
		List<Accounts> accounts=new ArrayList<>();
		
		
			
		//accounts.add(repository.save(new Accounts(name,Money.of(amount,currency),Currency.getInstance(currency))));}
		//if(account!=null&&repository.findById(account.id)!= null) {accounts.add(account);}
		//if(id!=null &&id2!=null&&name==null&&amount!=null&&currency!=null&&id!=0){
			
			Accounts acc = updateBalance(repository.findById(id).get(),amount,Currency.getInstance(currency));
			if(acc!=repository.findById(id).get()) {
			repository.updateBalance(acc.getId(),acc.getBalance());
			Accounts acc2 = updateBalance(repository.findById(id2).get(),amount,Currency.getInstance(currency));
			repository.updateBalance(acc.getId(),acc.getBalance());
			repository.save(acc);
			repository.save(acc2);
			accounts.add(acc);
			accounts.add(acc2);}
		//}
		return accounts;}
	
							
						
			
	  // Aggregate root
	  // tag::get-aggregate-root[]
	   
	  @GetMapping("/Accounts")
	  List<Accounts> all() {
	    return repository.findAll();
	  }
	  /*	
	  @GetMapping("/Accounts/{id}")
	  Optional<Accounts> id(@PathVariable(value="id") long id) {
	    return repository.findById(id);
	  }
	  
	  @PostMapping("/Accounts{account}")
	  Accounts CreateAccount(@RequestBody Accounts account) {
		  if(account.getTreasury()==false) { 
	  	        return repository.save(account);
	  	        }else{return null;}
	  }
	  

		 
			 
				    	  
				     
		 
		  
	  @PutMapping("/Accounts/{senderID,recipientID,payment,currency}")
	  List<Accounts> replaceEmployee(
			  				  @RequestParam(value="senderID") long senderID,
			  				  @RequestParam(value="recipientID") long recipientID,
			  				  @RequestParam(value="payment") BigDecimal payment,
			  				  @RequestParam(value="currency") String currency) {
	
			 
	    Accounts sender = repository.findById(senderID)
	      .map(account -> {
	         return updateBalance(account,payment,Currency.getInstance(currency),true);
	      })
	      .orElseGet(() -> {
	    	 /*
	        if(NewAccount.getTreasury()==false) { 
	        return repository.save(NewAccount);
	        }else{return null;}
	        
	    	  return null;
	      });
	    
	    Accounts recipient = repository.findById(recipientID)
	  	      .map(account -> {
	  	         return updateBalance(account,payment,Currency.getInstance(currency),false);
	  	      })
	  	      .orElseGet(() -> {
	  	    	 /*
	  	        if(NewAccount.getTreasury()==false) { 
	  	        return repository.save(NewAccount);
	  	        }else{return null;}
	  	        
	  	    	  return null;
	  	      });
	    Iterable<Accounts> involved_in_transaction
		  = Arrays.asList(sender, recipient);
	    return repository.saveAll(involved_in_transaction);
	  }*/
	Accounts updateBalance(Accounts accounts,BigDecimal payment,Currency currency) {
		MonetaryAmount amount = Monetary.getDefaultAmountFactory().setCurrency(currency.getCurrencyCode())
			      .setNumber(payment).create();
  	 
  	  MonetaryAmount convertedAmount=Money.of(payment, currency.getCurrencyCode());
  	  if(currency.getCurrencyCode() != accounts.getCurrency().getCurrencyCode()) {
  		  		System.out.println(accounts.getCurrency().getCurrencyCode());
  			    CurrencyConversion conversion = MonetaryConversions.getConversion(accounts.getCurrency().getCurrencyCode());
  			    
  			    try {
						TimeUnit.SECONDS.sleep(5);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
  			    convertedAmount = amount.with(conversion);
  			    
  	  }
  	
  	  
  		System.out.print("qwqwqw");
  		System.out.print(convertedAmount);
  		System.out.print("qwqwqw11111111111");
  		Money w = accounts.getBalance().subtract(convertedAmount);
  		System.out.print(w.toString());
  		System.out.print("wwwwwwwwwwwwwwwwwww3333");
  		 
  		if(accounts.getTreasury()==false&&accounts.getBalance().add(convertedAmount).isPositiveOrZero()==false)
		  {return accounts;}else {
  		  accounts.setBalance(accounts.getBalance().add(convertedAmount));
  	  }
  	  
  	  return accounts;
	}      
}
		  
		  
		  
		  
		  
		  
		  
		  
		  
		  
		  
		  
		  
		  
		  
		  
		  
		  
		  
		  
		  
		  
		  
		  
		  
		  
		  
		  /*
		
		 Iterable<Long> ids
		  = Arrays.asList(senderID, recipientID);
		 StreamSupport.stream(ids.spliterator(), false);
		 AtomicInteger i =new AtomicInteger(0);
		 Stream<Accounts> receipt = StreamSupport.stream(repository.findAllById(ids).spliterator(),false)
				 		.map( account -> {
				 			BigDecimal exchange_rate_factor= null;
					    	  MonetaryAmount amountToConvert = Monetary.getDefaultAmountFactory().setCurrency(currency)
				    			      .setNumber(payment).create();
					    	  
					    	  if(currency != account.getCurrency().getCurrencyCode()) {
					    		  		System.out.println(account.getCurrency().getCurrencyCode());
					    			    CurrencyConversion conversion = MonetaryConversions.getConversion(account.getCurrency().getCurrencyCode());
					    			    
					    			    try {
											TimeUnit.SECONDS.sleep(5);
										} catch (InterruptedException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
										
					    			    exchange_rate_factor = new BigDecimal(conversion.getExchangeRate(amountToConvert).getFactor().doubleValue());
					    	  }
					    	  Money amount = Money.of(payment.multiply(exchange_rate_factor),currency);
					    	  if(i==new AtomicInteger(0)) {
					    	  account.setBalance(account.getBalance().subtract(amount));}
					    	  else if(i==new AtomicInteger(1)){
					    		  account.setBalance(account.getBalance().add(amount));
					    	  }
					    	  i.getAndIncrement();
					    	  return repository.save(account);
			 
			 
				 
			 
		 });
		 	return receipt.collect(Collectors.toList());
		 }}
		/*
		 System.out.println("qqqqqqqqqqqqq");
		 Optional<Accounts> acc = repository.findById(senderID)
			      .map(account1 -> {
			    	  System.out.println("qqq");
			    	  BigDecimal exchange_rate_factor= null;
			    	  MonetaryAmount amountToConvert = Monetary.getDefaultAmountFactory().setCurrency(currency)
		    			      .setNumber(payment).create();
			    	  
			    	  if(currency != account1.getCurrency().getCurrencyCode()) {
			    		  		System.out.println(account1.getCurrency().getCurrencyCode());
			    			    CurrencyConversion conversion = MonetaryConversions.getConversion(account1.getCurrency().getCurrencyCode());
			    			    
			    			    try {
									TimeUnit.SECONDS.sleep(5);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
			    			    exchange_rate_factor = new BigDecimal(conversion.getExchangeRate(amountToConvert).getFactor().doubleValue());
			    	  }
			    	  Money amount = Money.of(payment.multiply(exchange_rate_factor),currency);
			    	  account1.setBalance(account1.getBalance().subtract(amount));
			    	  System.out.println("OOOOOOKOOOOOOOOOOOOOOOO");
			    	  return repository.saveAndFlush(account1);
			      					});
	    
	   
	    Optional<Accounts> acc1 = repository.findById(recipientID)
	  	   .map(account2 -> {
	  		 MonetaryAmount amountToConvert = Monetary.getDefaultAmountFactory().setCurrency(currency)
   			      .setNumber(payment).create();
	  		BigDecimal exchange_rate_factor= null;
	    	  if(currency != account2.getCurrency().getCurrencyCode()) {
	    		  		System.out.println(account2.getCurrency().getCurrencyCode());
	    			    CurrencyConversion conversion = MonetaryConversions.getConversion(account2.getCurrency().getCurrencyCode());
	    			    try {
							TimeUnit.SECONDS.sleep(5);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
	    			    exchange_rate_factor = new BigDecimal(conversion.getExchangeRate(amountToConvert).getFactor().doubleValue());
	    			    Money amount = Money.of(payment.multiply(exchange_rate_factor),currency);
	    			    account2.setBalance(account2.getBalance().add(amount));
	    			   
	    			 
	    			   
	    	  }
	    	  account2.setBalance(account2.getBalance().subtract(Money.of(payment.multiply(exchange_rate_factor), currency)));
	  	        return repository.saveAndFlush(account2);
	  	   					});
	    List<Optional<Accounts>> listOfOptionals = Arrays.asList(Optional.empty(),Optional.of(acc),Optional.of(acc1));
	    return listOfOptionals;
	     
	    
	  }

	  }
	  /*
	  @PutMapping("/employees/{id_sender,amount,currency,id_reciever}")
	  Accounts transfer( @PathVariable Long id_sender,@PathVariable String amount,) {

	    return repository.findById(id)
	      .map(employee -> {
	        employee.setName(newEmployee.getName());
	        employee.setRole(newEmployee.getRole());
	        return repository.save(employee);
	      })
	      .orElseGet(() -> {
	        newEmployee.setId(id);
	        return repository.save(newEmployee);
	      });
	  }
	  // end::get-aggregate-root[]
	  
	  /**
	  @PostMapping(value="/Accounts",params= {"name","balance","currency"})
	  public Accounts create(
			  @RequestParam(value="name",required=false) String name,
			  @RequestParam(value="balance",required=false) String balance,
			  @RequestParam(value="currency",required=false) String currency,
			  @RequestParam(value="recipient",required=false) String recipient,
			  @RequestParam(value="transfer",required=false) String transfer,
			  @RequestParam(value="transfer_currency",required=false) String transfer_currency) {
			  	Accounts account = null;
			  	if(!name.equals("")&&!balance.equals("")&&!currency.equals(""))
			  			 {
			  				 account = new Accounts(name,balance,currency);
			  			 }
	  
	    return repository.save(account);
	  }

	  // Single item

	  @GetMapping(value="/Accounts",params= "name")
	  public Optional<Accounts> one(@PathVariable Long id,@RequestParam(value="name",required=false)String name) {
		  return repository.findByName(name);
	  }

	  @PutMapping("/employees/{id}")
	  void replaceEmployee(
			  @RequestParam(value="name",required=false)String name,
			  @RequestParam(value="recipient",required=false)String recipient,
			  @RequestParam(value="transfer_quantity",required=false)String transfer_quantity,
			  @RequestParam(value="currency",required=false)String currency,@PathVariable Long id) {
		  		
		  
	      
	  }

	  @DeleteMapping("/employees/{id}")
	  void deleteEmployee(@PathVariable Long id) {
	    repository.deleteById(id);
	  }*/
	
