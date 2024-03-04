import { Component , Inject, OnInit} from '@angular/core';
import { MatDialogRef, MatDialog, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ClientService } from 'src/app/client/client.service';
import { Client } from 'src/app/client/model/Client';
import { GameService } from 'src/app/game/game.service';
import { Game } from 'src/app/game/model/Game';
import { LoanService } from '../loan.service';
import { Loan } from '../model/Loan';
import { HttpErrorResponse } from '@angular/common/http';
import { DialogExceptionComponent } from 'src/app/core/dialog-exception/dialog-exception.component';
import { DateAdapter } from '@angular/material/core';


@Component({
  selector: 'app-loan-edit',
  templateUrl: './loan-edit.component.html',
  styleUrls: ['./loan-edit.component.scss']
})
export class LoanEditComponent implements OnInit{
  loan:Loan;
  games: Game[];
  clients: Client[];

  constructor(
    public dialogRef: MatDialogRef<LoanEditComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private loanService: LoanService,
    private gameService: GameService,
    private clientService: ClientService,
    private dialog: MatDialog,
    private dateAdapter: DateAdapter<Date>
  ) {
    this.dateAdapter.getFirstDayOfWeek = () => 1;
  }

  ngOnInit(): void {
    this.loan = new Loan();

    this.gameService.getGames().subscribe(
      games => {
          this.games = games;

          if (this.loan.game != null) {
              let gameFilter: Game[] = games.filter(game => game.id == this.data.loan.game.id);
              if (gameFilter != null) {
                  this.loan.game = gameFilter[0];
              }
          }
      }
    );

    this.clientService.getClients().subscribe(
      clients => {
          this.clients = clients

          if (this.loan.client != null) {
              let clientFilter: Client[] = clients.filter(client => client.id == this.data.loan.client.id);
              if (clientFilter != null) {
                  this.loan.client = clientFilter[0];
              }
          }
      }
    );
  }

  onSave() {

    this.loanService.saveLoan(this.loan).subscribe(
      result => {
        this.dialogRef.close();
      },
      (error: HttpErrorResponse) => {
        if (error.status === 400) {
          const errorMessage = error.error;
          this.openDialogError(errorMessage);
        }
      }
    ); 
  }  

  openDialogError(errorMessage: string): void {
    const dialogRef = this.dialog.open(DialogExceptionComponent, {
      data: { errorMessage: errorMessage }
    });
  }

  onClose() {
    this.dialogRef.close();
  }

}
