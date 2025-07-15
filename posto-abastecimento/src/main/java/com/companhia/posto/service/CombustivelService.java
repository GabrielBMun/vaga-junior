package com.companhia.posto.service;

import com.companhia.posto.dao.CombustivelDAO;
import com.companhia.posto.model.Combustivel;

import jakarta.persistence.EntityManager;
import java.util.List;

/**
 *
 * @author Gabriel Bilibio Mundins
 * Classe de serviço que realiza operações de CRUD e controle transacional sobre combustíveis, utilizando a CombustivelDAO.
 */
public class CombustivelService {

    private CombustivelDAO combustivelDAO;

    public CombustivelService(EntityManager em) {
        this.combustivelDAO = new CombustivelDAO(em);
    }

    public void criarCombustivel(Combustivel combustivel) {
        try {
            combustivelDAO.getEntityManager().getTransaction().begin();
            combustivelDAO.save(combustivel);
            combustivelDAO.getEntityManager().getTransaction().commit();
        } catch (Exception e) {
            if (combustivelDAO.getEntityManager().getTransaction().isActive()) {
                combustivelDAO.getEntityManager().getTransaction().rollback();
            }
            throw e;
        }
    }

    public Combustivel buscarPorId(Long id) {
        return combustivelDAO.findById(id);
    }

    public List<Combustivel> listarTodos() {
        return combustivelDAO.findAll();
    }

    public void atualizarCombustivel(Combustivel combustivel) {
        try {
            combustivelDAO.getEntityManager().getTransaction().begin();
            combustivelDAO.update(combustivel);
            combustivelDAO.getEntityManager().getTransaction().commit();
        } catch (Exception e) {
            if (combustivelDAO.getEntityManager().getTransaction().isActive()) {
                combustivelDAO.getEntityManager().getTransaction().rollback();
            }
            throw e;
        }
    }

    public void removerCombustivel(Long id) {
        try {
            combustivelDAO.getEntityManager().getTransaction().begin();
            combustivelDAO.delete(id);
            combustivelDAO.getEntityManager().getTransaction().commit();
        } catch (Exception e) {
            if (combustivelDAO.getEntityManager().getTransaction().isActive()) {
                combustivelDAO.getEntityManager().getTransaction().rollback();
            }
            throw e;
        }
    }
}
