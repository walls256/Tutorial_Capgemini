package com.ccsw.tutorial.loan;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.ccsw.tutorial.client.ClientService;
import com.ccsw.tutorial.client.model.Client;
import com.ccsw.tutorial.client.model.ClientDto;
import com.ccsw.tutorial.common.criteria.SearchCriteria;
import com.ccsw.tutorial.game.GameService;
import com.ccsw.tutorial.game.model.Game;
import com.ccsw.tutorial.game.model.GameDto;
import com.ccsw.tutorial.loan.model.Loan;
import com.ccsw.tutorial.loan.model.LoanDto;
import com.ccsw.tutorial.loan.model.LoanSearchDto;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class LoanServiceImpl implements LoanService {

    @Autowired
    LoanRepository loanRepository;

    @Autowired
    ClientService clientService;

    @Autowired
    GameService gameService;

    /**
     * {@inheritDoc}
     */
    @Override
    public Loan get(Long id) {

        return this.loanRepository.findById(id).orElse(null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<Loan> findPage(LoanSearchDto dto) {

        LoanSpecification gameSpec = new LoanSpecification(new SearchCriteria("game.id", ":", dto.getGameId()));
        LoanSpecification clientSpec = new LoanSpecification(new SearchCriteria("client.id", ":", dto.getClientId()));
        LoanSpecification startDateSpec = new LoanSpecification(new SearchCriteria("startDate", ">=", dto.getDate()));
        LoanSpecification endDateSpec = new LoanSpecification(new SearchCriteria("endDate", "<=", dto.getDate()));

        Specification<Loan> spec = Specification.where(gameSpec).and(clientSpec).and(startDateSpec).and(endDateSpec);
        return this.loanRepository.findAll(spec, dto.getPageable().getPageable());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void save(LoanDto data) throws Exception {

        LocalDate loanStartDate = data.getStartDate();
        LocalDate loanEndDate = data.getEndDate();

        if (loanEndDate.isBefore(loanStartDate)) {
            throw new Exception("La fecha de finalización no puede ser anterior a la fecha de inicio");

        }

        Long daysDifference = ChronoUnit.DAYS.between(loanStartDate, loanEndDate);

        if (daysDifference > 14) {
            throw new Exception("Se superan los 14 días para el préstamo");
        }

        if (hasLoanForGameInSelectedDates(data)) {
            throw new Exception("El juego ya está prestado en las fechas seleccionadas");
        }

        if (hasLoanForClientInSelectedDates(data)) {
            throw new Exception("El cliente ya tiene un préstamo en las fechas seleccionadas");
        }

        Loan loan = new Loan();

        BeanUtils.copyProperties(data, loan, "id", "game", "client");
        loan.setClient(clientService.get(data.getClient().getId()));
        loan.setGame(gameService.get(data.getGame().getId()));
        this.loanRepository.save(loan);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(Long id) throws Exception {

        if (this.get(id) == null) {
            throw new Exception("Not exists");
        }

        this.loanRepository.deleteById(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasLoanForClientInSelectedDates(LoanDto data) {
        boolean hasLoan = false;

        Iterable<Loan> listLoan = loanRepository.findAll();
        LocalDate selectedStartDate = data.getStartDate();
        LocalDate selectedEndDate = data.getEndDate();
        ClientDto clientDto = data.getClient();
        Long clientDtoId = clientDto.getId();

        for (Loan loan : listLoan) {

            Client client = loan.getClient();

            if (client.getId().equals(clientDtoId)) {

                LocalDate loanStartDate = loan.getStartDate();
                LocalDate loanEndDate = loan.getEndDate();

                if (loanStartDate.equals(selectedStartDate) || loanEndDate.equals(selectedEndDate)) {
                    hasLoan = true;
                    break;
                }

                if (loanStartDate.isBefore(selectedEndDate) && loanEndDate.isAfter(selectedStartDate)) {
                    hasLoan = true;
                    break;
                }
            }

        }

        return hasLoan;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasLoanForGameInSelectedDates(LoanDto data) {
        boolean hasLoan = false;

        Iterable<Loan> listLoan = loanRepository.findAll();
        LocalDate selectedStartDate = data.getStartDate();
        LocalDate selectedEndDate = data.getEndDate();
        GameDto gameDto = data.getGame();
        Long gameDtoId = gameDto.getId();

        for (Loan loan : listLoan) {

            Game game = loan.getGame();

            if (game.getId().equals(gameDtoId)) {

                LocalDate loanStartDate = loan.getStartDate();
                LocalDate loanEndDate = loan.getEndDate();

                if (loanStartDate.equals(selectedStartDate) || loanEndDate.equals(selectedEndDate)) {
                    hasLoan = true;
                    break;
                }

                if (loanStartDate.isBefore(selectedEndDate) && loanEndDate.isAfter(selectedStartDate)) {
                    hasLoan = true;
                    break;
                }
            }

        }

        return hasLoan;
    }

}