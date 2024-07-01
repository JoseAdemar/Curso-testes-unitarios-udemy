package com.appsdeveloperblog.tutorials.junit.ui.controlle;

import com.appsdeveloperblog.tutorials.junit.security.SecurityConstants;
import com.appsdeveloperblog.tutorials.junit.ui.response.UserRest;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.TestPropertySource;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestPropertySource(locations = "/application-test.properties", properties = "server.port=8081")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UsersControllerIntegrationTest {

    @Value("${server.port}")
    private int serverPort;

    @Autowired
    private TestRestTemplate testRestTemplate;

    private String authorizationToken;

    @Test
    void contextLoads() {
        System.out.println("server.port = " + serverPort);
    }

    @Test
    @DisplayName("User can be created")
    @Order(1)
    void testCreateUser_whenValidDetailsProvided_returnsUserDetails() throws JSONException {
        //Arrange
        JSONObject userDetailsRequestJason = new JSONObject();
        userDetailsRequestJason.put("firstName", "Sergey");
        userDetailsRequestJason.put("lastName", "Kargopolov");
        userDetailsRequestJason.put("email", "test@email.com");
        userDetailsRequestJason.put("password", "12345678");
        userDetailsRequestJason.put("repeatPassword", "12345678");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        HttpEntity<String> request = new HttpEntity<>(userDetailsRequestJason.toString(), headers);

        //Act
        ResponseEntity<UserRest> createUserDetailsEntity = testRestTemplate.postForEntity("/users",
                request,
                UserRest.class);
        UserRest createdUserDetails = createUserDetailsEntity.getBody();

        //Assert
        assertEquals(HttpStatus.OK, createUserDetailsEntity.getStatusCode());
        assertEquals(userDetailsRequestJason.getString("firstName"),
                createdUserDetails.getFirstName());
        assertEquals(userDetailsRequestJason.getString("lastName"),
                createdUserDetails.getLastName());
        assertEquals(userDetailsRequestJason.getString("email"),
                createdUserDetails.getEmail());
        assertFalse(createdUserDetails.getUserId().trim().isEmpty());

    }

    @Test
    @DisplayName("GET /users requires JWT")
    @Order(2)
    void testGetUsers_whenMissingJWT_returns403() {
        //Arrange
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");

        HttpEntity requestEntity = new HttpEntity(null, headers);

        //Act
        ResponseEntity<List<UserRest>> response = testRestTemplate.exchange("/users", HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<List<UserRest>>() {
                });

        //Assert
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    @DisplayName("/login works")
    @Order(3)
    void testUserLogin_whenValidCredentialsProvided_returnsJWTinAuthorizationHeader() throws JSONException {
        //Arrange
        JSONObject loginCredentials = new JSONObject();
        loginCredentials.put("email", "test@email.com");
        loginCredentials.put("password", 12345678);

        HttpEntity<String> request = new HttpEntity<>(loginCredentials.toString());

        //Act
        ResponseEntity<Object> response = testRestTemplate.postForEntity("/users/login", request, null);
        authorizationToken = response.getHeaders().getValuesAsList(SecurityConstants.HEADER_STRING).get(0);

        //Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(authorizationToken);
        assertNotNull(response.getHeaders()
                .getValuesAsList("UserID").get(0));
    }

    @Test
    @Order(4)
    @DisplayName("GET /users works")
    void testGetUsers_whenValidJWTProvided_returnsUsers(){
        //Arrange
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setBearerAuth(authorizationToken);

        HttpEntity requestEntity = new HttpEntity(headers);

        //Act
        ResponseEntity<List<UserRest>> response = testRestTemplate.exchange("/users",
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<List<UserRest>>() {
                });

        //Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().size() == 1);

    }

}
