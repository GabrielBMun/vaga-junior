package com.companhia.posto.view;

import com.companhia.posto.model.Abastecimento;
import com.companhia.posto.model.Bomba;
import com.companhia.posto.service.AbastecimentoService;
import com.companhia.posto.service.BombaService;
import com.companhia.posto.util.JPAUtil;
import jakarta.persistence.EntityManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

/**
 *
 * @author Gabriel Bilibio Mundins
 * Inicialmente feito como JFrame (por isso essa e outras classes podem ter alguma referência que esqueci de trocar o nome),
 * mostra as informações para cadastro de novos abastecimentos, tem funções básicas, serve só como prova de conceito.
 * 
 */
public class CadastroAbastecimentoDialog extends JDialog {

    private JComboBox<Bomba> comboBomba;
    private JTextField txtQuantidade;
    private JTextField txtData;

    private Abastecimento abastecimentoParaEditar;
    private ListaAbastecimentoDialog listaDialog;

    private final DateTimeFormatter formatoData = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public CadastroAbastecimentoDialog(JFrame parent, ListaAbastecimentoDialog listaDialog) {
        this(parent, listaDialog, null);
    }

    public CadastroAbastecimentoDialog(JFrame parent, ListaAbastecimentoDialog listaDialog, Abastecimento abastecimento) {
        super(parent, abastecimento == null ? "Cadastro de Abastecimento" : "Edição de Abastecimento", true);

        this.listaDialog = listaDialog;
        this.abastecimentoParaEditar = abastecimento;

        setSize(400, 250);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        initComponents();

        if (abastecimentoParaEditar != null) {
            carregarDadosParaEdicao();
        }

        setVisible(true);
    }

    private void initComponents() {
        JLabel lblBomba = new JLabel("Bomba:");
        JLabel lblQuantidade = new JLabel("Quantidade (litros):");
        JLabel lblData = new JLabel("Data (dd/MM/yyyy):");

        comboBomba = new JComboBox<>();
        txtQuantidade = new JTextField(10);
        txtData = new JTextField(10);

        carregarBombas();

        JButton btnSalvar = new JButton(abastecimentoParaEditar == null ? "Cadastrar" : "Atualizar");
        btnSalvar.addActionListener((ActionEvent e) -> salvarAbastecimento());

        JPanel panel = new JPanel(new GridLayout(4, 2, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panel.add(lblBomba);
        panel.add(comboBomba);

        panel.add(lblQuantidade);
        panel.add(txtQuantidade);

        panel.add(lblData);
        panel.add(txtData);

        panel.add(new JLabel());
        panel.add(btnSalvar);

        add(panel);
    }

    private void carregarBombas() {
        EntityManager em = JPAUtil.getEntityManager();
        BombaService bombaService = new BombaService(em);

        List<Bomba> bombas = bombaService.listarTodas();
        DefaultComboBoxModel<Bomba> model = new DefaultComboBoxModel<>();
        for (Bomba b : bombas) {
            model.addElement(b);
        }
        comboBomba.setModel(model);

        em.close();
    }

    private void carregarDadosParaEdicao() {
        txtQuantidade.setText(String.valueOf(abastecimentoParaEditar.getLitros()));
        txtData.setText(abastecimentoParaEditar.getDataAbastecimento().format(formatoData));
        comboBomba.setSelectedItem(abastecimentoParaEditar.getBomba());
    }

    private void salvarAbastecimento() {
        EntityManager em = JPAUtil.getEntityManager();
        AbastecimentoService service = new AbastecimentoService(em);

        try {
            Bomba bomba = (Bomba) comboBomba.getSelectedItem();
            String quantidadeStr = txtQuantidade.getText();
            String dataStr = txtData.getText();

            if (bomba == null || quantidadeStr.isEmpty() || dataStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Preencha todos os campos.");
                return;
            }

            double quantidade = Double.parseDouble(quantidadeStr);
            LocalDate data;

            try {
                data = LocalDate.parse(dataStr, formatoData);
            } catch (DateTimeParseException ex) {
                JOptionPane.showMessageDialog(this, "Data inválida. Use o formato dd/MM/yyyy.");
                return;
            }

            if (abastecimentoParaEditar == null) {
                Abastecimento novo = new Abastecimento(bomba, data, quantidade);
                service.criarAbastecimento(novo);
                JOptionPane.showMessageDialog(this, "Abastecimento cadastrado com sucesso!");
            } else {
                abastecimentoParaEditar.setBomba(bomba);
                abastecimentoParaEditar.setLitros(quantidade);
                abastecimentoParaEditar.setDataAbastecimento(data);
                service.atualizarAbastecimento(abastecimentoParaEditar);
                JOptionPane.showMessageDialog(this, "Abastecimento atualizado com sucesso!");
            }

            if (listaDialog != null) {
                listaDialog.atualizarLista();
            }

            dispose();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Quantidade inválida.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar: " + ex.getMessage());
        } finally {
            em.close();
        }
    }
}
