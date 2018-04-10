package hello;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

// Source: https://www.logicbig.com/tutorials/spring-framework/spring-security/user-details-service.html
public class MyUserDetailsService implements UserDetailsService {
    private static List<UserObject> users = new ArrayList();

    public MyUserDetailsService() {
        //in a real application, instead of using local data,
        // we will find user details by some other means e.g. from an external system
        users.add(new UserObject("erin", "{noop}123", "ADMIN")); // It may not work without explicit encoder e.g. {noop}
        users.add(new UserObject("mike", "{noop}234", "ADMIN"));
        users.add(new UserObject("todd", "{bcrypt}$2a$04$o8D4uVjGvYZjGo84peVz1.wVZatn80GWjRctOrUJoS5FH7RPNPjSi", "ADMIN")); // 'password2'
        users.add(new UserObject("suzy", "{bcrypt}$2a$04$9/4eXcGI8re4JApkU.c.jOk330BfMWsqkvWmitC2WYTYUnWS9UHSu", "ADMIN")); // '123456'
        users.add(new UserObject("matt", "{bcrypt}$2a$04$dUQbLsYq.3hgFp7jWQQj2eV0jR1KDOtvnFZA9hvRveChqv50d32TK", "ADMIN")); // '123456'
        users.add(new UserObject("alice", "{bcrypt}$2a$04$N7lX6KegKqJSgOPtrEos3e/miHTOJ9t8kfFl0coW8KixfAhWSS14y", "ADMIN")); // '123456'

        // List of possible password encoders:
        // https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/crypto/password/DelegatingPasswordEncoder.html
        // Usefull bcrypt calculator:
        // https://www.dailycred.com/article/bcrypt-calculator
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserObject> user = users.stream()
                                         .filter(u -> u.name.equals(username))
                                         .findAny();
        if (!user.isPresent()) {
            throw new UsernameNotFoundException("User not found by name: " + username);
        }
        return toUserDetails(user.get());
    }

    private UserDetails toUserDetails(UserObject userObject) {
        return User.withUsername(userObject.name)
                   .password(userObject.password)
                   .roles(userObject.role).build();
    }

    private static class UserObject {
        private String name;
        private String password;
        private String role;

        public UserObject(String name, String password, String role) {
            this.name = name;
            this.password = password;
            this.role = role;
        }
    }
}
