import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-home',
  templateUrl: './home.page.html',
  styleUrls: ['./home.page.scss'],
})
export class HomePage implements OnInit {

  constructor() { }

  ngOnInit() {
  }

  pesquisarCotacao(value) {
    console.log('Pesquisar cotação', new Date(value));
  }

  //http://localhost:8080/cotacao

}
