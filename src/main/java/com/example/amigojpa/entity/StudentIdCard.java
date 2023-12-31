package com.example.amigojpa.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import static jakarta.persistence.GenerationType.SEQUENCE;


@NoArgsConstructor
@Data
@Entity(name = "StudentIdCard")
@Table(name = "student_id_card",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "student_id_card_number_unique",
                        columnNames = "card_number")
        })
public class StudentIdCard {

    @Id
    @SequenceGenerator(
            name = "student_card_id_sequence",
            sequenceName = "student_card_id_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "student_card_id_sequence"
    )
    @Column(name = "id",
            updatable = false)
    private Long id;

    @Column(
            name = "card_number",
            nullable = false,
            columnDefinition = "TEXT",
            length = 15
    )
    private String cardNumber;


    public StudentIdCard(String cardNumber) {
        this.cardNumber = cardNumber;
    }


    public StudentIdCard(String cardNumber, Student student) {
        this.cardNumber = cardNumber;
        this.student = student;
    }

    // TODO understand what "cascade" does
    @OneToOne(
            cascade = CascadeType.ALL,
            // Is the default for one-to-one relationships
            fetch = FetchType.EAGER)
    @JoinColumn(name = "student_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name = "student_id_fk"
            )
    )
    @ToString.Exclude
    private Student student;

}

