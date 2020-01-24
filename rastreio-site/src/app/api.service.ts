import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';

/**
 *Serviço responsável pela comunicao entre a aplicação angular e o servidor springboot
 *
 * @export
 * @class ApiService
 */
@Injectable({
  providedIn: 'root'
})
export class ApiService {

  localUrl = 'http://localhost:8080';

  constructor(private http: HttpClient) { }

  /**
   * Função que faz a requisição do tipo get para o servidor Springboot, contendo um parametro código do tipo string.
   *
   * @param {string} codigoParam
   * @returns
   * @memberof ApiService
   */
  getOcorrencias(codigoParam: string) {

    const params = new HttpParams()
      .set('codigo', codigoParam);

    return this.http.get(this.localUrl, { params });
  }


}
