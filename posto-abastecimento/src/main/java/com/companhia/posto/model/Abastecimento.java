package com.companhia.posto.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 *
 * @author Gabriel Bilibio Mundins
 * Classe que representa um abastecimento realizado, com data, quantidade em litros, bomba utilizada e cálculo automático do valor total com base no preço do combustível.
 * 
 */
@Entity
@Table(name = "abastecimentos")
public class Abastecimento implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "bomba_id", referencedColumnName = "id")
    private Bomba bomba;

    @Column(name = "data_abastecimento", nullable = false)
    private LocalDate dataAbastecimento;

    @Column(nullable = false)
    private double litros;

    @Column(name = "valor_total", nullable = false)
    private double valorTotal;

    public Abastecimento() {
    }

    public Abastecimento(Bomba bomba, LocalDate dataAbastecimento, double litros) {
        this.bomba = bomba;
        this.dataAbastecimento = dataAbastecimento;
        this.setLitros(litros);
    }

    public Long getId() {
        return id;
    }

    public Bomba getBomba() {
        return bomba;
    }

    public LocalDate getDataAbastecimento() {
        return dataAbastecimento;
    }

    public double getLitros() {
        return litros;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setBomba(Bomba bomba) {
        this.bomba = bomba;
        recalcularValorTotal();
    }

    public void setDataAbastecimento(LocalDate dataAbastecimento) {
        this.dataAbastecimento = dataAbastecimento;
    }

    public void setLitros(double litros) {
        this.litros = litros;
        recalcularValorTotal();
    }

    private void recalcularValorTotal() {
        if (this.bomba != null && this.bomba.getCombustivel() != null) {
            this.valorTotal = this.litros * this.bomba.getCombustivel().getPrecoPorLitro();
        }
    }

    @Override
    public String toString() {
        return String.format("Abastecimento em %s | %.2fL | R$ %.2f",
                dataAbastecimento.toString(), litros, valorTotal);
    }
}
