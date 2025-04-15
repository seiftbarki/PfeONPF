package com.example.pfe2.controller;

import com.example.pfe2.entity.Demande;
import com.example.pfe2.service.DemandeService;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.stereotype.Controller;

import java.io.IOException;

@Controller
public class ActeController {

    @Autowired
    private DemandeService demandeService;

    @PostMapping("/user/enregistrer-acte")
    public void generatePdf(
            @RequestParam("demandeId") Long demandeId,
            @RequestParam("contenu") String contenu,
            HttpServletResponse response) throws IOException {

        Demande demande = demandeService.findById(demandeId);

        // Générer le PDF
        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);

        PDPageContentStream stream = new PDPageContentStream(document, page);
        stream.beginText();
        stream.setFont(PDType1Font.HELVETICA_BOLD, 12);
        stream.setLeading(14.5f);
        stream.newLineAtOffset(50, 700);

        // Contenu de l’acte
        stream.showText("Acte pour la demande #" + demande.getId());
        stream.newLine();
        stream.newLine();
        stream.showText("Nom du Demandeur: " + demande.getNomDemandeur());
        stream.newLine();
        stream.showText("Titre Financière: " + demande.getTitreFinanciere().getNumeroTitre());
        stream.newLine();
        stream.newLine();
        stream.showText("Contenu de l’acte:");
        stream.newLine();

        // Affichage du contenu ligne par ligne (en cas de retour à la ligne dans le texte)
        for (String line : contenu.split("\n")) {
            stream.showText(line);
            stream.newLine();
        }

        stream.endText();
        stream.close();

        // Préparer le fichier pour le téléchargement
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=acte_" + demandeId + ".pdf");

        document.save(response.getOutputStream());
        document.close();
    }
}
