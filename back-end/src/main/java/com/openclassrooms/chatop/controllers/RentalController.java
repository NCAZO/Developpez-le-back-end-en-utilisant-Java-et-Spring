package com.openclassrooms.chatop.controllers;

import com.openclassrooms.chatop.dto.requests.RentalRequestDto;
import com.openclassrooms.chatop.dto.responses.MessageResponseDto;
import com.openclassrooms.chatop.dto.responses.RentalsResponseDto;
import com.openclassrooms.chatop.models.Rental;
import com.openclassrooms.chatop.models.User;
import com.openclassrooms.chatop.services.RentalService;
import com.openclassrooms.chatop.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class RentalController {

    @Autowired
    private RentalService rentalService;
    @Autowired
    private UserService userService;

    @GetMapping("/rentals")
    public ResponseEntity<RentalsResponseDto> getRentals() {
        RentalsResponseDto rentalsResponseDto = new RentalsResponseDto();
        rentalsResponseDto.setRentals(rentalService.getRentals());
        return ResponseEntity.ok(rentalsResponseDto);
    }

    @GetMapping("/rentals/{id}")
    public ResponseEntity<Rental> getRentalById(@PathVariable("id") Long id) {
        Rental rental = rentalService.getRentalById(id);
        return ResponseEntity.ok(rental);
    }

    @PostMapping(value = "/rentals", consumes = "multipart/form-data")
    public ResponseEntity<MessageResponseDto> saveRental(@Valid @ModelAttribute RentalRequestDto rentalRequestDto) throws IOException {
        Rental newRental = rentalService.saveRental(rentalRequestDto);
        MessageResponseDto messageResponseDto = new MessageResponseDto("Rental created!");
        return ResponseEntity.ok(messageResponseDto);
    }

    @PutMapping("/rentals/{id}")
    public ResponseEntity<MessageResponseDto> updateRental(@PathVariable("id") Long id, @ModelAttribute RentalRequestDto rentalRequestDto) {
        Rental rental = rentalService.updateRental(id, rentalRequestDto);
        MessageResponseDto messageResponseDto = new MessageResponseDto("Rental updated !");
        return ResponseEntity.ok(messageResponseDto);
    }

    @DeleteMapping("/rentals/{id}")
    public ResponseEntity<Void> deleteRental(@PathVariable("id") Long id) {
        rentalService.deleteRental(id);
        return null;
    }

}