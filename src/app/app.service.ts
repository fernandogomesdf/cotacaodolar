import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map, share } from 'rxjs/operators';
import { AlertController } from '@ionic/angular';

@Injectable({
  providedIn: 'root'
})
export class AppService {

  public showProgress = false;

  constructor(private http: HttpClient, public alertController: AlertController) { }

  getServerHostPort() {
    let serverHostPort = location.protocol + '//localhost:8080';
    if ('8100' !== location.port && '4200' !== location.port) { // local
      serverHostPort = location.protocol + '//' + location.hostname + ':' + location.port;
    }
    return serverHostPort;
  }

  private chamarUrl(url: string, data: any, verbo: VerboHttp): Observable<any> {
    const token = sessionStorage.getItem('token');
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': typeof data == 'object' ? 'application/json' : 'text/plain',
        Authorization: 'Bearer ' + token
      }),
      body: data
    };
    switch (verbo) {
      case VerboHttp.PUT:
        return this.http.put(url, data, httpOptions);
      case VerboHttp.GET:
        return this.http.get(url, httpOptions);
      case VerboHttp.POST:
        return this.http.post(url, data, httpOptions);
      case VerboHttp.PATCH:
        return this.http.patch(url, data, httpOptions);
      case VerboHttp.DELETE:
        return this.http.delete(url, httpOptions);
      default:
        break;
    }
  }

  request(url: string, data: any, verbo: VerboHttp): Observable<any> {
    this.showProgress = true;
    const response$ = this.chamarUrl(this.getServerHostPort() + url.replace('./', '/'), data, verbo).pipe(
      map(response => response),
      share()
    );
    response$.subscribe({
      error: (err) => {
        this.tratarErro(err);
        this.showProgress = false;
      },
      complete: () => {
        this.showProgress = false;
      }
    });
    return response$;
  }

  tratarErro(err) {
    try {
      if (err.error.type === 'error') {
        this.alert('Não foi possí­vel conectar no servidor.');
      } else {
        const resposta = err.error;
        if (resposta.status === 500 || err.status === 500) {
          this.alert(resposta.message);
        } else if (resposta.status === 400 || err.status === 400) {
          if (resposta.violations) {
            this.alert(resposta.violations[0].message);
          }
          if (resposta.message) {
            this.alert(resposta.message);
          }
        }
        if (resposta.exception) {
          console.log(resposta.exception);
        }
      }
    } catch (erro) {
      console.log(erro);
    }
  }

  async alert(msg) {
    const alert = await this.alertController.create({
      header: 'Alerta',
      message: msg,
      buttons: ['OK']
    });

    await alert.present();
  }
}

export enum VerboHttp {
  PUT,
  GET,
  POST,
  PATCH,
  DELETE
}
