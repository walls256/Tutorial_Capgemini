package com.ccsw.tutorial.loan.model;

import java.time.LocalDateTime;

import com.ccsw.tutorial.client.model.ClientDto;
import com.ccsw.tutorial.game.model.GameDto;

public class LoanDto {

    private Long id;

    private GameDto game;

    private ClientDto client;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    /**
     * @return id
     */
    public Long getId() {

        return this.id;
    }

    /**
     * @param id new value of {@link #getId}.
     */
    public void setId(Long id) {

        this.id = id;
    }

    /**
     * @return game
     */
    public GameDto getGame() {

        return this.game;
    }

    /**
     * @param game new value of {@link #getGame}.
     */
    public void setGame(GameDto game) {

        this.game = game;
    }

    /**
     * @return client
     */
    public ClientDto getClient() {

        return this.client;
    }

    /**
     * @param client new value of {@link #getClient}.
     */
    public void setClient(ClientDto client) {

        this.client = client;
    }

    /**
     * @return start date
     */
    public LocalDateTime getStartDate() {

        return this.startDate;
    }

    /**
     * @param startDate new value of {@link #getStartDate}.
     */
    public void setStartDate(LocalDateTime startDate) {

        this.startDate = startDate;
    }

    /**
     * @return end date
     */
    public LocalDateTime getEndDate() {

        return this.endDate;
    }

    /**
     * @param endDate new value of {@link #getEndDate}.
     */
    public void setEndDate(LocalDateTime endDate) {

        this.endDate = endDate;
    }
}
