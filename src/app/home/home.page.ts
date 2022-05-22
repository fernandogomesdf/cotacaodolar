import { Component, OnInit } from '@angular/core';
import { AppService, VerboHttp } from '../app.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.page.html',
  styleUrls: ['./home.page.scss'],
})
export class HomePage implements OnInit {

  constructor(private appService: AppService) { }

  ngOnInit() {
  }

  pesquisarCotacao(value) {
    this.appService.request('./cotacao', { data: value }, VerboHttp.POST).subscribe(
      response => {
        console.log(response);
      }
    );
  }

  //http://localhost:8080/cotacao

}
