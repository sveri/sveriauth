package de.sveri.auth.controller

import org.springframework.http.HttpStatus
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus


enum class RestErrorTypes {VALIDATION}

data class RestError(val type: RestErrorTypes, val error: MutableList<FieldError>)

@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handle(exception: MethodArgumentNotValidException): RestError {
        return RestError(RestErrorTypes.VALIDATION, exception.bindingResult.fieldErrors)
    }



}