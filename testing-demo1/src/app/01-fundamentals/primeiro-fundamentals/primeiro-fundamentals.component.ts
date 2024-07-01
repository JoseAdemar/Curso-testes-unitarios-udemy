import { Component } from '@angular/core';

@Component({
  selector: 'app-primeiro-fundamentals',
  standalone: true,
  imports: [],
  templateUrl: './primeiro-fundamentals.component.html',
  styleUrl: './primeiro-fundamentals.component.css'
})
export class PrimeiroFundamentalsComponent {

  compute(number: number) {
    if (number < 0)
      return 0; 
  
    return number + 1;
  }
  

}
