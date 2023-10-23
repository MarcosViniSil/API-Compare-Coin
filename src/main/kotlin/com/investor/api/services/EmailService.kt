package com.investor.api.services


import com.investor.api.entities.HistoricEmails
import com.investor.api.entities.Investor
import com.investor.api.repositories.HistoricEmailsRepository
import com.investor.api.repositories.InvestorRepository
import org.springframework.mail.MailException
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service
import java.sql.Date
import java.util.*

@Service
class EmailService(
    private val investorRepository: InvestorRepository,
    private val emailRepository: HistoricEmailsRepository,
    private val sendEmail: JavaMailSender
) {
    fun sendEmail(dataInvestor: Investor): Boolean {
        var aboutEmail: List<String> = textEmail(dataInvestor)
        var subject: String =
            "Email send day : ${Date(Calendar.getInstance().timeInMillis)} about coins: ${dataInvestor.coinMainName} and ${dataInvestor.coinSecondName} "
        var msg = SimpleMailMessage()
        msg.setTo(dataInvestor.email)
        msg.setSubject(subject)
        msg.setText(aboutEmail[0])

        try {
            //sendEmail.send(msg)
            var test:List<HistoricEmails> =emailRepository.findAll()
            var historicById: HistoricEmails
            try {
                historicById = emailRepository.findById(dataInvestor.id!!).get()
            }catch(e:NoSuchElementException){
                historicById=HistoricEmails(investor = dataInvestor)
            }
            var listCodeEmail:MutableList<String>? = emailRepository.listAllCodes(dataInvestor.id)
            var listMessagesEmail:MutableList<String>? = emailRepository.listAllMessages(dataInvestor.id)
            var listDatesEmail:MutableList<Date>? = emailRepository.listAllDates(dataInvestor.id)

            listCodeEmail?.add((1..999_999_999_999_999).random().toString())
            listMessagesEmail?.add(aboutEmail[1])
            listDatesEmail?.add(Date(Calendar.getInstance().timeInMillis))

            historicById.id=dataInvestor.id
            historicById.investor=dataInvestor
            historicById.codeEmail=listCodeEmail
            historicById.message=listMessagesEmail
            historicById.dateEmail=listDatesEmail

            dataInvestor.historicEmails=historicById

            investorRepository.save(dataInvestor)
            emailRepository.save(historicById)





            return true
        } catch (ex: MailException) {
            System.err.println(ex.cause)
            return false
        }

    }


    fun sendEmailInvestors() {

        var listInvestor: MutableList<Investor> = investorRepository.findAll()

        listInvestor.forEach { e ->

            if (this.sendEmail(e)) {
                println("GOOD!!")
            } else {
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

    private fun findHighestCoin(dataInvestor: Investor): String {
        if (dataInvestor.coinMainPrice!! > dataInvestor.coinSecondPrice!!) {
            return dataInvestor.coinMainName
        } else if (dataInvestor.coinMainPrice!! < dataInvestor.coinSecondPrice!!) {
            return dataInvestor.coinSecondName
        } else {
            return "equals"
        }
    }

    private fun textEmail(dataInvestor: Investor): List<String> {
        var percentCoins: Double = this.findPercentInvestor(dataInvestor.coinMainPrice, dataInvestor.coinSecondPrice)
        var coinValueHighestName: String = this.findHighestCoin(dataInvestor)
        var coinValueLowestName: String
        var percentCoinUser: String
        if (coinValueHighestName.equals(dataInvestor.coinMainName)) {
            coinValueLowestName = dataInvestor.coinSecondName
        } else if (coinValueHighestName.equals(dataInvestor.coinSecondName)) {
            coinValueLowestName = dataInvestor.coinMainName
        } else {
            coinValueHighestName = dataInvestor.coinMainName
            coinValueLowestName = dataInvestor.coinSecondName
        }
        var stringEmail: String
        if (coinValueHighestName.equals("equals")) {
            stringEmail =
                "Hello ${dataInvestor.name}.Today day:${Date(Calendar.getInstance().timeInMillis)} your coins has the following price: \n" +
                        "Coin main:${dataInvestor.coinMainName} price: ${dataInvestor.coinMainPrice}. Coin second:${dataInvestor.coinSecondName} price: ${dataInvestor.coinSecondPrice}  \n " +
                        "And the percent of relation between : Coin main: ${dataInvestor.coinMainPrice} is 0 % more valued that Coin second: ${dataInvestor.coinSecondPrice}, therefore they have the same price today " +
                        "Email send by Spring boot " +
                        "Date send: ${Date(Calendar.getInstance().timeInMillis)} "
            percentCoinUser =
                "${dataInvestor.coinMainName}: R$ ${dataInvestor.coinMainPrice} is 0 % more valued that:R$ ${dataInvestor.coinSecondName} ${dataInvestor.coinSecondPrice}"

        } else {
            stringEmail =
                "Hello ${dataInvestor.name}.Today day:${Date(Calendar.getInstance().timeInMillis)} your coins has the following price: \n" +
                        "Coin main:${dataInvestor.coinMainName} price: ${dataInvestor.coinMainPrice}. Coin second:${dataInvestor.coinSecondName} price: ${dataInvestor.coinSecondPrice}  \n " +
                        "And the percent of relation between : Coin : ${coinValueHighestName} is $percentCoins % more valued that Coin: ${coinValueLowestName} " +
                        "Email send by Spring boot " +
                        "Date send: ${Date(Calendar.getInstance().timeInMillis)} "
            percentCoinUser =
                " ${coinValueHighestName} is $percentCoins % more valued that Coin: ${coinValueLowestName}"
        }

        return mutableListOf(stringEmail, percentCoinUser)
    }



}