package com.companhia.posto.model;

/**
 *
 * @author Gabriel Bilibio Mundins
 * Classe que representa uma bomba de combustível, com nome e ligação obrigatória a um tipo de combustível.
 */
import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "bombas")
public class Bomba implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @ManyToOne(optional = false)
    @JoinColumn(name = "combustivel_id", referencedColumnName = "id")
    private Combustivel combustivel;

    public Bomba() {
    }

    public Bomba(String nome, Combustivel combustivel) {
        this.nome = nome;
        this.combustivel = combustivel;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public Combustivel getCombustivel() {
        return combustivel;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCombustivel(Combustivel combustivel) {
        this.combustivel = combustivel;
    }

    @Override
    public String toString() {
        return "Bomba: " + nome + " | Combustível: " + combustivel.getNome();
    }
}