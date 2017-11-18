package masterSpringMVC.date

import java.lang.annotation.ElementType
import java.time.LocalDate
import javax.validation.Constraint
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext
import kotlin.reflect.KClass
import javax.validation.Payload

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = arrayOf(PastValidator::class))
annotation class PastLocalDate(val message: String = "{javax.validation.constraints.Past.message}", val groups: Array<KClass<Any>> = arrayOf(), val payload: Array<KClass<out Payload>> = arrayOf())

class PastValidator : ConstraintValidator<PastLocalDate, LocalDate> {
  override fun initialize(constraintAnnotation: PastLocalDate?) {}

  override fun isValid(value: LocalDate?, context: ConstraintValidatorContext?): Boolean = value == null || value.isBefore(LocalDate.now())
}