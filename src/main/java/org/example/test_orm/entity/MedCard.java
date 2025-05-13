package org.example.test_orm.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
@Getter
@Setter
@ToString
public class MedCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long ID;

    @ManyToOne
    private Visits visits;

    @OneToMany(mappedBy = "medCard", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<MedCardMaterial> medCardMaterials = new HashSet<>();

    @OneToMany(mappedBy = "medCard", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Document> documents =  new ArrayList<>();

    private String diagnosis;
    private String complaints;
    private String anamnesis;
    private String treatment;
    private String objective;
    private String recommendations;
    private String allergies;
    private String botkin;

    public void addMedCardMaterial(Material material, int quantity) {
        MedCardMaterial medCardMaterial = new MedCardMaterial(this, material, quantity);
        this.medCardMaterials.add(medCardMaterial);
        material.getMedCardMaterials().add(medCardMaterial);
    }

    public void removeMaterial(Material material) {
        this.medCardMaterials.removeIf(m -> m.getMaterial().equals(material));
        material.getMedCardMaterials().removeIf(m-> m.getMedCard().equals(this));
    }

    public void addDocument(Document document) {
        document.setMedCard(this);
        this.documents.add(document);

    }

    public void removeDocument(Document document) {
        document.setMedCard(null);
        this.documents.remove(document);
    }

}
