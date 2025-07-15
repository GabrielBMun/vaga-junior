package com.companhia.posto.view;

import com.companhia.posto.model.Abastecimento;
import com.companhia.posto.service.AbastecimentoService;
import com.companhia.posto.util.JPAUtil;
import jakarta.persistence.EntityManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 *
 * @author Gabriel Bilibio Mundins
 * Inicialmente feito como JFrame (por isso essa e outras classes podem ter alguma referência que esqueci de trocar o nome),
 * mostra as informações dos abastecimentos do banco, além de botões para adicionar, editar e excluir abastecimentos.
 * 
 */
public class ListaAbastecimentoDialog extends JDialog {

    private JTable table;
    private DefaultTableModel tableModel;

    public ListaAbastecimentoDialog(JFrame parent) {
        super(parent, "Lista de Abastecimentos", true);

        setSize(700, 400);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        initComponents();
        carregarAbastecimentos();

        setVisible(true);
    }

    private void initComponents() {
        tableModel = new DefaultTableModel(new Object[]{"ID", "Data", "Bomba", "Quantidade (L)", "Preço Total"}, 0) {
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
        btnEditar.addActionListener(e -> editarSelecionado());
        btnExcluir.addActionListener(e -> excluirSelecionado());
    }

    private void carregarAbastecimentos() {
        EntityManager em = JPAUtil.getEntityManager();
        AbastecimentoService service = new AbastecimentoService(em);

        try {
            tableModel.setRowCount(0);
            List<Abastecimento> abastecimentos = service.listarTodos();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            for (Abastecimento a : abastecimentos) {
                String bombaNome = a.getBomba() != null ? a.getBomba().getNome() : "N/A";
                String dataFormatada = a.getDataAbastecimento().format(formatter);

                tableModel.addRow(new Object[]{
                        a.getId(),
                        dataFormatada,
                        bombaNome,
                        a.getLitros(),
                        a.getValorTotal()
                });
            }
        } finally {
            em.close();
        }
    }

    private void abrirCadastro() {
        new CadastroAbastecimentoDialog((JFrame) getParent(), this);
    }

    private void editarSelecionado() {
        int linha = table.getSelectedRow();
        if (linha == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um abastecimento para editar.");
            return;
        }

        Long id = (Long) tableModel.getValueAt(linha, 0);

        EntityManager em = JPAUtil.getEntityManager();
        AbastecimentoService service = new AbastecimentoService(em);

        try {
            Abastecimento abastecimento = service.buscarPorId(id);
            new CadastroAbastecimentoDialog((JFrame) getParent(), this, abastecimento);
        } finally {
            em.close();
        }
    }

    private void excluirSelecionado() {
        int linha = table.getSelectedRow();
        if (linha == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um abastecimento para excluir.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Confirma exclusão do abastecimento?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            Long id = (Long) tableModel.getValueAt(linha, 0);

            EntityManager em = JPAUtil.getEntityManager();
            AbastecimentoService service = new AbastecimentoService(em);

            try {
                service.removerAbastecimento(id);
                carregarAbastecimentos();
                JOptionPane.showMessageDialog(this, "Abastecimento excluído com sucesso!");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Erro ao excluir: " + e.getMessage());
            } finally {
                em.close();
            }
        }
    }

    public void atualizarLista() {
        carregarAbastecimentos();
    }
}
