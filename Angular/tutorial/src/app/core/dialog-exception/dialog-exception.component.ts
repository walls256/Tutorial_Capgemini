import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-dialog-exception',
  templateUrl: './dialog-exception.component.html',
  styleUrls: ['./dialog-exception.component.scss']
})
export class DialogExceptionComponent {
  constructor(    
    @Inject(MAT_DIALOG_DATA) public data: any,
    public dialogRef: MatDialogRef<DialogExceptionComponent>
    ) {}

  closeDialog(): void {
    this.dialogRef.close();
  }
}