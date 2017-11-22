package masterSpringMVC.config

import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter

@Configuration
class WebSecurityConfiguration : WebSecurityConfigurerAdapter() {

  @Throws(Exception::class)
  override fun configure(http: HttpSecurity?) {
    if (http != null) {
      http
          .formLogin()
          .loginPage("/login")
          .defaultSuccessUrl("/profile")
          .and()
          .logout().logoutSuccessUrl("/login")
          .and()
          .authorizeRequests()
          .antMatchers("/webjars/**", "/login").permitAll()
          .anyRequest().authenticated()
    }
  }
}
