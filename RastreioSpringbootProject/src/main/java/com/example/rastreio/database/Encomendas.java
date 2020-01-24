/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.rastreio.database;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author david
 */
@Entity
@Table(name = "encomendas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Encomendas.findAll", query = "SELECT e FROM Encomendas e"),
    @NamedQuery(name = "Encomendas.findByEncoId", query = "SELECT e FROM Encomendas e WHERE e.encoId = :encoId"),
    @NamedQuery(name = "Encomendas.findByCodigoRastreio", query = "SELECT e FROM Encomendas e WHERE e.codigoRastreio = :codigoRastreio")})
public class Encomendas implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "enco_id")
    private Integer encoId;
    @Basic(optional = false)
    @Column(name = "codigo_rastreio")
    private String codigoRastreio;

    public Encomendas() {
    }

    public Encomendas(Integer encoId) {
        this.encoId = encoId;
    }

    public Encomendas(Integer encoId, String codigoRastreio) {
        this.encoId = encoId;
        this.codigoRastreio = codigoRastreio;
    }

    public Integer getEncoId() {
        return encoId;
    }

    public void setEncoId(Integer encoId) {
        this.encoId = encoId;
    }

    public String getCodigoRastreio() {
        return codigoRastreio;
    }

    public void setCodigoRastreio(String codigoRastreio) {
        this.codigoRastreio = codigoRastreio;
    }


    @Override
    public int hashCode() {
        int hash = 0;
        hash += (encoId != null ? encoId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Encomendas)) {
            return false;
        }
        Encomendas other = (Encomendas) object;
        if ((this.encoId == null && other.encoId != null) || (this.encoId != null && !this.encoId.equals(other.encoId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.example.rastreio.database.Encomendas[ encoId=" + encoId + " ]";
    }
    
}
