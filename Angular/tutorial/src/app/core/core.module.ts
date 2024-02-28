import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatIconModule } from '@angular/material/icon';
import { MatToolbarModule } from '@angular/material/toolbar';
import { HeaderComponent } from './header/header.component';
import { RouterModule } from '@angular/router';
import { DialogConfirmationComponent } from './dialog-confirmation/dialog-confirmation.component';
import { MatDialogModule, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatButtonModule } from '@angular/material/button';
import { DialogExceptionComponent } from './dialog-exception/dialog-exception.component';

@NgModule({
  declarations: [
    HeaderComponent,
    DialogConfirmationComponent,
    DialogExceptionComponent,
  ],
  imports: [
    CommonModule,
    RouterModule,
    MatIconModule, 
    MatToolbarModule,
    MatDialogModule,
    MatButtonModule,
  ],
  providers: [
    {
      provide: MAT_DIALOG_DATA,
      useValue: {},
    }
  ],
  exports: [
    HeaderComponent
  ]
})
export class CoreModule { }
