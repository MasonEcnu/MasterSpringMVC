package masterSpringMVC.model

import masterSpringMVC.date.PastLocalDate
import org.hibernate.validator.constraints.Email
import org.hibernate.validator.constraints.NotBlank
import org.hibernate.validator.constraints.NotEmpty
import java.time.LocalDate
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

class ProfileForm {
  @Size(min = 2)
  var name: String = ""
  @Email()
  @NotEmpty()
  var email: String = ""
  @NotEmpty()
  var mobilePhone: String = ""
  @PastLocalDate
  @NotNull()
  var birthDate: LocalDate? = null
  @NotEmpty()
  var tastes: MutableList<String?> = mutableListOf()
}