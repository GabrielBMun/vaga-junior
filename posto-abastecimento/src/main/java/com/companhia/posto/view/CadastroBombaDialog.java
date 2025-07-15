package com.companhia.posto.view;

import com.companhia.posto.model.Bomba;
import com.companhia.posto.model.Combustivel;
import com.companhia.posto.service.BombaService;
import com.companhia.posto.service.CombustivelService;
import com.companhia.posto.util.JPAUtil;
import jakarta.persistence.EntityManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

/**
 *
 * @author Gabriel Bilibio Mundins
 * Inicialmente feito como JFrame (por isso essa e outras classes podem ter alguma referência que esqueci de trocar o nome),
 * mostra as informações para cadastro de novas bombas, com tipo do combustível em combobox e etc, acredito ser uma boa prova de conceito.
 * 
 */
public class CadastroBombaDialog extends JDialog {

    private JTextField txtNome;
    private JComboBox<Combustivel> comboCombustivel;

    private ListaBombaDialog listaDialog;
    private Bomba bombaParaEditar;

    public CadastroBombaDialog(JFrame parent, ListaBombaDialog listaDialog) {
        this(parent, listaDialog, null);
    }

    public CadastroBombaDialog(JFrame parent, ListaBombaDialog listaDialog, Bomba bomba) {
        super(parent, bomba == null ? "Cadastro de Bomba" : "Edição de Bomba", true);
        this.listaDialog = listaDialog;
        this.bombaParaEditar = bomba;

        setSize(400, 200);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        initComponents();
        setVisible(true);
    }

    private void initComponents() {
        JLabel lblNome = new JLabel("Nome da bomba:");
        JLabel lblCombustivel = new JLabel("Combustível:");

        txtNome = new JTextField(20);
        comboCombustivel = new JComboBox<>();

        carregarCombustiveis();

        if (bombaParaEditar != null) {
            txtNome.setText(bombaParaEditar.getNome());
            comboCombustivel.setSelectedItem(bombaParaEditar.getCombustivel());
        }

        JButton btnSalvar = new JButton(bombaParaEditar == null ? "Cadastrar" : "Atualizar");

        btnSalvar.addActionListener((ActionEvent e) -> {
            EntityManager em = JPAUtil.getEntityManager();
            BombaService bombaService = new BombaService(em);

            try {
                String nome = txtNome.getText();
                Combustivel combustivel = (Combustivel) comboCombustivel.getSelectedItem();

                if (nome.isEmpty() || combustivel == null) {
                    JOptionPane.showMessageDialog(this, "Preencha todos os campos.");
                    return;
                }

                if (bombaParaEditar == null) {
                    Bomba nova = new Bomba(nome, combustivel);
                    bombaService.criarBomba(nova);
                    JOptionPane.showMessageDialog(this, "Bomba cadastrada com sucesso!");
                } else {
                    bombaParaEditar.setNome(nome);
                    bombaParaEditar.setCombustivel(combustivel);
                    bombaService.atualizarBomba(bombaParaEditar);
                    JOptionPane.showMessageDialog(this, "Bomba atualizada com sucesso!");
                }

                if (listaDialog != null) {
                    listaDialog.atualizarLista();
                }

                dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage());
            } finally {
                em.close();
            }
        });

        JPanel panel = new JPanel(new GridLayout(3, 2, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panel.add(lblNome);
        panel.add(txtNome);
        panel.add(lblCombustivel);
        panel.add(comboCombustivel);
        panel.add(new JLabel());
        panel.add(btnSalvar);

        add(panel);
    }

    private void carregarCombustiveis() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            CombustivelService combustivelService = new CombustivelService(em);
            List<Combustivel> combustiveis = combustivelService.listarTodos();
            DefaultComboBoxModel<Combustivel> model = new DefaultComboBoxModel<>();
            for (Combustivel c : combustiveis) {
                model.addElement(c);
            }
            comboCombustivel.setModel(model);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar combustíveis: " + e.getMessage());
        } finally {
            em.close();
        }
    }
}
