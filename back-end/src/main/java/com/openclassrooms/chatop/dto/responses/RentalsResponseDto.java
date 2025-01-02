package com.openclassrooms.chatop.dto.responses;

import com.openclassrooms.chatop.models.Rental;

import java.util.List;

public class RentalsResponseDto {
    private List<Rental> rentals;

    public List<Rental> getRentals() {
        return rentals;
    }

    public void setRentals(List<Rental> rentals) {
        this.rentals = rentals;
    }
}
