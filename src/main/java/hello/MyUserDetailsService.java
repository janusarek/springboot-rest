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
