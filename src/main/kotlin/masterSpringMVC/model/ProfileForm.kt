package masterSpringMVC.model

import java.time.LocalDate

data class ProfileForm(
    var name: String = "",
    var email: String = "",
    var mobilePhone: String = "",
    var birthDate: LocalDate? = null,
    var tastes: List<String> = mutableListOf()
)