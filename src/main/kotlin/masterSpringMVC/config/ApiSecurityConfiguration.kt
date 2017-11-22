package masterSpringMVC.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter

@Configuration
@Order(1)
class ApiSecurityConfiguration : WebSecurityConfigurerAdapter() {

  @Autowired
  @Throws(Exception::class)
  fun configureAuth(auth: AuthenticationManagerBuilder) {
    auth.inMemoryAuthentication()
        .withUser("user").password("user").roles("USER")
        .and()
        .withUser("admin").password("admin").roles("USER", "ADMIN")
  }

  @Throws(Exception::class)
  override fun configure(http: HttpSecurity?) {
    if (http != null) {
      http
          .antMatcher("/api/**")
          .httpBasic()
          .and()
          .csrf().disable()
          .authorizeRequests()
          .antMatchers("/login", "/logout").permitAll()
          .antMatchers(HttpMethod.GET).hasRole("USER")
          .antMatchers(HttpMethod.POST).hasRole("ADMIN")
          .antMatchers(HttpMethod.PUT).hasRole("ADMIN")
          .antMatchers(HttpMethod.DELETE).hasRole("USER")
          .anyRequest().authenticated()
    }
  }
}
