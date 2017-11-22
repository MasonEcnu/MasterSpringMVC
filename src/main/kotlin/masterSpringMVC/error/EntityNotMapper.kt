package masterSpringMVC.error

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus

@ControllerAdvice
class EntityNotMapper{
  @ExceptionHandler(EntityNotFoundException::class)
  @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Entity could not be found!")
  fun handlerNotFound(){}
}