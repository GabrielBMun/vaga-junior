package com.companhia.posto.dao;

import com.companhia.posto.model.Bomba;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;

/**
 *
 * @author Gabriel Bilibio Mundins
 * Classe que gerencia o acesso à tabela de bombas, com métodos para criar, atualizar, buscar por ID, listar todos e remover registros.
 * 
 */
public class BombaDAO {

    private EntityManager em;

    public BombaDAO(EntityManager em) {
        this.em = em;
    }
    
    public EntityManager getEntityManager() {
        return em;
    }

    public void save(Bomba bomba) {
        em.persist(bomba);
    }

    public Bomba findById(Long id) {
        return em.find(Bomba.class, id);
    }

    public List<Bomba> findAll() {
        TypedQuery<Bomba> query = em.createQuery("SELECT b FROM Bomba b", Bomba.class);
        return query.getResultList();
    }

    public void update(Bomba bomba) {
        em.merge(bomba);
    }

    public void delete(Long id) {
        Bomba bomba = em.find(Bomba.class, id);
        if (bomba != null) {
            em.remove(bomba);
        }
    }
}
