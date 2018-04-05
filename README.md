# securedSpringBoot
Secured Spring Boot Application extracting custom claims using KeyCloak

The application has a secure endpoint '/greeting' , if a request is successful it will return 'Hello x' where X is the principal name derived from the JWT token.  In the applicaiton console output a list of 'otherClaims' will be printed. 

## Prerequisites

1. A Configured Keycloak or Redhat SSO server (Realm / User / Roles)
2. The SSO certificate needs to be placed in a truststore that this applicaition can access (example keystores in repo)

### Config

Amend the application.properties file as required

Note the following

Property|Description
--------|-----------
keycloak.realm| Should be set to the assocaited relam in key cloak
keycloak.auth-server-url=|The url to key cloak
keycloak.resource| The 'client' as setup in key cloak
keycloak.bearer-only| When this is set to true the application will only validate the JWT token and will not send a redirection to log on via key cloak
keycloak.principal-attribute| Sets what attribute is used to propulate the Principal Name attribute

The following section in the Application.properties file controls which roles, as defined in key cloak, can access which endpoints.  In the example below the authenticated user needs to have the role 'swarmRole' to access the /greeting end point

keycloak.securityConstraints[0].authRoles[0]=swarmRole 
keycloak.securityConstraints[0].securityCollections[0].name=application
keycloak.securityConstraints[0].securityCollections[0].patterns[0]=/greeting

This logging entry is useful when setting up connectivity between the application and key cloak

logging.level.org.keycloak: DEBUG

### Local Deployment

The application needs to be associated with a truststore that contains the SSO Certificate for key cloak

mvn -Djavax.net.ssl.trustStore=./certs/truststore.jks -Djavax.net.ssl.trustStorePassword=password spring-boot:run
