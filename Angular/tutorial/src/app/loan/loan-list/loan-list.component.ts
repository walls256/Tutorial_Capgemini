import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { PageEvent } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { Pageable } from 'src/app/core/model/page/Pageable';
import { DialogConfirmationComponent } from 'src/app/core/dialog-confirmation/dialog-confirmation.component';
import { LoanEditComponent } from '../loan-edit/loan-edit.component';
import { LoanService } from '../loan.service';
import { Loan } from '../model/Loan';
import { Client } from 'src/app/client/model/Client';
import { ClientService } from 'src/app/client/client.service';
import { Game } from 'src/app/game/model/Game';
import { GameService } from 'src/app/game/game.service';

@Component({
  selector: 'app-loan-list',
  templateUrl: './loan-list.component.html',
  styleUrls: ['./loan-list.component.scss']
})
export class LoanListComponent implements OnInit{

  loans : Loan[];
  games : Game[];
  clients : Client[];
  filterTitle : Game;
  filterClient : Client;
  filterDate : Date;

  pageNumber : number = 0;
  pageSize: number = 5;
  totalElements: number = 0;

  dataSource = new MatTableDataSource<Loan>();
  displayedColumns: string[] = ['id', 'game', 'client', 'startDate', 'endDate', 'action'];

  errorMessage: string;

  constructor(
    private loanService: LoanService,
    private gameService: GameService,
    private clientService: ClientService,
    public dialog: MatDialog,
  ) {}

  ngOnInit(): void {
    this.loadPage();
    this.gameService.getGames().subscribe(games => {
      this.games = games;
    });
  
    this.clientService.getClients().subscribe(clients => {
      this.clients = clients;
    });
  }

  onCleanFilter(): void {
    this.filterTitle = null;
    this.filterClient = null;
    this.filterDate = null;

    this.onSearch();
  }

  onSearch(): void {

    let pageable : Pageable =  {
      pageNumber: this.pageNumber,
      pageSize: this.pageSize,
      sort: [{
        property: 'id',
        direction: 'ASC'
      }]
    }

    let gameId = this.filterTitle != null ? this.filterTitle.id : null;
    let clientId = this.filterClient != null ? this.filterClient.id : null;
    let date = this.filterDate;
   
    if (date) {
      date = new Date(date.getTime() + 2 * 60 * 60 * 1000); // Sumar dos horas en milisegundos
    }

    this.loanService.getLoans(pageable, gameId, clientId, date).subscribe(
      loanPage => {
        this.dataSource.data = loanPage.content;
        this.pageNumber = loanPage.pageable.pageNumber;
        this.pageSize = loanPage.pageable.pageSize;
        this.totalElements = loanPage.totalElements;
      }      
    );
  }

  loadPage(event?: PageEvent) {

    let pageable : Pageable =  {
        pageNumber: this.pageNumber,
        pageSize: this.pageSize,
        sort: [{
            property: 'id',
            direction: 'ASC'
        }]
    }

    if (event != null) {
        pageable.pageSize = event.pageSize
        pageable.pageNumber = event.pageIndex;
    }

    this.loanService.getLoans(pageable).subscribe(data => {
        this.dataSource.data = data.content;
        this.pageNumber = data.pageable.pageNumber;
        this.pageSize = data.pageable.pageSize;
        this.totalElements = data.totalElements;
    });

  }

  createLoan() {      
    const dialogRef = this.dialog.open(LoanEditComponent, {
      data: {}
    });

    dialogRef.afterClosed().subscribe(result => {
      this.ngOnInit();
    });   
  }

  deleteLoan(loan: Loan) {    
    const dialogRef = this.dialog.open(DialogConfirmationComponent, {
        data: { title: "Eliminar prestamo", description: "Atención si borra el préstamo se perderán sus datos.<br> ¿Desea eliminar el préstamo?" }
    });

    dialogRef.afterClosed().subscribe(result => {
        if (result) {
            this.loanService.deleteLoan(loan.id).subscribe(result =>  {
                this.ngOnInit();
            }); 
        }
    });
  }

}