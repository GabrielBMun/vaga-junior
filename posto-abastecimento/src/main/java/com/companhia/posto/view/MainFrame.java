package com.companhia.posto.view;

import javax.swing.*;
import java.awt.*;

/**
 *
 * @author Gabriel Bilibio Mundins
 * Aqui implementado a interfac visual básica do projeto, a página inicial com os três botões iniciais para selecionar o combustível, bomba e abastecimento.
 * 
 */
public class MainFrame extends JFrame {

    public MainFrame() {
        super("Posto de Abastecimento");

        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setIconImage(new ImageIcon(getClass().getResource("/logo.png")).getImage());

        initComponents();

        setVisible(true);
    }

    private void initComponents() {
        JButton btnCombustivel = new JButton("Gerenciar Combustíveis");
        JButton btnBomba = new JButton("Gerenciar Bombas");
        JButton btnAbastecimento = new JButton("Gerenciar Abastecimentos");

        btnCombustivel.setFocusPainted(false);
        btnBomba.setFocusPainted(false);
        btnAbastecimento.setFocusPainted(false);

        btnCombustivel.addActionListener(e -> new ListaCombustivelDialog(this));
        btnBomba.addActionListener(e -> new ListaBombaDialog(this));
        btnAbastecimento.addActionListener(e -> new ListaAbastecimentoDialog(this));

        JPanel panel = new JPanel(new GridLayout(3, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        panel.add(btnCombustivel);
        panel.add(btnBomba);
        panel.add(btnAbastecimento);

        add(panel);
    }
}
