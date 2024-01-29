package com.ssafy.easysign.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class SignInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long singId;
    private Long categoryId;
    private String content;
    private String imagePath;
    private String videoPath;
}