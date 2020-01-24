import { Component } from '@angular/core';
import { FormGroup, FormControl } from '@angular/forms';
import { ApiService } from './api.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {

  form = new FormGroup({
    codigo: new FormControl('')
  });

  message = 'O código deve ser informado.';

  messageError;
  response = [];

  constructor(private api: ApiService) {

  }

  /**
   * Função que é chamada automaticamente quando o usuário pressina enter, o angular entende que o usuário está submetendo o formulario.
   * Essa funsão está associada no arquivo html (ngSubmit)="submit()"
   *
   * @memberof AppComponent
   */
  submit() {
    if (this.form.valid) {

      this.api.getOcorrencias(this.form.value.codigo).subscribe((data: any) => {
        this.messageError = null;
        this.response = [];

        // se existir esse propriedade, significa que o servidor retornou um erro.
        if (data.hasOwnProperty('error')) {
          // assinala o error para a variavel messageError que o angular irá renderizar na tela
          this.messageError = data.error;
        } else {
          // assinala o resultado para a variavel this.response que o angular irá renderizar na tela
          this.response = data;
        }

      });
    }
  }

}
