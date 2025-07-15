package com.companhia.posto.model;

/**
 *
 * @author Gabriel Bilibio Mundins
 * Classe que define o modelo de combustível, contendo nome e preço por litro, usada em relações com bombas e abastecimentos.
 */
import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "combustiveis")
public class Combustivel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(name = "preco_por_litro", nullable = false)
    private double precoPorLitro;

    public Combustivel() {
    }

    public Combustivel(String nome, double precoPorLitro) {
        this.nome = nome;
        this.precoPorLitro = precoPorLitro;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public double getPrecoPorLitro() {
        return precoPorLitro;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setPrecoPorLitro(double precoPorLitro) {
        this.precoPorLitro = precoPorLitro;
    }

    @Override
    public String toString() {
        return nome + " - R$ " + precoPorLitro + "/L";
    }
}