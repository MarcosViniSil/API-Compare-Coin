package com.investor.api.services


import com.investor.api.dtos.LoginInvestorDTO
import com.investor.api.dtos.ReturnHistoricEmailsDTO
import com.investor.api.entities.Email
import com.investor.api.entities.HistoricEmails
import com.investor.api.entities.Investor
import com.investor.api.exceptions.InvestorNotExistsException
import com.investor.api.exceptions.LoginInvestorException
import com.investor.api.projections.HistoricEmailsProjection
import com.investor.api.repositories.EmailRepository
import com.investor.api.repositories.HistoricEmailsRepository
import com.investor.api.repositories.InvestorRepository
import org.springframework.mail.MailException
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.sql.Date
import java.text.SimpleDateFormat
import java.util.*

@Service
class EmailService(
    private val investorRepository: InvestorRepository,
    private val historicEmailRepository: HistoricEmailsRepository,
    private val sendEmail: JavaMailSender,
    private val emailRepository: EmailRepository,
    private val historicCoins: HistoricCoinsService
) : HistoricEmailsProjection {
    override fun sendEmail(dataInvestor: Investor): Boolean {
        val date=Date(Calendar.getInstance().timeInMillis)
        val formatBrasil = SimpleDateFormat("dd/MM/yyyy", Locale("pt", "BR"))
        val dateFormat= formatBrasil.format(date)
        var aboutEmail: List<String> = textEmail(dataInvestor)
        var subject: String =
            "Email send day : ${dateFormat} about coins : ${dataInvestor.coinMainName} and ${dataInvestor.coinSecondName} "
        var msg = SimpleMailMessage()
        msg.setTo(dataInvestor.email)
        msg.setSubject(subject)
        msg.setText(aboutEmail[0])

        try {

            sendEmail.send(msg)

            var historicEmails = dataInvestor.historicEmails

            if (historicEmails == null) {
                historicEmails = HistoricEmails()
                historicEmails.investor = dataInvestor
                dataInvestor.historicEmails = historicEmails
            }


            val newEmail = Email(
                code = (1..999_999_999_999_999).random(),
                message = aboutEmail[1],
                dateEmail = Date(Calendar.getInstance().timeInMillis),
                historice = historicEmails
            )

            val emails = historicEmails.emails ?: mutableListOf()
            emails.add(newEmail)
            historicEmails.emails = emails

            dataInvestor.historicEmails = historicEmails

            investorRepository.save(dataInvestor)
            return true
        } catch (ex: MailException) {
            System.err.println(ex.cause)
            return false
        }

    }

    @Scheduled(cron = "0 43 17 * * ?")
    override fun sendEmailInvestors() {
        historicCoins.updateCoins()
        var listInvestor: MutableList<Investor> = investorRepository.findAll()

        listInvestor.forEach { e ->

            if (this.sendEmail(e)) {
                println("GOOD!!")
            } else {
                println("FAIL!!")
            }

        }

    }


    override fun listEmailsInvestor(loginInvestor: LoginInvestorDTO): ReturnHistoricEmailsDTO? {
        if (loginInvestor.email != null && loginInvestor.password != null) {
            var idInvestor: Long? = investorRepository.findIdInvestor(loginInvestor.email, loginInvestor.password)
            if (idInvestor != null) {
                var investorLogin: Investor = investorRepository.findById(idInvestor!!).get()
                var listCoinsInvestor: HistoricEmails? = investorLogin.historicEmails
                if (listCoinsInvestor?.emails != null) {
                    var historicEmailsDTO: ReturnHistoricEmailsDTO =
                        ReturnHistoricEmailsDTO(emails = listCoinsInvestor?.emails)
                    return historicEmailsDTO
                }


            } else {
                throw InvestorNotExistsException("Investor nor exists")
            }


        } else {
            throw LoginInvestorException("Investor invalid")
        }

        return null
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


        val date=Date(Calendar.getInstance().timeInMillis)
        val formatBrasil = SimpleDateFormat("dd/MM/yyyy", Locale("pt", "BR"))
        val dateFormat= formatBrasil.format(date)
        var stringEmail: String
        if (coinValueHighestName.equals("equals")) {
            stringEmail =
                "Hello ${dataInvestor.name}. Today day :${dateFormat} your coins has the following price: \n" +
                        "Coin main :${dataInvestor.coinMainName} price : ${dataInvestor.coinMainPrice}. Coin second :${dataInvestor.coinSecondName} price : ${dataInvestor.coinSecondPrice}  \n " +
                        "And the percent of relation between : Coin main : ${dataInvestor.coinMainPrice} is 0 % more valued that Coin second : ${dataInvestor.coinSecondPrice}, therefore they have the same price today \n " +
                        "Email send by Spring boot \n" +
                        "Date send: ${dateFormat} "
            percentCoinUser =
                "${dataInvestor.coinMainName}: R$ ${dataInvestor.coinMainPrice} is 0 % more valued that:R$ ${dataInvestor.coinSecondName} ${dataInvestor.coinSecondPrice}"

        } else {
            stringEmail =
                "Hello ${dataInvestor.name}. Today day :${dateFormat} your coins has the following price: \n" +
                        "Coin main :${dataInvestor.coinMainName} price : ${dataInvestor.coinMainPrice}. Coin second :${dataInvestor.coinSecondName} price : ${dataInvestor.coinSecondPrice}  \n" +
                        "And the percent of relation between : Coin : ${coinValueHighestName} is $percentCoins % more valued that Coin : ${coinValueLowestName} \n" +
                        "Email send by Spring boot \n" +
                        "Date send: ${dateFormat} "
            percentCoinUser =
                " ${coinValueHighestName} is $percentCoins % more valued that Coin: ${coinValueLowestName}"
        }

        return mutableListOf(stringEmail, percentCoinUser)
    }


}