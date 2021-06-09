package com.thedariusz.warnme;

import com.thedariusz.warnme.user.UserDetailsSecurityService;
import com.thedariusz.warnme.user.UserService;
import com.thedariusz.warnme.user.repository.UserRepository;
import com.thedariusz.warnme.user.repository.RoleJpaRepository;
import com.thedariusz.warnme.user.UserDao;
import com.thedariusz.warnme.user.repository.UserJpaRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/", "/alerts", "/css/**", "/js/**", "/images/**").permitAll()
                .antMatchers("/alerts/register", "/alerts/create-user").permitAll()
                .antMatchers("/alerts/twitter").authenticated()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/alerts/login").permitAll()
                .defaultSuccessUrl("/alerts", true)
                .and()
                .logout()
                .logoutUrl("/alerts/logout")
                .logoutSuccessUrl("/alerts")
                .permitAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(customUserDetailsService())
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsSecurityService customUserDetailsService() {
        return new UserDetailsSecurityService();
    }

    @Bean
    public UserDao userDao(UserJpaRepository userJpaRepository, RoleJpaRepository roleDao, BCryptPasswordEncoder passwordEncoder) {
        return new UserRepository(userJpaRepository, roleDao, passwordEncoder);
    }

    @Bean
    public UserService userService(UserDao userDao) {
        return new UserService(userDao);
    }
}
