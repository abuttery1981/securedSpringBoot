package com.redhat.uki.sso3Scale.securedSpringBoot.controller;

import java.util.Map;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.representations.AccessToken;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequiredArgsConstructor
public class GreetingController {

    private static final String template = "Hello, %s!";

    private final KeycloakSecurityContext securityContext = null;
    private final AccessToken accessToken = null;
   
    @RequestMapping("/greeting")
    public String greeting(HttpServletRequest request) {

        AccessToken token = ((KeycloakPrincipal) request.getUserPrincipal()).getKeycloakSecurityContext().getToken();
        Map<String, Object> otherClaims = token.getOtherClaims();

        for (String key : otherClaims.keySet())
        {
            System.out.println(key);
            System.out.println(otherClaims.get(key).toString());
        }
  
        return  String.format(template, request.getUserPrincipal().getName());
    }

    @RequestMapping("/greetings")
    public String greeting(@RequestParam(value="name", defaultValue="World") String name) {
        try
        {
        System.err.println("AccessToken: " + securityContext.getTokenString());
        System.err.println("User: " +  accessToken.getPreferredUsername() + " - " + accessToken.getName());

        }
        catch (Exception e)
        {

        }
        return  String.format(template, "World!");
    }
}