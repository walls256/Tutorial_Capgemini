package com.ccsw.tutorial.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import com.ccsw.tutorial.client.model.ClientDto;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ClientIT {

    public static final String LOCALHOST = "http://localhost:";
    public static final String SERVICE_PATH = "/client";

    public static final Long MODIFY_CLIENT_ID = 3L;
    public static final Long NEW_CLIENT_ID = 14L;
    public static final Long DELETE_CLIENT_ID = 5L;
    public static final String NEW_CLIENT_NAME = "Nuevo Cliente";
    public static final String CLIENT_NAME = "Cliente 2";
    private static final int TOTAL_CLIENTS = 6;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    ParameterizedTypeReference<List<ClientDto>> responseType = new ParameterizedTypeReference<List<ClientDto>>() {
    };

    @Test
    public void findAllShouldReturnAllClients() {

        ResponseEntity<List<ClientDto>> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH,
                HttpMethod.GET, null, responseType);

        assertNotNull(response);
        assertEquals(TOTAL_CLIENTS, response.getBody().size());
    }

    @Test
    public void saveWithoutIdandUniqueNameShouldCreateNewClient() {

        long newClientId = TOTAL_CLIENTS + 1;
        long newClientSize = TOTAL_CLIENTS + 1;

        ClientDto dto = new ClientDto();
        dto.setName(NEW_CLIENT_NAME);

        restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.PUT, new HttpEntity<>(dto), Void.class);

        ResponseEntity<List<ClientDto>> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH,
                HttpMethod.GET, null, responseType);
        assertNotNull(response);
        assertEquals(newClientSize, response.getBody().size());

        ClientDto clientSearch = response.getBody().stream().filter(item -> item.getId().equals(newClientId))
                .findFirst().orElse(null);
        assertNotNull(clientSearch);
        assertEquals(NEW_CLIENT_NAME, clientSearch.getName());
    }

    @Test
    public void saveWithoutIdAndExistNameShouldInternalError() {

        ClientDto existingClient = new ClientDto();
        existingClient.setName(CLIENT_NAME);

        ResponseEntity<?> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.PUT,
                new HttpEntity<>(existingClient), Void.class);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());

    }

    @Test
    public void modifyWithExistIdAndUniqueNameShouldModifyClient() {

        ClientDto dto = new ClientDto();
        dto.setName(NEW_CLIENT_NAME);

        restTemplate.exchange(LOCALHOST + port + SERVICE_PATH + "/" + MODIFY_CLIENT_ID, HttpMethod.PUT,
                new HttpEntity<>(dto), Void.class);

        ResponseEntity<List<ClientDto>> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH,
                HttpMethod.GET, null, responseType);
        assertNotNull(response);
        assertEquals(TOTAL_CLIENTS, response.getBody().size());

        ClientDto clientSearch = response.getBody().stream().filter(item -> item.getId().equals(MODIFY_CLIENT_ID))
                .findFirst().orElse(null);
        assertNotNull(clientSearch);
        assertEquals(NEW_CLIENT_NAME, clientSearch.getName());
    }

    @Test
    public void modifyWithExistIdAndExistNameShouldInternalError() {

        ClientDto existingClient = new ClientDto();
        existingClient.setName(CLIENT_NAME);

        ResponseEntity<?> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH + "/" + MODIFY_CLIENT_ID,
                HttpMethod.PUT, new HttpEntity<>(existingClient), Void.class);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void modifyWithNotExistIdShouldInternalError() {

        ClientDto dto = new ClientDto();
        dto.setName(NEW_CLIENT_NAME);

        ResponseEntity<?> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH + "/" + NEW_CLIENT_ID,
                HttpMethod.PUT, new HttpEntity<>(dto), Void.class);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void deleteWithExistsIdShouldDeleteClient() {

        long newClientSize = TOTAL_CLIENTS - 1;

        restTemplate.exchange(LOCALHOST + port + SERVICE_PATH + "/" + DELETE_CLIENT_ID, HttpMethod.DELETE, null,
                Void.class);

        ResponseEntity<List<ClientDto>> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH,
                HttpMethod.GET, null, responseType);
        assertNotNull(response);
        assertEquals(newClientSize, response.getBody().size());
    }

    @Test
    public void deleteWithNotExistsIdShouldInternalError() {

        ResponseEntity<?> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH + "/" + NEW_CLIENT_ID,
                HttpMethod.DELETE, null, Void.class);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

}
