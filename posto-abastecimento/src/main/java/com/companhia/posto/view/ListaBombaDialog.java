package com.companhia.posto.view;

import com.companhia.posto.model.Bomba;
import com.companhia.posto.service.BombaService;
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
 * mostra as informações das bombas de combustível do banco, além de botões para adicionar, editar e excluir bombas.
 * 
 */
public class ListaBombaDialog extends JDialog {

    private BombaService bombaService;
    private JTable table;
    private DefaultTableModel tableModel;

    public ListaBombaDialog(JFrame parent) {
        super(parent, "Lista de Bombas", true); // modal

        EntityManager em = JPAUtil.getEntityManager();
        bombaService = new BombaService(em);

        setSize(600, 400);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        initComponents();
        carregarBombas();

        setVisible(true);
    }

    private void initComponents() {
        tableModel = new DefaultTableModel(new Object[]{"ID", "Nome", "Combustível"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
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
        btnEditar.addActionListener(e -> editarSelecionada());
        btnExcluir.addActionListener(e -> excluirSelecionada());
    }

    private void carregarBombas() {
        tableModel.setRowCount(0); // limpa
        List<Bomba> bombas = bombaService.listarTodas();
        for (Bomba b : bombas) {
            tableModel.addRow(new Object[]{
                b.getId(),
                b.getNome(),
                b.getCombustivel() != null ? b.getCombustivel().getNome() : "N/A"
            });
        }
    }

    private void abrirCadastro() {
        // Aqui deve ser o dialog de cadastro, também modal
        new CadastroBombaDialog((JFrame) getParent(), this);
    }

    private void editarSelecionada() {
        int linha = table.getSelectedRow();
        if (linha == -1) {
            JOptionPane.showMessageDialog(this, "Selecione uma bomba para editar.");
            return;
        }

        Long id = (Long) tableModel.getValueAt(linha, 0);
        Bomba bomba = bombaService.buscarPorId(id);
        new CadastroBombaDialog((JFrame) getParent(), this, bomba);
    }

    private void excluirSelecionada() {
        int linha = table.getSelectedRow();
        if (linha == -1) {
            JOptionPane.showMessageDialog(this, "Selecione uma bomba para excluir.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Deseja excluir a bomba selecionada?", "Confirmação", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            Long id = (Long) tableModel.getValueAt(linha, 0);
            try {
                bombaService.removerBomba(id);
                carregarBombas();
                JOptionPane.showMessageDialog(this, "Bomba excluída com sucesso!");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Erro ao excluir: " + e.getMessage());
            }
        }
    }

    public void atualizarLista() {
        carregarBombas();
    }
}
