/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.rastreio.database;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.example.rastreio.database.Encomendas;
import com.example.rastreio.database.Ocorrencias;
import com.example.rastreio.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

/**
 *
 * @author david
 */
public class OcorrenciasJpaController implements Serializable {

    public OcorrenciasJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Ocorrencias ocorrencias) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Encomendas encoId = ocorrencias.getEncoId();
            if (encoId != null) {
                encoId = em.getReference(encoId.getClass(), encoId.getEncoId());
                ocorrencias.setEncoId(encoId);
            }
            em.persist(ocorrencias);
         
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Ocorrencias ocorrencias) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ocorrencias persistentOcorrencias = em.find(Ocorrencias.class, ocorrencias.getOcoId());
            Encomendas encoIdOld = persistentOcorrencias.getEncoId();
            Encomendas encoIdNew = ocorrencias.getEncoId();
            if (encoIdNew != null) {
                encoIdNew = em.getReference(encoIdNew.getClass(), encoIdNew.getEncoId());
                ocorrencias.setEncoId(encoIdNew);
            }
            ocorrencias = em.merge(ocorrencias);
  
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = ocorrencias.getOcoId();
                if (findOcorrencias(id) == null) {
                    throw new NonexistentEntityException("The ocorrencias with id " + id + " no longer exists.");
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
            Ocorrencias ocorrencias;
            try {
                ocorrencias = em.getReference(Ocorrencias.class, id);
                ocorrencias.getOcoId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ocorrencias with id " + id + " no longer exists.", enfe);
            }
            Encomendas encoId = ocorrencias.getEncoId();
 
            em.remove(ocorrencias);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Ocorrencias> findOcorrenciasEntities() {
        return findOcorrenciasEntities(true, -1, -1);
    }

    public List<Ocorrencias> findOcorrenciasEntities(int maxResults, int firstResult) {
        return findOcorrenciasEntities(false, maxResults, firstResult);
    }

    private List<Ocorrencias> findOcorrenciasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Ocorrencias.class));
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

    public Ocorrencias findOcorrencias(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Ocorrencias.class, id);
        } finally {
            em.close();
        }
    }
    
        public List<Ocorrencias> findOcorrencias(Encomendas encomenda) {
        EntityManager em = getEntityManager();
        try {

            TypedQuery tq = em.createNamedQuery("Ocorrencias.findByEncomenda", Ocorrencias.class);

            tq.setParameter("enco_id", encomenda);

            return tq.getResultList();
        } finally {
            em.close();
        }
    }

    public int getOcorrenciasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Ocorrencias> rt = cq.from(Ocorrencias.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
