package masterSpringMVC.user

import java.time.LocalDate

data class User(
    var name: String = "",
    var email: String = "",
    var mobilePhone: String = "",
    var birthDate: LocalDate? = null,
    var tastes: List<String> = mutableListOf()
)