package masterSpringMVC.date

import org.springframework.format.Formatter
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class USLocalDateFormatter : Formatter<LocalDate> {

  companion object {
    private val US_PATTERN = "MM/dd/yyyy"
    private val NORMAL_PATTERN = "dd/MM/yyyy"

    fun getPattern(locale: Locale?): String = if (isUnitedStates(locale)) US_PATTERN else NORMAL_PATTERN

    private fun isUnitedStates(locale: Locale?): Boolean = Locale.US.country == locale?.country
  }

  override fun print(`object`: LocalDate?, locale: Locale?): String =
      DateTimeFormatter.ofPattern(getPattern(locale)).format(`object`)

  override fun parse(text: String?, locale: Locale?): LocalDate =
      LocalDate.parse(text, DateTimeFormatter.ofPattern(getPattern(locale)))

}