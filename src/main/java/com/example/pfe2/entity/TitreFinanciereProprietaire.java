
package com.example.pfe2.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "titre_financiere_proprietaire")
public class TitreFinanciereProprietaire {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "numero_titre", referencedColumnName = "numero_titre")
    private TitreFinanciere titreFinanciere;

    @ManyToOne
    @JoinColumn(name = "cin", referencedColumnName = "cin")
    private Proprietaire proprietaire;

    private Double surface;

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TitreFinanciere getTitreFinanciere() {
        return titreFinanciere;
    }

    public void setTitreFinanciere(TitreFinanciere titreFinanciere) {
        this.titreFinanciere = titreFinanciere;
    }

    public Proprietaire getProprietaire() {
        return proprietaire;
    }

    public void setProprietaire(Proprietaire proprietaire) {
        this.proprietaire = proprietaire;
    }

    public Double getSurface() {
        return surface;
    }

    public void setSurface(Double surface) {
        this.surface = surface;
    }
}
