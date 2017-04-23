package com.student.newsportalspringboot.configuration;

import com.student.newsportalspringboot.services.person.PersonDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Value("${default.user.name}")
    private String name;

    @Value("${default.user.password}")
    private String password;

    @Autowired
    private PersonDetailsServiceImpl detailsServiceImpl;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/", "/post/**", "/webjars/**", "/css/**", "/registration", "/image").permitAll()
                .antMatchers("/user").hasAnyRole("USER", "ADMIN")
                .antMatchers("/admin/person/settings/**").hasRole("SUPER_ADMIN")
                .anyRequest().hasAnyRole("ADMIN", "SUPER_ADMIN")
                .and()
                .formLogin().failureUrl("/login?error")
                .loginPage("/login").usernameParameter("email").passwordParameter("password").permitAll()
                .and()
                .logout().permitAll();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder amb) throws Exception {
        amb.inMemoryAuthentication().withUser(name).password(password).roles("SUPER_ADMIN");
        amb.userDetailsService(detailsServiceImpl).passwordEncoder(passwordEncoder());

    }

}
