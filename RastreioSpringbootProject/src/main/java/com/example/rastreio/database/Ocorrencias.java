/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.rastreio.database;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author david
 */
@Entity
@Table(name = "ocorrencias")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ocorrencias.findAll", query = "SELECT o FROM Ocorrencias o"),
    @NamedQuery(name = "Ocorrencias.findByOcoId", query = "SELECT o FROM Ocorrencias o WHERE o.ocoId = :ocoId"),
    @NamedQuery(name = "Ocorrencias.findByEncomenda", query = "SELECT o FROM Ocorrencias o WHERE o.encoId = :enco_id ORDER BY o.data")})
public class Ocorrencias implements Serializable {

    @Basic(optional = false)
    @Column(name = "data")
    @Temporal(TemporalType.TIMESTAMP)
    private Date data;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "oco_id")
    private Integer ocoId;
    @Basic(optional = false)
    @Column(name = "ocorrencia")
    private String ocorrencia;
    @JoinColumn(name = "enco_id", referencedColumnName = "enco_id")
    @ManyToOne
    private Encomendas encoId;

    public Ocorrencias() {
    }

    public Ocorrencias(Integer ocoId) {
        this.ocoId = ocoId;
    }

    public Ocorrencias(Integer ocoId, String ocorrencia) {
        this.ocoId = ocoId;
        this.ocorrencia = ocorrencia;
    }

    public Integer getOcoId() {
        return ocoId;
    }

    public void setOcoId(Integer ocoId) {
        this.ocoId = ocoId;
    }

    public String getOcorrencia() {
        return ocorrencia;
    }

    public void setOcorrencia(String ocorrencia) {
        this.ocorrencia = ocorrencia;
    }

    public Encomendas getEncoId() {
        return encoId;
    }

    public void setEncoId(Encomendas encoId) {
        this.encoId = encoId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ocoId != null ? ocoId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ocorrencias)) {
            return false;
        }
        Ocorrencias other = (Ocorrencias) object;
        if ((this.ocoId == null && other.ocoId != null) || (this.ocoId != null && !this.ocoId.equals(other.ocoId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.example.rastreio.database.Ocorrencias[ ocoId=" + ocoId + " ]";
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }
    
}
