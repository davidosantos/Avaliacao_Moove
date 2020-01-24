/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.rastreio.database;

import com.example.rastreio.database.Encomendas;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.example.rastreio.database.Ocorrencias;
import com.example.rastreio.exceptions.NonexistentEntityException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

/**
 *
 * @author david
 */
public class EncomendasJpaController implements Serializable {

    public EncomendasJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Encomendas encomendas) {
    
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            
            
            em.persist(encomendas);
            
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Encomendas encomendas) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();

            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = encomendas.getEncoId();
                if (findEncomendas(id) == null) {
                    throw new NonexistentEntityException("The encomendas with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Encomendas encomendas;
            try {
                encomendas = em.getReference(Encomendas.class, id);
                encomendas.getEncoId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The encomendas with id " + id + " no longer exists.", enfe);
            }
       
            em.remove(encomendas);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Encomendas> findEncomendasEntities() {
        return findEncomendasEntities(true, -1, -1);
    }

    public List<Encomendas> findEncomendasEntities(int maxResults, int firstResult) {
        return findEncomendasEntities(false, maxResults, firstResult);
    }

    private List<Encomendas> findEncomendasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Encomendas.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Encomendas findEncomendas(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Encomendas.class, id);
        } finally {
            em.close();
        }
    }

    public List<Encomendas> findEncomendas(String codigoRastreio) {
        EntityManager em = getEntityManager();
        try {

            TypedQuery tq = em.createNamedQuery("Encomendas.findByCodigoRastreio", Encomendas.class);

            tq.setParameter("codigoRastreio", codigoRastreio);

            return tq.getResultList();
        } finally {
            em.close();
        }
    }

    public int getEncomendasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Encomendas> rt = cq.from(Encomendas.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
