package com.ccsw.tutorial.loan;

import org.springframework.data.domain.Page;

import com.ccsw.tutorial.loan.model.Loan;
import com.ccsw.tutorial.loan.model.LoanDto;
import com.ccsw.tutorial.loan.model.LoanSearchDto;

public interface LoanService {
    /**
     * Recupera un {@link Loan} a través de su ID
     *
     * @param id PK de la entidad
     * @return {@link Loan}
     */
    Loan get(Long id);

    /**
     * Método para recuperar un listado paginado de {@link Loan}
     *
     * @param dto dto de búsqueda
     * @return {@link Page} de {@link Loan}
     */
    Page<Loan> findPage(LoanSearchDto dto);

    /**
     * Método para crear o actualizar un {@link Loan}
     *
     * @param dto datos de la entidad
     */
    void save(LoanDto dto) throws Exception;

    /**
     * Método para crear o actualizar un {@link Loan}
     *
     * @param id PK de la entidad
     */
    void delete(Long id) throws Exception;

    /**
     * Método para verificar si un cliente tiene un préstamo en las fechas
     * seleccionadas. {@link Loan}
     * 
     * @param dto datos de la entidad
     * @return true si el cliente tiene un préstamo en las fechas seleccionadas,
     *         false de lo contrario
     */

    boolean hasLoanForClientInSelectedDates(LoanDto dto);

    /**
     * Método para verificar si un juego está prestado en las fechas seleccionadas.
     * {@link Loan}
     * 
     * @param dto datos de la entidad
     * @return true si el juego está prestado en las fechas seleccionadas, false de
     *         lo contrario
     */

    boolean hasLoanForGameInSelectedDates(LoanDto dto);

}
