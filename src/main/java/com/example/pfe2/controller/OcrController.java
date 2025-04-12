package com.example.pfe2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import java.io.File;
import java.io.IOException;

@Controller
@RequestMapping("/admin")
public class OcrController {

    // Afficher la page OCR
    @GetMapping("/ocr")
    public String showOcrPage(Model model) {
        return "admin/ocr"; // Nom du template Thymeleaf
    }

    // Gérer l'upload de l'image
    @PostMapping("/upload")
    public String uploadImage(@RequestParam("file") MultipartFile file, Model model) {
        if (file.isEmpty()) {
            model.addAttribute("error", "Veuillez sélectionner un fichier.");
            return "admin/ocr";
        }

        try {
            // Enregistrer le fichier temporairement
            File tempFile = File.createTempFile("uploaded-", file.getOriginalFilename());
            file.transferTo(tempFile);

            // Configurer Tesseract
            Tesseract tesseract = new Tesseract();
            tesseract.setDatapath("C:\\Program Files\\Tesseract-OCR\\tessdata"); // Mettez à jour le chemin vers le dossier tessdata
            tesseract.setLanguage("ara"); // Langue arabe

            // Effectuer l'OCR
            String detectedText = tesseract.doOCR(tempFile);

            // Ajouter le texte détecté au modèle
            model.addAttribute("detectedText", detectedText);
            tempFile.delete(); // Supprimer le fichier temporaire
        } catch (TesseractException e) {
            model.addAttribute("error", "Erreur lors de l'OCR : " + e.getMessage());
        } catch (IOException e) {
            model.addAttribute("error", "Erreur lors de l'enregistrement du fichier : " + e.getMessage());
        }

        return "admin/ocr"; // Rediriger vers la page avec le texte détecté
    }
}
