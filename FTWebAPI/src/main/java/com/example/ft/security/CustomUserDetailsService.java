package com.example.ft.security;

import com.example.ft.entity.application.Application;
import com.example.ft.entity.application.ApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${ft.security.admin_account}")
    private String adminUser;

    @Value("${ft.security.admin_password}")
    private String adminPassword;

    @Value("${ft.security.admin_role}")
    private String adminRole;

    @Value("${ft.security.user_role}")
    private String userRole;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String password;
        String role;

        if (username.equals(adminUser)) {
            password = passwordEncoder.encode(adminPassword);
            role = adminRole;
        } else {
            Application application = applicationRepository.findByUsername(username)
                    .orElseThrow(() ->
                            new UsernameNotFoundException("Application not found"));
            password = application.getPassword();
            role = userRole;
        }

        return new UserPrincipal(username, password, new String[]{role});
    }

}
