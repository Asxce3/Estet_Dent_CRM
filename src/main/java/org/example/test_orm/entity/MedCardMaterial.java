package org.example.test_orm.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "medcard_material")
@Getter
@Setter
@NoArgsConstructor
public class MedCardMaterial {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medcard_id")
    private MedCard medCard;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "material_id")
    private Material material;

    private int quantity;

    public MedCardMaterial(MedCard medCard, Material material, Integer quantity) {
        this.medCard = medCard;
        this.material = material;
        this.quantity = quantity;
    }

}
