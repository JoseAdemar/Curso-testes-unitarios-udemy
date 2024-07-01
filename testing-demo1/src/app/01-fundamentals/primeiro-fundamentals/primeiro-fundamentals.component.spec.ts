import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PrimeiroFundamentalsComponent } from './primeiro-fundamentals.component';
import { AppComponent } from '../../app.component';

describe('PrimeiroFundamentalsComponent', () => {
  let component: PrimeiroFundamentalsComponent;
  let fixture: ComponentFixture<PrimeiroFundamentalsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PrimeiroFundamentalsComponent],
      
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(PrimeiroFundamentalsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
