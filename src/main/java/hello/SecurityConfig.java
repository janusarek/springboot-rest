package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import javax.sql.DataSource;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.User;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  protected void configure(HttpSecurity http) throws Exception {
      http.authorizeRequests()
        .antMatchers("/","/home").permitAll()
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
        .withUser(users.username("user").password("password").roles("USER"))
        .withUser(users.username("admin").password("password").roles("USER","ADMIN"))
        .withUser(users.username("foo").password("bar2").roles("USER"));

    // TODO: App will try to make this users on every start. Try to add user here only if none exists.

    // auth.withDefaultSchema() can often be seen in online tutorial, but it will probably work only with DB like H2
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
