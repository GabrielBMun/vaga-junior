package com.companhia.posto.service;

import com.companhia.posto.dao.AbastecimentoDAO;
import com.companhia.posto.model.Abastecimento;
import jakarta.persistence.EntityManager;
import java.util.List;

/**
 *
 * @author Gabriel Bilibio Mundins
 * Classe de serviço que gerencia operações de criação, busca, atualização e remoção de abastecimentos, utilizando a DAO com controle de transações.
 */
public class AbastecimentoService {

    private AbastecimentoDAO abastecimentoDAO;

    public AbastecimentoService(EntityManager em) {
        this.abastecimentoDAO = new AbastecimentoDAO(em);
    }

    public void criarAbastecimento(Abastecimento abastecimento) {
        try {
            abastecimentoDAO.getEntityManager().getTransaction().begin();
            abastecimentoDAO.save(abastecimento);
            abastecimentoDAO.getEntityManager().getTransaction().commit();
        } catch (Exception e) {
            if (abastecimentoDAO.getEntityManager().getTransaction().isActive()) {
                abastecimentoDAO.getEntityManager().getTransaction().rollback();
            }
            throw e;
        }
    }

    public Abastecimento buscarPorId(Long id) {
        return abastecimentoDAO.findById(id);
    }

    public List<Abastecimento> listarTodos() {
        return abastecimentoDAO.findAll();
    }

    public void atualizarAbastecimento(Abastecimento abastecimento) {
        try {
            abastecimentoDAO.getEntityManager().getTransaction().begin();
            abastecimentoDAO.update(abastecimento);
            abastecimentoDAO.getEntityManager().getTransaction().commit();
        } catch (Exception e) {
            if (abastecimentoDAO.getEntityManager().getTransaction().isActive()) {
                abastecimentoDAO.getEntityManager().getTransaction().rollback();
            }
            throw e;
        }
    }

    public void removerAbastecimento(Long id) {
        try {
            abastecimentoDAO.getEntityManager().getTransaction().begin();
            abastecimentoDAO.delete(id);
            abastecimentoDAO.getEntityManager().getTransaction().commit();
        } catch (Exception e) {
            if (abastecimentoDAO.getEntityManager().getTransaction().isActive()) {
                abastecimentoDAO.getEntityManager().getTransaction().rollback();
            }
            throw e;
        }
    }
}
