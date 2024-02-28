package com.ccsw.tutorial.loan;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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

import com.ccsw.tutorial.author.model.AuthorSearchDto;
import com.ccsw.tutorial.client.model.ClientDto;
import com.ccsw.tutorial.common.pagination.PageableRequest;
import com.ccsw.tutorial.config.ResponsePage;
import com.ccsw.tutorial.game.model.GameDto;
import com.ccsw.tutorial.loan.model.LoanDto;
import com.ccsw.tutorial.loan.model.LoanSearchDto;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class LoanIT {

    public static final String LOCALHOST = "http://localhost:";
    public static final String SERVICE_PATH = "/loan";

    public static final Long EXISTS_LOAN_ID = 1L;
    private static final Long DELETE_LOAN_ID = 1L;

    private static final int TOTAL_LOANS = 6;
    private static final int PAGE_SIZE = 5;

    private static final LocalDateTime GAME_START_DATE = LocalDateTime.of(2024, 2, 10, 0, 0, 0);
    private static final LocalDateTime GAME_END_DATE = LocalDateTime.of(2024, 2, 15, 0, 0, 0);

    private static final LocalDateTime CLIENT_START_DATE = LocalDateTime.of(2024, 1, 27, 0, 0, 0);
    private static final LocalDateTime CLIENT_END_DATE = LocalDateTime.of(2024, 2, 5, 0, 0, 0);

    private static final Long GAME_ID = 3L;
    private static final Long CLIENT_ID = 2L;
    private static final LocalDateTime START_DATE = LocalDateTime.of(2024, 4, 2, 0, 0, 0);
    private static final LocalDateTime END_DATE = LocalDateTime.of(2024, 4, 12, 0, 0, 0);

    private static final LocalDateTime DATE = LocalDateTime.of(2024, 2, 5, 0, 0, 0);
    private static final LocalDateTime NOT_EXIST_DATE = LocalDateTime.of(2024, 1, 5, 0, 0, 0);

    @LocalServerPort
    private int port;

    @Mock
    private LoanRepository loanRepository;

    @InjectMocks
    private LoanServiceImpl loanService;

    @Autowired
    private TestRestTemplate restTemplate;

    ParameterizedTypeReference<ResponsePage<LoanDto>> responseTypePage = new ParameterizedTypeReference<ResponsePage<LoanDto>>() {
    };

    @Test
    public void findFirstPageWithoutFilterWithFiveSizeShouldReturnFirstFiveResults() {

        LoanSearchDto searchDto = new LoanSearchDto();
        searchDto.setPageable(new PageableRequest(0, PAGE_SIZE));

        ResponseEntity<ResponsePage<LoanDto>> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH,
                HttpMethod.POST, new HttpEntity<>(searchDto), responseTypePage);

        assertNotNull(response);
        assertEquals(TOTAL_LOANS, response.getBody().getTotalElements());
        assertEquals(PAGE_SIZE, response.getBody().getContent().size());
    }

    @Test
    public void findSecondPageWithoutFilterWithFiveSizeShouldReturnLastResult() {

        int elementsCount = TOTAL_LOANS - PAGE_SIZE;

        LoanSearchDto searchDto = new LoanSearchDto();
        searchDto.setPageable(new PageableRequest(1, PAGE_SIZE));

        ResponseEntity<ResponsePage<LoanDto>> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH,
                HttpMethod.POST, new HttpEntity<>(searchDto), responseTypePage);

        assertNotNull(response);
        assertEquals(TOTAL_LOANS, response.getBody().getTotalElements());
        assertEquals(elementsCount, response.getBody().getContent().size());
    }

    @Test
    public void findExistsGameShouldReturnLoans() {

        int LOANS_WITH_FILTER = 1;

        LoanSearchDto searchDto = new LoanSearchDto();
        searchDto.setPageable(new PageableRequest(0, PAGE_SIZE));
        searchDto.setGameId(GAME_ID);

        ResponseEntity<ResponsePage<LoanDto>> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH,
                HttpMethod.POST, new HttpEntity<>(searchDto), responseTypePage);

        assertNotNull(response);
        assertEquals(LOANS_WITH_FILTER, response.getBody().getTotalElements());
    }

    @Test
    public void findExistsClientShouldReturnLoans() {

        int LOANS_WITH_FILTER = 1;

        LoanSearchDto searchDto = new LoanSearchDto();
        searchDto.setPageable(new PageableRequest(0, PAGE_SIZE));
        searchDto.setClientId(CLIENT_ID);

        ResponseEntity<ResponsePage<LoanDto>> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH,
                HttpMethod.POST, new HttpEntity<>(searchDto), responseTypePage);

        assertNotNull(response);
        assertEquals(LOANS_WITH_FILTER, response.getBody().getTotalElements());
    }

    @Test
    public void findExistsDateShouldReturnLoans() {

        int LOANS_WITH_FILTER = 3;

        LoanSearchDto searchDto = new LoanSearchDto();
        searchDto.setPageable(new PageableRequest(0, PAGE_SIZE));
        searchDto.setDate(DATE);

        ResponseEntity<ResponsePage<LoanDto>> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH,
                HttpMethod.POST, new HttpEntity<>(searchDto), responseTypePage);

        assertNotNull(response);
        assertEquals(LOANS_WITH_FILTER, response.getBody().getTotalElements());
    }

    @Test
    public void findExistsGameAndClientShouldReturnLoans() {

        int LOANS_WITH_FILTER = 1;

        LoanSearchDto searchDto = new LoanSearchDto();
        searchDto.setPageable(new PageableRequest(0, PAGE_SIZE));
        searchDto.setGameId(GAME_ID - 2);
        searchDto.setClientId(CLIENT_ID);

        ResponseEntity<ResponsePage<LoanDto>> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH,
                HttpMethod.POST, new HttpEntity<>(searchDto), responseTypePage);

        assertNotNull(response);
        assertEquals(LOANS_WITH_FILTER, response.getBody().getTotalElements());
    }

    @Test
    public void findExistsGameAndDateShouldReturnLoans() {

        int LOANS_WITH_FILTER = 1;

        LoanSearchDto searchDto = new LoanSearchDto();
        searchDto.setPageable(new PageableRequest(0, PAGE_SIZE));
        searchDto.setGameId(GAME_ID - 1);
        searchDto.setDate(DATE);

        ResponseEntity<ResponsePage<LoanDto>> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH,
                HttpMethod.POST, new HttpEntity<>(searchDto), responseTypePage);

        assertNotNull(response);
        assertEquals(LOANS_WITH_FILTER, response.getBody().getTotalElements());
    }

    @Test
    public void findExistsClientAndDateShouldReturnLoans() {

        int LOANS_WITH_FILTER = 1;

        LoanSearchDto searchDto = new LoanSearchDto();
        searchDto.setPageable(new PageableRequest(0, PAGE_SIZE));
        searchDto.setClientId(CLIENT_ID);
        searchDto.setDate(DATE);

        ResponseEntity<ResponsePage<LoanDto>> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH,
                HttpMethod.POST, new HttpEntity<>(searchDto), responseTypePage);

        assertNotNull(response);
        assertEquals(LOANS_WITH_FILTER, response.getBody().getTotalElements());
    }

    @Test
    public void findExistsGameAndClientAndDateShouldReturnLoans() {

        int LOANS_WITH_FILTER = 1;

        LoanSearchDto searchDto = new LoanSearchDto();
        searchDto.setPageable(new PageableRequest(0, PAGE_SIZE));
        searchDto.setGameId(GAME_ID - 2);
        searchDto.setClientId(CLIENT_ID);
        searchDto.setDate(DATE);

        ResponseEntity<ResponsePage<LoanDto>> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH,
                HttpMethod.POST, new HttpEntity<>(searchDto), responseTypePage);

        assertNotNull(response);
        assertEquals(LOANS_WITH_FILTER, response.getBody().getTotalElements());
    }

    @Test
    public void findNotExistsGameShouldReturnNothing() {

        int LOANS_WITH_FILTER = 0;

        LoanSearchDto searchDto = new LoanSearchDto();
        searchDto.setPageable(new PageableRequest(0, PAGE_SIZE));
        searchDto.setGameId(GAME_ID + 3);

        ResponseEntity<ResponsePage<LoanDto>> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH,
                HttpMethod.POST, new HttpEntity<>(searchDto), responseTypePage);

        assertNotNull(response);
        assertEquals(LOANS_WITH_FILTER, response.getBody().getTotalElements());
    }

    @Test
    public void findNotExistsClientShouldReturnNothing() {

        int LOANS_WITH_FILTER = 0;

        LoanSearchDto searchDto = new LoanSearchDto();
        searchDto.setPageable(new PageableRequest(0, PAGE_SIZE));
        searchDto.setClientId(CLIENT_ID + 1);

        ResponseEntity<ResponsePage<LoanDto>> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH,
                HttpMethod.POST, new HttpEntity<>(searchDto), responseTypePage);

        assertNotNull(response);
        assertEquals(LOANS_WITH_FILTER, response.getBody().getTotalElements());
    }

    @Test
    public void findNotExistsDateShouldReturnNothing() {

        int LOANS_WITH_FILTER = 0;

        LoanSearchDto searchDto = new LoanSearchDto();
        searchDto.setPageable(new PageableRequest(0, PAGE_SIZE));
        searchDto.setDate(NOT_EXIST_DATE);

        ResponseEntity<ResponsePage<LoanDto>> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH,
                HttpMethod.POST, new HttpEntity<>(searchDto), responseTypePage);

        assertNotNull(response);
        assertEquals(LOANS_WITH_FILTER, response.getBody().getTotalElements());
    }

    @Test
    public void saveWithoutIdShouldCreateNewAuthor() {

        long newLoanId = TOTAL_LOANS + 1;
        long newLoanSize = TOTAL_LOANS + 1;

        LoanDto dto = new LoanDto();

        GameDto gameDto = new GameDto();
        gameDto.setId(GAME_ID);

        ClientDto clientDto = new ClientDto();
        clientDto.setId(CLIENT_ID);

        dto.setGame(gameDto);
        dto.setClient(clientDto);
        dto.setStartDate(START_DATE);
        dto.setEndDate(END_DATE);

        restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.PUT, new HttpEntity<>(dto), Void.class);

        LoanSearchDto searchDto = new LoanSearchDto();
        searchDto.setPageable(new PageableRequest(0, (int) newLoanSize));

        ResponseEntity<ResponsePage<LoanDto>> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH,
                HttpMethod.POST, new HttpEntity<>(searchDto), responseTypePage);

        assertNotNull(response);
        assertEquals(newLoanSize, response.getBody().getTotalElements());

        LoanDto loan = response.getBody().getContent().stream().filter(item -> item.getId().equals(newLoanId))
                .findFirst().orElse(null);
        assertNotNull(loan);
        assertEquals(START_DATE, loan.getStartDate());
    }

    @Test
    public void saveWithGameLoanShouldThrowException() {

        LoanDto dto = new LoanDto();

        GameDto gameDto = new GameDto();
        gameDto.setId(GAME_ID);

        ClientDto clientDto = new ClientDto();
        clientDto.setId(CLIENT_ID);

        dto.setGame(gameDto);
        dto.setClient(clientDto);
        dto.setStartDate(GAME_START_DATE);
        dto.setEndDate(GAME_END_DATE);

        ResponseEntity<?> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.PUT,
                new HttpEntity<>(dto), Void.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void saveWithClientLoanShouldThrowException() {

        LoanDto dto = new LoanDto();

        GameDto gameDto = new GameDto();
        gameDto.setId(GAME_ID);

        ClientDto clientDto = new ClientDto();
        clientDto.setId(CLIENT_ID);

        dto.setGame(gameDto);
        dto.setClient(clientDto);
        dto.setStartDate(CLIENT_START_DATE);
        dto.setEndDate(CLIENT_END_DATE);

        ResponseEntity<?> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.PUT,
                new HttpEntity<>(dto), Void.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void saveWithEndDateBeforeStartDateShouldThrowException() {

        LoanDto dto = new LoanDto();

        GameDto gameDto = new GameDto();
        gameDto.setId(GAME_ID);

        ClientDto clientDto = new ClientDto();
        clientDto.setId(CLIENT_ID);

        dto.setGame(gameDto);
        dto.setClient(clientDto);
        dto.setStartDate(END_DATE);
        dto.setEndDate(START_DATE);

        ResponseEntity<?> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.PUT,
                new HttpEntity<>(dto), Void.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void saveWithMoreThanFourteenDaysShouldThrowException() {

        LoanDto dto = new LoanDto();

        GameDto gameDto = new GameDto();
        gameDto.setId(GAME_ID);

        ClientDto clientDto = new ClientDto();
        clientDto.setId(CLIENT_ID);

        dto.setGame(gameDto);
        dto.setClient(clientDto);
        dto.setStartDate(GAME_START_DATE);
        dto.setEndDate(END_DATE);

        ResponseEntity<?> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.PUT,
                new HttpEntity<>(dto), Void.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void deleteWithExistsIdShouldDeleteLoan() {

        long newloansSize = TOTAL_LOANS - 1;

        restTemplate.exchange(LOCALHOST + port + SERVICE_PATH + "/" + DELETE_LOAN_ID, HttpMethod.DELETE, null,
                Void.class);

        AuthorSearchDto searchDto = new AuthorSearchDto();
        searchDto.setPageable(new PageableRequest(0, TOTAL_LOANS));

        ResponseEntity<ResponsePage<LoanDto>> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH,
                HttpMethod.POST, new HttpEntity<>(searchDto), responseTypePage);

        assertNotNull(response);
        assertEquals(newloansSize, response.getBody().getTotalElements());
    }

    @Test
    public void deleteWithNotExistsIdShouldThrowException() {

        long deleteLoanId = TOTAL_LOANS + 1;

        ResponseEntity<?> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH + "/" + deleteLoanId,
                HttpMethod.DELETE, null, Void.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

}
