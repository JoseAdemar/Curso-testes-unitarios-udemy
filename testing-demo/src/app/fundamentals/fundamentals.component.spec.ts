import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FundamentalsComponent } from './fundamentals.component';

describe('FundamentalsComponent', () => {
  let component: FundamentalsComponent;
  let fixture: ComponentFixture<FundamentalsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [FundamentalsComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(FundamentalsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should return 0 case negative number', () =>{
      const fundamentals =  new FundamentalsComponent();
      const result = fundamentals.compute(-10);
      expect(result).toBe(0);
  });

  it('should return a number case positive number', () =>{
    const fundamentals =  new FundamentalsComponent();
    const result = fundamentals.compute(11);
    expect(result).toBe(12);
});
});
