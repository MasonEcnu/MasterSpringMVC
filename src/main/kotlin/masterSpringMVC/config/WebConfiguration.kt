package masterSpringMVC.config

import masterSpringMVC.date.USLocalDateFormatter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.format.FormatterRegistry
import org.springframework.web.servlet.LocaleResolver
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor
import org.springframework.web.servlet.i18n.SessionLocaleResolver

import java.time.LocalDate

@Configuration
class WebConfiguration : WebMvcConfigurerAdapter() {
  override fun addFormatters(registry: FormatterRegistry?) {
    registry?.addFormatterForFieldType(LocalDate::class.java, USLocalDateFormatter())
  }

  @Bean
  fun localeResolver(): LocaleResolver = SessionLocaleResolver()

  @Bean
  fun localeChangeInterceptor(): LocaleChangeInterceptor = LocaleChangeInterceptor().apply { paramName = "lang" }

  override fun addInterceptors(registry: InterceptorRegistry) {
    registry.addInterceptor(localeChangeInterceptor())
  }
}