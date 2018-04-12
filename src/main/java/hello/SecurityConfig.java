package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import javax.sql.DataSource;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  protected void configure(HttpSecurity http) throws Exception {
      http.authorizeRequests()
        .anyRequest().authenticated()
        .and().httpBasic();
  }

  @Autowired
  private DataSource dataSource;

  @Autowired
  public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
  	// ensure the passwords are encoded properly
  	UserBuilder users = User.withDefaultPasswordEncoder();
  	auth
  		.jdbcAuthentication()
  			.dataSource(dataSource)
  			// .withDefaultSchema() // Here is probably a problem with non-existen data types
  			.withUser(users.username("user").password("password").roles("USER"))
  			.withUser(users.username("admin").password("password").roles("USER","ADMIN"));
  }

/*
  @Autowired
  public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        // Later check inMemoryAuthentication with hashed password.
        // auth.inMemoryAuthentication().withUser("user")
        //   .password("{noop}password").roles("USER");
        // {noop} prefix forces usage of NoOpPasswordEncoder - test password encoder which does not encrypt passwords:
        // https://stackoverflow.com/questions/46999940/spring-boot-passwordencoder-error
  }
*/

}
