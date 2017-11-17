package masterSpringMVC.config

import masterSpringMVC.dates.USLocalDateFormatter
import org.springframework.context.annotation.Configuration
import org.springframework.format.FormatterRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter

import java.time.LocalDate

@Configuration
class WebConfiguration : WebMvcConfigurerAdapter() {
  override fun addFormatters(registry: FormatterRegistry?) {
    registry?.addFormatterForFieldType(LocalDate::class.java, USLocalDateFormatter())
  }
}