package com.example.pfe2.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "proprietaire")
public class Proprietaire {

    @Id
    @Column(name = "cin")
    private String cin;

    private String nom;

    private String prenom;

    private String numTel;

    // Getters et Setters
    public String getCin() {
        return cin;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNumTel() {
        return numTel;
    }

    public void setNumTel(String numTel) {
        this.numTel = numTel;
    }
}
