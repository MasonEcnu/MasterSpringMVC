package masterSpringMVC.config

import masterSpringMVC.date.USLocalDateFormatter
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer
import org.springframework.boot.web.servlet.ErrorPage
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.format.FormatterRegistry
import org.springframework.web.multipart.MultipartException
import org.springframework.web.servlet.LocaleResolver
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor
import org.springframework.web.servlet.i18n.SessionLocaleResolver
import org.springframework.web.util.UrlPathHelper
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2

import java.time.LocalDate
import java.util.*

@Configuration
@EnableSwagger2
class WebConfiguration : WebMvcConfigurerAdapter() {
  override fun addFormatters(registry: FormatterRegistry?) {
    registry?.addFormatterForFieldType(LocalDate::class.java, USLocalDateFormatter())
  }

  @Bean
  fun containerCustomizer(): EmbeddedServletContainerCustomizer = EmbeddedServletContainerCustomizer { container ->
    container.addErrorPages(ErrorPage(MultipartException::class.java, "/uploadError"))
  }

  override fun configurePathMatch(configurer: PathMatchConfigurer?) {
    val urlPathHelper = UrlPathHelper()
    urlPathHelper.setRemoveSemicolonContent(false)
    configurer?.urlPathHelper = urlPathHelper
    configurer?.isUseRegisteredSuffixPatternMatch = true
  }

  @Bean
  fun localeResolver(): LocaleResolver = SessionLocaleResolver().apply { setDefaultLocale(Locale.SIMPLIFIED_CHINESE) }

  @Bean
  fun localeChangeInterceptor(): LocaleChangeInterceptor = LocaleChangeInterceptor().apply { paramName = "lang" }

  override fun addInterceptors(registry: InterceptorRegistry) {
    registry.addInterceptor(localeChangeInterceptor())
  }

  @Bean
  fun userApi(): Docket = Docket(DocumentationType.SWAGGER_2).select().paths { path ->
    path?.startsWith("/api/") ?: false
  }.build()
}