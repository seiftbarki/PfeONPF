package com.example.pfe2.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "demande")
public class Demande {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomDemandeur;

    @ManyToOne
    @JoinColumn(name = "numero_titre", referencedColumnName = "numero_titre")
    private TitreFinanciere titreFinanciere;
    @ManyToOne
    @JoinColumn(name = "user_id") // Clé étrangère vers User
    private User user;

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomDemandeur() {
        return nomDemandeur;
    }

    public void setNomDemandeur(String nomDemandeur) {
        this.nomDemandeur = nomDemandeur;
    }

    public TitreFinanciere getTitreFinanciere() {
        return titreFinanciere;
    }

    public void setTitreFinanciere(TitreFinanciere titreFinanciere) {
        this.titreFinanciere = titreFinanciere;
    }
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
