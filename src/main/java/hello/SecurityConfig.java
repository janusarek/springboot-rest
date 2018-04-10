package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;

import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  protected void configure(HttpSecurity http) throws Exception {
      http.authorizeRequests()
        .anyRequest().authenticated()
        .and().httpBasic();
  }

  @Bean
  @Override
  public UserDetailsService userDetailsService() {
      return new MyUserDetailsService();
  }

  @Autowired
  public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        /*
        auth.inMemoryAuthentication().withUser("user")
          .password("{noop}password").roles("USER");

        // {noop} prefix forces usage of NoOpPasswordEncoder - test password encoder which does not encrypt passwords:
        // https://stackoverflow.com/questions/46999940/spring-boot-passwordencoder-error
        */
  }

}
