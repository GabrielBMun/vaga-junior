package com.companhia.posto.util;

/**
 *
 * @author Gabriel Bilibio Mundins
 * Classe utilitária que gerencia a fábrica de EntityManager e fornece instâncias de EntityManager para acesso ao banco via JPA.
 */
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JPAUtil {
    private static final EntityManagerFactory emf = 
        Persistence.createEntityManagerFactory("PostoPU");

    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
}