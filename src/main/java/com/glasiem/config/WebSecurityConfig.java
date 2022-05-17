package com.glasiem.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception{
        httpSecurity.authorizeRequests()
                    .antMatchers("/registrations").permitAll()
                    .antMatchers("/agency").permitAll()
                    .antMatchers("/agency/create").hasRole("ADMIN")
                    .antMatchers("/agency/edit").hasRole("ADMIN")
                    .antMatchers("/agency/delete").hasRole("ADMIN")
                    .antMatchers("/generation").permitAll()
                    .antMatchers("/generation/sorted").permitAll()
                    .antMatchers("/generation/create").hasRole("ADMIN")
                    .antMatchers("/generation/edit").hasRole("ADMIN")
                    .antMatchers("/generation/delete").hasRole("ADMIN")
                    .antMatchers("/vtuber").permitAll()
                    .antMatchers("/vtuber/sorted").permitAll()
                    .antMatchers("/vtuber/create").hasRole("ADMIN")
                    .antMatchers("/vtuber/edit").hasRole("ADMIN")
                    .antMatchers("/vtuber/delete").hasRole("ADMIN")
                    .antMatchers("/manager").permitAll()
                    //.antMatchers("/manager/sorted").permitAll()
                    .antMatchers("/manager/create").hasRole("ADMIN")
                    .antMatchers("/manager/edit").hasRole("ADMIN")
                    .antMatchers("/manager/delete").hasRole("ADMIN")
                    .antMatchers("/media").hasAnyRole("USER", "ADMIN")
                    .antMatchers("/media/sorted").hasAnyRole("USER", "ADMIN")
                    .antMatchers("/media/create").hasRole("ADMIN")
                    .antMatchers("/media/edit").hasRole("ADMIN")
                    .antMatchers("/media/delete").hasRole("ADMIN")
                .and()
                    .formLogin()
                    .loginPage("/login")
                    .permitAll()
                .and()
                    .logout()
                    .permitAll()
                .and()
                    .csrf().disable()
                    .exceptionHandling().accessDeniedPage("/access/denied");

    }

    @Override
    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception{
        authenticationManagerBuilder.jdbcAuthentication()
                .dataSource(dataSource)
                .passwordEncoder(passwordEncoder())
                .usersByUsernameQuery("select username, password, active from user_entity where username = ?")
                .authoritiesByUsernameQuery("select u.username, ur.roles from user_entity u inner join user_entity_roles ur on u.id = ur.user_entity_id where u.username=?");
    }
}
