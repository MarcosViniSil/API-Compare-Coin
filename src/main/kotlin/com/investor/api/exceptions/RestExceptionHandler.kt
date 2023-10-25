package com.investor.api.exceptions

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.time.LocalDateTime

@RestControllerAdvice
class RestExceptionHandler {

    @ExceptionHandler(EmailInvalidException::class)
    fun handlerEmailInvalid(ex: EmailInvalidException): ResponseEntity<ExceptionDetails> {
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
            .body(
                ExceptionDetails(
                    title = "Email invalid",
                    timestamp = LocalDateTime.now(),
                    status = HttpStatus.NOT_ACCEPTABLE.value(),
                    exception = ex.javaClass.toString(),
                    details = mutableMapOf(ex.cause.toString() to ex.message)
                )
            )

    }

    @ExceptionHandler(InvestorInvalidException::class)
    fun handlerInvestorInvalid(ex: InvestorInvalidException): ResponseEntity<ExceptionDetails> {
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
            .body(
                ExceptionDetails(
                    title = "Investor Invalid",
                    timestamp = LocalDateTime.now(),
                    status = HttpStatus.NOT_ACCEPTABLE.value(),
                    exception = ex.javaClass.toString(),
                    details = mutableMapOf(ex.cause.toString() to ex.message)
                )
            )

    }

    @ExceptionHandler(InvestorNullException::class)
    fun handlerInvestorNull(ex: InvestorNullException): ResponseEntity<ExceptionDetails> {
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
            .body(
                ExceptionDetails(
                    title = "Investor null",
                    timestamp = LocalDateTime.now(),
                    status = HttpStatus.NOT_ACCEPTABLE.value(),
                    exception = ex.javaClass.toString(),
                    details = mutableMapOf(ex.cause.toString() to ex.message)
                )
            )

    }

    @ExceptionHandler(InvestorNotExistsException::class)
    fun handlerInvestorNotExists(ex: InvestorNotExistsException): ResponseEntity<ExceptionDetails> {
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
            .body(
                ExceptionDetails(
                    title = "Investor not exists",
                    timestamp = LocalDateTime.now(),
                    status = HttpStatus.NOT_ACCEPTABLE.value(),
                    exception = ex.javaClass.toString(),
                    details = mutableMapOf(ex.cause.toString() to ex.message)
                )
            )

    }

    @ExceptionHandler(LoginInvestorException::class)
    fun handlerInvestorLoginInvalid(ex: LoginInvestorException): ResponseEntity<ExceptionDetails> {
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
            .body(
                ExceptionDetails(
                    title = "Investor invalid",
                    timestamp = LocalDateTime.now(),
                    status = HttpStatus.NOT_ACCEPTABLE.value(),
                    exception = ex.javaClass.toString(),
                    details = mutableMapOf(ex.cause.toString() to ex.message)
                )
            )

    }

    @ExceptionHandler(ApiExternalException::class)
    fun handlerApiExternalException(ex: ApiExternalException): ResponseEntity<ExceptionDetails> {
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
            .body(
                ExceptionDetails(
                    title = "Investor invalid",
                    timestamp = LocalDateTime.now(),
                    status = HttpStatus.NOT_ACCEPTABLE.value(),
                    exception = ex.javaClass.toString(),
                    details = mutableMapOf(ex.cause.toString() to ex.message)
                )
            )

    }

    @ExceptionHandler(CoinInvalidException::class)
    fun handlerCoinInvalid(ex: CoinInvalidException): ResponseEntity<ExceptionDetails> {
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
            .body(
                ExceptionDetails(
                    title = "Coin invalid",
                    timestamp = LocalDateTime.now(),
                    status = HttpStatus.NOT_ACCEPTABLE.value(),
                    exception = ex.javaClass.toString(),
                    details = mutableMapOf(ex.cause.toString() to ex.message)
                )
            )

    }
}