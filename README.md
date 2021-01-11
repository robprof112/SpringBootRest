# SpringBootRest

Still in Progress...

transfer money:
curl -v  -X PUT  localhost:8080/Account -d {id=1,name=q,balance=100,currency=USD}  |fx .

add Treasury account:
>open src/main/java/com/example/demo/LoadDatabase.java
>in  
@Bean
  CommandLineRunner initDatabase(AccountRepository repository) {

    return args -> {ADD HERE}
String name= "name";
String currencyCode = "USD";
BigDecimal balance = 1000.12;
repository.saveAndFlush(new Account(name,Money.of(balance,CurrencyCode),Currency.getInstanceOf(currencyCode)));

add Account:
>

modify Account:
>

    

