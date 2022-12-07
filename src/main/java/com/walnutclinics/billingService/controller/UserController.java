package com.walnutclinics.billingService.controller;

import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.walnutclinics.billingService.configuration.security.KeycloakProvider;
import com.walnutclinics.billingService.request.CreateUserRequest;
import com.walnutclinics.billingService.request.LoginRequest;
import com.walnutclinics.billingService.service.KeycloakAdminClientService;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.AccessTokenResponse;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.core.Response;


@RestController
@RequestMapping("api/v1/user")
public class UserController {
    private static final Logger LOG = org.slf4j.LoggerFactory.getLogger(UserController.class);
    private final KeycloakAdminClientService kcAdminClient;
    private final KeycloakProvider kcProvider;


    public UserController(KeycloakAdminClientService kcAdminClient, KeycloakProvider kcProvider) {
        this.kcProvider = kcProvider;
        this.kcAdminClient = kcAdminClient;
    }


    @PostMapping(value = "/create")
    public ResponseEntity<?> createUser(@RequestBody CreateUserRequest user) {
        Response createdResponse = kcAdminClient.createKeycloakUser(user);
        return ResponseEntity.status(createdResponse.getStatus()).build();

    }

    @PostMapping("/login")
    public ResponseEntity<AccessTokenResponse> login(@NotNull @RequestBody LoginRequest loginRequest) {
        Keycloak keycloak = kcProvider.newKeycloakBuilderWithPasswordCredentials(loginRequest.getUsername(), loginRequest.getPassword()).build();

        AccessTokenResponse accessTokenResponse = null;
        try {
            accessTokenResponse = keycloak.tokenManager().getAccessToken();

            return ResponseEntity.status(HttpStatus.OK).body(accessTokenResponse);

        } catch (BadRequestException ex) {
            LOG.warn("invalid account. User probably hasn't verified email.", ex);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(accessTokenResponse);
        }
    }

    @GetMapping("/refreshToken")
    public JsonNode refresh(@NotNull @RequestParam String token) throws UnirestException {
        return kcProvider.refreshToken(token);
    }


}
