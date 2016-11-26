package de.zooplus.converter.web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by dragan
 */
@Configuration
@EnableWebMvcSecurity
@ComponentScan(value = {"de.zooplus.converter"})
public class WebSecurityConfig {

    @Configuration
    @Order(2)
    public static class FormSecurityConfig extends WebSecurityConfigurerAdapter {

        @Autowired
        @Qualifier("ConverterUserDetailsService")
        private UserDetailsService converterUserDetailsService;

        private static final String PASSWORD_SECRET = "secret";

        @Autowired
        public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(converterUserDetailsService);
            auth.authenticationProvider(authenticationProvider());

        }


        @Bean
        public PasswordEncoder passwordEncoder() {
            return new StandardPasswordEncoder(PASSWORD_SECRET);
        }


        @Bean
        public DaoAuthenticationProvider authenticationProvider() {
            DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
            authenticationProvider.setUserDetailsService(converterUserDetailsService);
            authenticationProvider.setPasswordEncoder(passwordEncoder());
            return authenticationProvider;
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .authorizeRequests()
                    .antMatchers("/home/**")
                    .hasAnyAuthority("USER")
                    .and()
                    .csrf()
                    .disable()
                    .formLogin()
                    .loginPage("/login").usernameParameter("email").passwordParameter("password")
                    .and()
                    .authorizeRequests()
                    .antMatchers("/login", "/registration", "/resources/**")
                    .permitAll();
        }
    }

    @Configuration
    @Order(1)
    public static class WebserviceSecurityConfig extends WebSecurityConfigurerAdapter {

        @Value("${REST_USER}")
        private String restUser;

        @Value("${REST_PASSWORD}")
        private String restPassword;


        @Override
        public void configure(final HttpSecurity http) throws Exception {

            http
                    .requestMatchers()
                    .antMatchers("/webservice/**")
                    .and()
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                    .authorizeRequests()
                    .anyRequest()
                    .hasAnyAuthority("WEBSERVICE_USER")
                    .and()
                    .csrf().disable()
                    .httpBasic().realmName("REST requests");

        }

        @Override
        public void configure(final AuthenticationManagerBuilder auth) throws Exception {
            auth.inMemoryAuthentication()
                    .withUser(restUser).password(restPassword).authorities("WEBSERVICE_USER");
        }

        @Bean(name = "authenticationManager")
        @Override
        public AuthenticationManager authenticationManagerBean() throws Exception {
            return super.authenticationManagerBean();
        }
    }
}