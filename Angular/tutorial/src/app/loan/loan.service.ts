import { HttpClient } from '@angular/common/http';
import { Injectable} from '@angular/core';
import { Observable, of  } from 'rxjs';
import { Pageable } from '../core/model/page/Pageable';
import { Loan } from './model/Loan';
import { LoanPage } from './model/LoanPage';
import { LOAN_DATA } from './model/mock-loan';
import { DatePipe } from '@angular/common';

@Injectable({
  providedIn: 'root'
})
export class LoanService {

  constructor(
    private http: HttpClient,
    private datePipe: DatePipe
  ) { }

    getLoans(pageable: Pageable, gameId?: number, clientId?: number, date?: Date): Observable<LoanPage> {
      if (date)
        date= new Date(this.datePipe.transform(date, 'MM-dd-yyyy', 'es-ES') + 'UTC');
      return this.http.post<LoanPage>('http://localhost:8080/loan', {pageable: pageable, gameId: gameId,
      clientId: clientId, date: date } );
    }

    saveLoan(loan: Loan): Observable<void> {
      loan.startDate = new Date(this.datePipe.transform(loan.startDate, 'MM-dd-yyyy', 'es-ES') + 'UTC');
      loan.endDate = new Date(this.datePipe.transform(loan.endDate, 'MM-dd-yyyy', 'es-ES') + 'UTC');
      
      let url = 'http://localhost:8080/loan';
      return this.http.put<void>(url, loan)
    }

    deleteLoan(idLoan : number): Observable<void> {
      return this.http.delete<void>('http://localhost:8080/loan/'+idLoan);
    }
}
