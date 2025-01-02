package com.openclassrooms.chatop.dto.requests;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class RentalRequestDto {

    private Long id;

    private String name;

    private Double surface;

    private Double price;

    private String description;

    private Long owner_id;

    private MultipartFile picture;



}
