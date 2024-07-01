import { Component } from '@angular/core';

@Component({
  selector: 'app-fundamentals',
  templateUrl: './fundamentals.component.html',
  styleUrl: './fundamentals.component.less'
})
export class FundamentalsComponent {

  compute(number: number) {
    if (number < 0){
      return 0; 
    }else{
      return number + 1;
    }

  }

}
