package com.ccsw.tutorial.loan.model;

import java.time.LocalDate;

import com.ccsw.tutorial.common.pagination.PageableRequest;

public class LoanSearchDto {

    private PageableRequest pageable;
    private Long gameId;
    private Long clientId;
    private LocalDate date;

    public Long getGameId() {
        return this.gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    public Long getClientId() {
        return this.clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public PageableRequest getPageable() {
        return this.pageable;
    }

    public void setPageable(PageableRequest pageable) {
        this.pageable = pageable;
    }
}