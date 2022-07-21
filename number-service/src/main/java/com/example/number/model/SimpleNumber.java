package com.example.number.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SimpleNumber {
    @Id
    @PrimaryKeyJoinColumn
    private int number;
    private int squaredNumber;
}
