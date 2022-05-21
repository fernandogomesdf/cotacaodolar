import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map, share } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AppService {

  public showProgress = false;

  constructor(private http: HttpClient) { }

  getLocalServerHostPort() {
    return '//' + location.hostname + ':' + location.port;
  }

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
    const response$ = this.chamarUrl(url.startsWith('./')
      ? (this.getLocalServerHostPort() + url.replace('./', '/'))
      : this.getServerHostPort() + url, data, verbo).pipe(
        map(response => response),
        share()
      );
    response$.subscribe({
      error: (err) => {
        //this.tratarErro(err);
        this.showProgress = false;
      },
      complete: () => {
        this.showProgress = false;
      }
    });
    return response$;
  }
}

export enum VerboHttp {
  PUT,
  GET,
  POST,
  PATCH,
  DELETE
}
