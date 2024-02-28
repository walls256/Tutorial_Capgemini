import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DialogExceptionComponent } from './dialog-exception.component';

describe('DialogExceptionComponent', () => {
  let component: DialogExceptionComponent;
  let fixture: ComponentFixture<DialogExceptionComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DialogExceptionComponent]
    });
    fixture = TestBed.createComponent(DialogExceptionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
