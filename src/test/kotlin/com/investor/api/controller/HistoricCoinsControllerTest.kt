package com.investor.api.controller

import com.investor.api.repositories.InvestorRepository
import com.fasterxml.jackson.databind.ObjectMapper
import com.investor.api.dtos.InvestorDTO
import com.investor.api.dtos.LoginInvestorDTO
import com.investor.api.entities.Investor
import com.investor.api.repositories.HistoricCoinsRepository
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@ContextConfiguration
class HistoricCoinsControllerTest {

    @Autowired
    private lateinit var historicCoinsRepository: HistoricCoinsRepository

    @Autowired
    private lateinit var investorRepository: InvestorRepository

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    companion object {
        const val URL: String = "/coins/historic"

    }

    @BeforeEach
    fun setup() {
        historicCoinsRepository.deleteAll()
        investorRepository.deleteAll()
    }

    @AfterEach
    fun tearDown() {
        historicCoinsRepository.deleteAll()
        investorRepository.deleteAll()
    }

    @Test
    fun `should return historic coins and return 200 status`() {
        val investor = Investor(
            id=1,
            name = "Test Name",
            email = "testemail@gmail.com",
            password = "testPassword",
            coinMainName = "USD",
            coinSecondName = "EUR",
            coinMainPrice=5.09,
            coinSecondPrice=5.23
        )
        investorRepository.save(investor)

        val loginInvestorDTO:LoginInvestorDTO= LoginInvestorDTO(email="testemail@gmail.com", password = "testPassword")

        val valueAsString: String = objectMapper.writeValueAsString(loginInvestorDTO)

        mockMvc.perform(
            MockMvcRequestBuilders.post(HistoricCoinsControllerTest.URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(valueAsString)
        )

            .andExpect(MockMvcResultMatchers.status().isOk)
            .andDo(MockMvcResultHandlers.print())

    }

    @Test
    fun `should return return 406 status because not exists investor`() {

        val loginInvestorDTO:LoginInvestorDTO= LoginInvestorDTO(email="testemail@gmail.com", password = "testPassword")

        val valueAsString: String = objectMapper.writeValueAsString(loginInvestorDTO)

        mockMvc.perform(
            MockMvcRequestBuilders.post(HistoricCoinsControllerTest.URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(valueAsString)
        )

            .andExpect(MockMvcResultMatchers.status().isNotAcceptable)
            .andDo(MockMvcResultHandlers.print())

    }
}