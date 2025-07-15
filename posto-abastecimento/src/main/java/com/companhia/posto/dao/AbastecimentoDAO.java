package com.companhia.posto.dao;

import com.companhia.posto.model.Abastecimento;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;


/**
 *
 * @author Gabriel Bilibio Mundins
 * Classe que gerencia o acesso à tabela de Abastecimentos, com métodos para criar, atualizar, buscar por ID, listar todos e remover registros.
 * 
 */
public class AbastecimentoDAO {

    private EntityManager em;

    public AbastecimentoDAO(EntityManager em) {
        this.em = em;
    }
    
    public EntityManager getEntityManager() {
        return em;
    }

    public void save(Abastecimento abastecimento) {
        em.persist(abastecimento);
    }

    public Abastecimento findById(Long id) {
        return em.find(Abastecimento.class, id);
    }

    public List<Abastecimento> findAll() {
        TypedQuery<Abastecimento> query = em.createQuery("SELECT a FROM Abastecimento a", Abastecimento.class);
        return query.getResultList();
    }

    public void update(Abastecimento abastecimento) {
        em.merge(abastecimento);
    }

    public void delete(Long id) {
        Abastecimento abastecimento = em.find(Abastecimento.class, id);
        if (abastecimento != null) {
            em.remove(abastecimento);
        }
    }
}