package com.companhia.posto.service;

import com.companhia.posto.dao.BombaDAO;
import com.companhia.posto.model.Bomba;

import jakarta.persistence.EntityManager;
import java.util.List;

/**
 *
 * @author Gabriel Bilibio Mundins
 * Classe de serviço responsável por controlar transações e operações de CRUD sobre bombas, delegando a lógica para a BombaDAO
 */
public class BombaService {

    private BombaDAO bombaDAO;

    public BombaService(EntityManager em) {
        this.bombaDAO = new BombaDAO(em);
    }

    public void criarBomba(Bomba bomba) {
        try {
            bombaDAO.getEntityManager().getTransaction().begin();
            bombaDAO.save(bomba);
            bombaDAO.getEntityManager().getTransaction().commit();
        } catch (Exception e) {
            if (bombaDAO.getEntityManager().getTransaction().isActive()) {
                bombaDAO.getEntityManager().getTransaction().rollback();
            }
            throw e;
        }
    }

    public Bomba buscarPorId(Long id) {
        return bombaDAO.findById(id);
    }

    public List<Bomba> listarTodas() {
        return bombaDAO.findAll();
    }

    public void atualizarBomba(Bomba bomba) {
        try {
            bombaDAO.getEntityManager().getTransaction().begin();
            bombaDAO.update(bomba);
            bombaDAO.getEntityManager().getTransaction().commit();
        } catch (Exception e) {
            if (bombaDAO.getEntityManager().getTransaction().isActive()) {
                bombaDAO.getEntityManager().getTransaction().rollback();
            }
            throw e;
        }
    }

    public void removerBomba(Long id) {
        try {
            bombaDAO.getEntityManager().getTransaction().begin();
            bombaDAO.delete(id);
            bombaDAO.getEntityManager().getTransaction().commit();
        } catch (Exception e) {
            if (bombaDAO.getEntityManager().getTransaction().isActive()) {
                bombaDAO.getEntityManager().getTransaction().rollback();
            }
            throw e;
        }
    }
}