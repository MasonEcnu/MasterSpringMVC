package masterSpringMVC.config

import com.alibaba.fastjson.serializer.SerializerFeature
import com.alibaba.fastjson.support.config.FastJsonConfig
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter
import masterSpringMVC.date.USLocalDateFormatter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.format.FormatterRegistry
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.web.servlet.LocaleResolver
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor
import org.springframework.web.servlet.i18n.SessionLocaleResolver

import java.time.LocalDate
import java.util.*

@Configuration
class WebConfiguration : WebMvcConfigurerAdapter() {
  override fun addFormatters(registry: FormatterRegistry?) {
    registry?.addFormatterForFieldType(LocalDate::class.java, USLocalDateFormatter())
  }

  @Bean
  fun localeResolver(): LocaleResolver = SessionLocaleResolver().apply { setDefaultLocale(Locale.SIMPLIFIED_CHINESE) }

  @Bean
  fun localeChangeInterceptor(): LocaleChangeInterceptor = LocaleChangeInterceptor().apply { paramName = "lang" }

  override fun addInterceptors(registry: InterceptorRegistry) {
    registry.addInterceptor(localeChangeInterceptor())
  }
}