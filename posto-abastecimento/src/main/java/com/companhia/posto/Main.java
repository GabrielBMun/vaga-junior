package com.companhia.posto;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import com.companhia.posto.view.MainFrame;

/**
 *
 * @author Gabriel Bilibio Mundins
 * Aqui, o código usa o EntityManagerFactory com o persistence como base para poder criar/atualizar as tabelas necessárias do banco, 
 * em seguida, inicia o MainFrame.
 * Se a tabela já existir, a configuração no persistence de "update" não recria ela, se não existir, cria.
 * Ou seja, só criar uma conexão no postgresql com o nome "posto", usuario "postgres" e senha "admin" (algo bem padrão) sem esquecer a porta certa.
 * 
 */
public class Main {
    public static void main(String[] args) {
        try {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("PostoPU");
            emf.close();
        } catch (Exception e) {
            e.printStackTrace();
        };
        
        javax.swing.SwingUtilities.invokeLater(() -> new MainFrame());
    }
}