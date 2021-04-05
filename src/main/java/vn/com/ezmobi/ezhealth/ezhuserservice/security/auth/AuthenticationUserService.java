package vn.com.ezmobi.ezhealth.ezhuserservice.security.auth;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import vn.com.ezmobi.ezhealth.ezhuserservice.domain.Privilege;
import vn.com.ezmobi.ezhealth.ezhuserservice.domain.Role;
import vn.com.ezmobi.ezhealth.ezhuserservice.domain.User;
import vn.com.ezmobi.ezhealth.ezhuserservice.repositories.UserRepository;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by ezmobivietnam on 2021-04-04.
 */
@Service
@Transactional
public class AuthenticationUserService implements UserDetailsService {

    private final UserRepository userRepository;

    public AuthenticationUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository.findAllByEmail(s).stream()
                .findFirst()
                .orElseThrow(() -> new UsernameNotFoundException(String.format("Username %s not found", s)));
        return new AuthenticationUserDetails(
                user.getEmail(),
                user.getPassword(),
                getAuthorities(user),
                true,
                true,
                true,
                user.getActive()
        );
    }

    private final Collection<? extends GrantedAuthority> getAuthorities(User user) {
        Set<GrantedAuthority> authorities = new HashSet<>();
        if (!CollectionUtils.isEmpty(user.getRoles())) {
            for (Role role : user.getRoles()) {
                authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
                if (!CollectionUtils.isEmpty(role.getPrivileges())) {
                    for (Privilege privilege : role.getPrivileges()) {
                        authorities.add(new SimpleGrantedAuthority(privilege.getName()));
                    }
                }
            }
        }
        return authorities;
    }
}
