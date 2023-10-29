package com.investor.api.controller
import com.investor.api.repositories.InvestorRepository
import com.fasterxml.jackson.databind.ObjectMapper
import com.investor.api.dtos.InvestorDTO
import com.investor.api.dtos.LoginInvestorDTO
import com.investor.api.entities.Investor
import com.investor.api.repositories.HistoricCoinsRepository
import com.investor.api.repositories.HistoricEmailsRepository
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
class HistoricEmailsControllerTest {

    @Autowired
    private lateinit var historicEmailsRepository: HistoricEmailsRepository

    @Autowired
    private lateinit var investorRepository: InvestorRepository

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    companion object {
        const val URL: String = "/emails/historic"

    }

    @BeforeEach
    fun setup() {
        historicEmailsRepository.deleteAll()
        investorRepository.deleteAll()
    }

    @AfterEach
    fun tearDown() {
        historicEmailsRepository.deleteAll()
        investorRepository.deleteAll()
    }

    @Test
    fun `should return 200 because exists investor`() {
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
            MockMvcRequestBuilders.post(HistoricEmailsControllerTest.URL)
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
            MockMvcRequestBuilders.post(HistoricEmailsControllerTest.URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(valueAsString)
        )

            .andExpect(MockMvcResultMatchers.status().isNotAcceptable)
            .andDo(MockMvcResultHandlers.print())

    }

}