/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.rastreio;

import com.example.rastreio.database.Encomendas;
import com.example.rastreio.database.EncomendasJpaController;
import com.example.rastreio.database.Ocorrencias;
import com.example.rastreio.database.OcorrenciasJpaController;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Classe controller que recebe todas as requisições para tratamento
 * @author david
 */
@RestController
public class RequestController {

    EncomendasJpaController encomendas = new EncomendasJpaController(getEntityManager());
    OcorrenciasJpaController ocorrencias = new OcorrenciasJpaController(getEntityManager());
    
    /*
    Busca as ocorrencias na base de dados utilizando JPA.
    Recebe o parametro que é o código da encomenda pela anotação @RequestParam do Springboot
    Se não encontrado nenhuma encomenda com o codigo enviado, então joga-se uma RunTimeException contendo um mensagem
    que será interpretada na função contendo a notação @ExceptionHandler(RuntimeException.class).
    Caso há ocorrencias retona as mesmas para o Springboot criar o Json
    A notação @CrossOrigin é necessario para acessos aos dados retornar, caso contrario o proprio navegador irá interceptá-las.
    A notação @RequestMapping indica que todas as requisições que chegarem no endereço / raiz deverão ser tratados por esta função.
    */
    @CrossOrigin(origins = "*")
    @RequestMapping("/")
    public List<Ocorrencias> getOcorrencias(@RequestParam String codigo) {
        List<Encomendas> listaEncomendas = encomendas.findEncomendas(codigo);
        
        if(listaEncomendas.size() <= 0){
            throw new RuntimeException("{ \"error\" : \" Encomenda com o código "+codigo+" não encontrada.\"}");
        }
        
        List<Ocorrencias> Ocorrencias = ocorrencias.findOcorrencias(listaEncomendas.get(0));
        
        return Ocorrencias;

    }
    
    /*
    Trata as RuntimeException que são lançadas para o Springboot
    */
    @ExceptionHandler(RuntimeException.class)
    public String errorHandler(RuntimeException exception){
        return exception.getMessage();
    }

    /*
    Cria EntityManagerFactory onde contem as informações para acessas a base de dados,
    arquivo persistence.xml
    */
    public EntityManagerFactory getEntityManager() {
        return Persistence.createEntityManagerFactory("rastreio_persistence");
    }
}
