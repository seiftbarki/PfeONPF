package com.example.pfe2.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "titre_financiere")
public class TitreFinanciere {

    @Id
    @Column(name = "numero_titre")
    private String numeroTitre;

    private String gouvernement;

    private Double surface;

    // Getters et Setters
    public String getNumeroTitre() {
        return numeroTitre;
    }

    public void setNumeroTitre(String numeroTitre) {
        this.numeroTitre = numeroTitre;
    }

    public String getGouvernement() {
        return gouvernement;
    }

    public void setGouvernement(String gouvernement) {
        this.gouvernement = gouvernement;
    }

    public Double getSurface() {
        return surface;
    }

    public void setSurface(Double surface) {
        this.surface = surface;
    }
}
