package com.walnutclinics.billingService.configuration.security;

import org.keycloak.adapters.springsecurity.KeycloakConfiguration;
import org.keycloak.adapters.springsecurity.authentication.KeycloakAuthenticationProvider;
import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;

@KeycloakConfiguration //1
public class SecurityConfig extends KeycloakWebSecurityConfigurerAdapter { //2

    private static final String[] WHITELIST = {
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/api/v1/user/login",
            "/api/v1/receipt/receiptView/**"
//            "/api/v1/user/refreshToken"
    };

    @Override
    protected void configure(HttpSecurity http) throws Exception { //3
        super.configure(http);
        http
                .authorizeRequests()
                .antMatchers(WHITELIST).permitAll()
                .anyRequest().authenticated();
        http.csrf().disable();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        KeycloakAuthenticationProvider keycloakAuthenticationProvider = keycloakAuthenticationProvider();
        keycloakAuthenticationProvider.setGrantedAuthoritiesMapper(new SimpleAuthorityMapper());
        auth.authenticationProvider(keycloakAuthenticationProvider);
    }

    private KeycloakAuthenticationProvider getKeycloakAuthenticationProvider() { //5
        KeycloakAuthenticationProvider authenticationProvider = keycloakAuthenticationProvider();
        var mapper = new SimpleAuthorityMapper();
        mapper.setConvertToUpperCase(true);
        authenticationProvider.setGrantedAuthoritiesMapper(mapper);

        return authenticationProvider;
    }

    @Bean
    @Override
    protected SessionAuthenticationStrategy sessionAuthenticationStrategy() { //6
        return new RegisterSessionAuthenticationStrategy(new SessionRegistryImpl());
    }
}