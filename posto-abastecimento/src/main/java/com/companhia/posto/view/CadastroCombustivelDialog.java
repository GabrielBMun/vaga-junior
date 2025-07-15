package com.companhia.posto.view;

import com.companhia.posto.model.Combustivel;
import com.companhia.posto.service.CombustivelService;
import com.companhia.posto.util.JPAUtil;
import jakarta.persistence.EntityManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 *
 * @author Gabriel Bilibio Mundins
 * Inicialmente feito como JFrame (por isso essa e outras classes podem ter alguma referência que esqueci de trocar o nome),
 * mostra as informações para cadastro de novos combustíveis, tem funções básicas (no caso, o preço por litro e nome), acredito ser uma boa prova de conceito.
 * 
 */
public class CadastroCombustivelDialog extends JDialog {

    private JTextField txtNome;
    private JTextField txtPreco;

    private ListaCombustivelDialog listaDialog;
    private Combustivel combustivelParaEditar;

    public CadastroCombustivelDialog(JFrame parent, ListaCombustivelDialog listaDialog) {
        this(parent, listaDialog, null);
    }

    public CadastroCombustivelDialog(JFrame parent, ListaCombustivelDialog listaDialog, Combustivel combustivel) {
        super(parent, combustivel == null ? "Cadastrar Combustível" : "Editar Combustível", true);
        this.listaDialog = listaDialog;
        this.combustivelParaEditar = combustivel;

        setSize(400, 200);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        initComponents();
        setVisible(true);
    }

    private void initComponents() {
        JLabel lblNome = new JLabel("Nome:");
        JLabel lblPreco = new JLabel("Preço por litro:");

        txtNome = new JTextField(20);
        txtPreco = new JTextField(10);

        if (combustivelParaEditar != null) {
            txtNome.setText(combustivelParaEditar.getNome());
            txtPreco.setText(String.valueOf(combustivelParaEditar.getPrecoPorLitro()));
        }

        JButton btnSalvar = new JButton(combustivelParaEditar == null ? "Cadastrar" : "Atualizar");

        btnSalvar.addActionListener((ActionEvent e) -> {
            EntityManager em = JPAUtil.getEntityManager();
            CombustivelService service = new CombustivelService(em);

            try {
                String nome = txtNome.getText();
                double preco = Double.parseDouble(txtPreco.getText());

                if (nome.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "O nome do combustível não pode ser vazio.");
                    return;
                }

                if (combustivelParaEditar == null) {
                    Combustivel novo = new Combustivel(nome, preco);
                    service.criarCombustivel(novo);
                    JOptionPane.showMessageDialog(this, "Combustível cadastrado com sucesso!");
                } else {
                    combustivelParaEditar.setNome(nome);
                    combustivelParaEditar.setPrecoPorLitro(preco);
                    service.atualizarCombustivel(combustivelParaEditar);
                    JOptionPane.showMessageDialog(this, "Combustível atualizado com sucesso!");
                }

                if (listaDialog != null) {
                    listaDialog.atualizarLista();
                }

                dispose();

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Preço inválido. Use ponto para decimais.");
            } catch (Exception ex) {
                if (em.getTransaction().isActive()) {
                    em.getTransaction().rollback();
                }
                JOptionPane.showMessageDialog(this, "Erro ao salvar: " + ex.getMessage());
            } finally {
                em.close();
            }
        });

        JPanel panel = new JPanel(new GridLayout(3, 2, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panel.add(lblNome);
        panel.add(txtNome);
        panel.add(lblPreco);
        panel.add(txtPreco);
        panel.add(new JLabel());
        panel.add(btnSalvar);

        add(panel);
    }
}
