# API Compare Coins
## An API with the objective to compare two coins. Every day, one email will be sent to the user showing the relation between the coins. Also, it's possible to view the historical data of coins and historical emails on the API.
### Technologies used:
- Spring Framework
- Kotlin
- H2 DataBase
- Postgress
- Railway
## Public API used: awesomeapi
`https://docs.awesomeapi.com.br/api-de-moedas`
### Class Diagram

```mermaid
classDiagram
  class Investor {
    -String name
    -String email
    -String password
    -String coinMain
    -String coinSecond
    -Double coinMainPrice
    -Double coinSecondPrice
    -HistoricalCoins historicalCoins
    -HistoricalEmails historicalEmails
  }
  class HistoricalEmails {
    -Investor investor
    -Email[] emails
  }
  class HistoricalCoins {
    -Investor investor
    -Coin[] coins
  }
  class Coin {
    -String name
    -Date dateView
    -Double value
    -HistoricalCoins historicCoins
  }
  class Email {
    -Long code
    -String mesage 
    -Date dateEmail
    -HistoricalEmails historicalEmails
  }
  Investor "1" *-- "1" HistoricalEmails
  Investor "1" *-- "1" HistoricalCoins
  HistoricalEmails "1" *-- "N" Email
  HistoricalCoins "1" *-- "N" Coin
```
## Documentation API using Swagger

<p align="center">

  <img src="https://github.com/MarcosViniSil/API-Compare-Coin/blob/main/imagesGit/Capturar.PNG" alt="swagger documentation1">
</p>

## Link deploy API: **[Deploy](https://api-compare-coin-prod.up.railway.app/swagger-ui/index.html)**

