package com.companhia.posto.dao;

import com.companhia.posto.model.Combustivel;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import java.util.List;

/**
 *
 * @author Gabriel Bilibio Mundins
 * Classe que gerencia o acesso à tabela de Combustíveis, com métodos para criar, atualizar, buscar por ID, listar todos e remover registros.
 * 
 */
public class CombustivelDAO {

    private EntityManager em;

    public CombustivelDAO(EntityManager em) {
        this.em = em;
    }

    public EntityManager getEntityManager() {
        return em;
    }
    
    public void save(Combustivel combustivel) {
        em.persist(combustivel);
    }

    public Combustivel findById(Long id) {
        return em.find(Combustivel.class, id);
    }

    public List<Combustivel> findAll() {
        TypedQuery<Combustivel> query = em.createQuery("SELECT c FROM Combustivel c", Combustivel.class);
        return query.getResultList();
    }

    public void update(Combustivel combustivel) {
        em.merge(combustivel);
    }

    public void delete(Long id) {
        Combustivel combustivel = em.find(Combustivel.class, id);
        if (combustivel != null) {
            em.remove(combustivel);
        }
    }
}
