package com.companhia.posto.view;

import com.companhia.posto.model.Combustivel;
import com.companhia.posto.service.CombustivelService;
import com.companhia.posto.util.JPAUtil;
import jakarta.persistence.EntityManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 *
 * @author Gabriel Bilibio Mundins
 * Inicialmente feito como JFrame (por isso essa e outras classes podem ter alguma referência que esqueci de trocar o nome),
 * mostra as informações dos combustíveis do banco, além de botões para adicionar, editar e excluir combustíveis.
 * 
 */
public class ListaCombustivelDialog extends JDialog {

    private CombustivelService service;
    private JTable table;
    private DefaultTableModel tableModel;

    public ListaCombustivelDialog(JFrame parent) {
        super(parent, "Lista de Combustíveis", true);  // Modal, com pai

        EntityManager em = JPAUtil.getEntityManager();
        service = new CombustivelService(em);

        setSize(600, 400);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        initComponents();
        carregarCombustiveis();

        setVisible(true);
    }

    private void initComponents() {
        tableModel = new DefaultTableModel(new Object[]{"ID", "Nome", "Preço"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // impede edição direta
            }
        };
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        JButton btnNovo = new JButton("Novo");
        JButton btnEditar = new JButton("Editar");
        JButton btnExcluir = new JButton("Excluir");

        JPanel painelBotoes = new JPanel();
        painelBotoes.add(btnNovo);
        painelBotoes.add(btnEditar);
        painelBotoes.add(btnExcluir);

        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
        add(painelBotoes, BorderLayout.SOUTH);

        btnNovo.addActionListener(e -> abrirCadastro());

        btnEditar.addActionListener(e -> editarSelecionado());

        btnExcluir.addActionListener(e -> excluirSelecionado());
    }

    private void carregarCombustiveis() {
        tableModel.setRowCount(0);
        List<Combustivel> combustiveis = service.listarTodos();
        for (Combustivel c : combustiveis) {
            tableModel.addRow(new Object[]{c.getId(), c.getNome(), c.getPrecoPorLitro()});
        }
    }

    private void abrirCadastro() {
        new CadastroCombustivelDialog((JFrame) getParent(), this);
    }

    private void editarSelecionado() {
        int linha = table.getSelectedRow();
        if (linha == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um combustível para editar.");
            return;
        }

        Long id = (Long) tableModel.getValueAt(linha, 0);
        Combustivel combustivel = service.buscarPorId(id);

        new CadastroCombustivelDialog((JFrame) getParent(), this, combustivel);
    }

    private void excluirSelecionado() {
        int linha = table.getSelectedRow();
        if (linha == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um combustível para excluir.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Deseja realmente excluir o combustível selecionado?", "Confirmar Exclusão", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            Long id = (Long) tableModel.getValueAt(linha, 0);
            try {
                service.removerCombustivel(id);
                carregarCombustiveis();
                JOptionPane.showMessageDialog(this, "Combustível excluído com sucesso!");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Erro ao excluir: " + e.getMessage());
            }
        }
    }

    public void atualizarLista() {
        carregarCombustiveis();
    }
}
