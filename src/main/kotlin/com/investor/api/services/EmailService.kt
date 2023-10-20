package com.investor.api.services

import com.investor.api.dtos.EmailInvestorsDTO
import com.investor.api.entities.Investor
import com.investor.api.repositories.HstoricEmailsRepository
import com.investor.api.repositories.InvestorRepository
import org.springframework.mail.MailException
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.sql.Date
import java.util.*

@Service
class EmailService (private val investorRepository: InvestorRepository,
                    private val emailRepository: HstoricEmailsRepository,
                    private val sendEmail: JavaMailSender
) {
     fun sendEmail(dataInvestor:EmailInvestorsDTO): Boolean {
      var aboutEmail:String=textEmail(dataInvestor)
         var subject:String="Email send day : ${Date(Calendar.getInstance().timeInMillis)} about coins: ${dataInvestor.coinMainName} and ${dataInvestor.coinSecondName} "
         var msg = SimpleMailMessage()
         msg.setTo(dataInvestor.email)
         msg.setSubject(subject)
         msg.setText(aboutEmail)

        try {
            sendEmail.send(msg)
            return true
        } catch (ex: MailException) {
            System.err.println(ex.cause)
            return false
        }

    }
    @Scheduled(cron = "0 */2 * * * ?")
    fun sendEmailInvestors(){
        println("email")
        var listInvestor:MutableList<Investor> = investorRepository.findAll()

        listInvestor.forEach { e ->
                val investorEmail: EmailInvestorsDTO = EmailInvestorsDTO(
                    name = e.name,
                    coinMainName = e.coinMainName,
                    coinSecondName = e.coinSecondName,
                    priceCoinMain = e.coinMainPrice,
                    priceCoinSecond = e.coinSecondPrice,
                    email = e.email
                )
                 if(this.sendEmail(investorEmail)){
                     println("GOOD!!")
                 }else{
                     println("FAIL!!")
                 }

        }

    }

   private fun findPercentInvestor(coinPriceMain: Double?, coinPriceSecond: Double?): Double {
        var percent: Double
        if (coinPriceMain!! > coinPriceSecond!!) {
            percent = (coinPriceSecond * 100) / coinPriceMain
        } else if (coinPriceMain!! < coinPriceSecond!!) {
            percent = (coinPriceMain * 100) / coinPriceSecond
        } else {
            return 0.0
        }

        return 100 - percent
    }

    private fun findHighestCoin(dataInvestor:EmailInvestorsDTO):String{
        if(dataInvestor.priceCoinMain!!>dataInvestor.priceCoinSecond!!){
            return dataInvestor.coinMainName
        }else if(dataInvestor.priceCoinMain!!<dataInvestor.priceCoinSecond!!){
            return dataInvestor.coinSecondName
        }else{
            return "equals"
        }
    }

    private fun textEmail(dataInvestor:EmailInvestorsDTO):String{
        var percentCoins:Double=this.findPercentInvestor(dataInvestor.priceCoinMain,dataInvestor.priceCoinSecond)
        var coinValueHighestName:String=this.findHighestCoin(dataInvestor)
        var coinValueLowestName:String
        if(coinValueHighestName.equals(dataInvestor.coinMainName)){
            coinValueLowestName=dataInvestor.coinSecondName
        }else if(coinValueHighestName.equals(dataInvestor.coinSecondName)){
            coinValueLowestName=dataInvestor.coinMainName
        }else{
            coinValueHighestName=dataInvestor.coinMainName
            coinValueLowestName=dataInvestor.coinSecondName
        }
        var stringEmail:String
        if(coinValueHighestName.equals("equals")){
            stringEmail="Hello ${dataInvestor.name}.Today day:${Date(Calendar.getInstance().timeInMillis)} your coins has the following price: \n" +
                    "Coin main: ${dataInvestor.priceCoinMain} Coin second: ${dataInvestor.priceCoinSecond} \n " +
                    "And the percent of relation between : Coin main: ${dataInvestor.priceCoinMain} is 0 % more valued that Coin second: ${dataInvestor.priceCoinSecond},therefore they have the same price today" +
                    "Email send by Spring boot" +
                    "Date send: ${Date(Calendar.getInstance().timeInMillis)} "

        }else{
            stringEmail="Hello ${dataInvestor.name}.Today day:${Date(Calendar.getInstance().timeInMillis)} your coins has the following price: \n" +
                    "Coin main: ${dataInvestor.priceCoinMain} Coin second: ${dataInvestor.priceCoinSecond} \n " +
                    "And the percent of relation between : Coin : ${coinValueHighestName} is $percentCoins % more valued that Coin: ${coinValueLowestName}" +
                    "Email send by Spring boot" +
                    "Date send: ${Date(Calendar.getInstance().timeInMillis)} "
        }

        return stringEmail
    }



}