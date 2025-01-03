package com.openclassrooms.chatop.services;

import com.openclassrooms.chatop.dto.requests.RentalRequestDto;
import com.openclassrooms.chatop.models.Rental;
import com.openclassrooms.chatop.models.User;
import com.openclassrooms.chatop.repository.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class RentalService {

    // Injection du chemin du répertoire de stockage
    @Value("${file.upload-dir}")
    private String uploadDir;

    @Autowired
    private RentalRepository rentalRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private JwtService jwtService;

    public List<Rental> getRentals() {
        return rentalRepository.findAll();
    }

    public Rental getRentalById(Long id) {
        Optional<Rental> rental = rentalRepository.findById(id);

        if (rental.isEmpty()) {
            throw new IllegalArgumentException("Rental not found !");
        }
        return rental.get();
    }

    public Rental saveRental(String bearerToken, RentalRequestDto rental) throws IOException {
        Long time = Date.from(Instant.now()).getTime();

        String token = bearerToken.substring(7);
        String owner_email = jwtService.getUserNameFromJwtToken(token);
        User owner = userService.getUserByEmail(owner_email);

        Rental newRental = new Rental();
        newRental.setName(rental.getName());
        newRental.setSurface(rental.getSurface());
        newRental.setPrice(rental.getPrice());

        String filename = uploadPicture(rental);
        newRental.setPicture(filename);

        newRental.setDescription(rental.getDescription());

        newRental.setOwner_id(owner.getId());
        newRental.setCreated_at(new Date(time));
        newRental.setUpdated_at(new Date(time));

        return rentalRepository.save(newRental);
    }

    public String uploadPicture(RentalRequestDto rental) throws IOException {
        MultipartFile imageFile = rental.getPicture();

        if (imageFile == null || imageFile.isEmpty()) {
            throw new IOException("Image file is empty");
        }

        String filename = imageFile.getOriginalFilename();

        Path uploadDirectory = Paths.get(uploadDir);

        if (!Files.exists(uploadDirectory)) {
            Files.createDirectories(uploadDirectory);
        }

        Path filePath = uploadDirectory.resolve(filename);

        Files.write(filePath, imageFile.getBytes());

        return "http://localhost:3001/" + uploadDir + filename;
    }

    public Rental updateRental(Long id, RentalRequestDto rental) {
        Optional<Rental> existingRental = rentalRepository.findById(id);
        Long time = Date.from(Instant.now()).getTime();
        if (existingRental.isPresent()) {
            Rental updatedRental = existingRental.get();

            updatedRental.setName(rental.getName());
            updatedRental.setSurface(rental.getSurface());
            updatedRental.setPrice(rental.getPrice());
            updatedRental.setDescription(rental.getDescription());
            updatedRental.setUpdated_at(new Date(time));
            rentalRepository.save(updatedRental);

            return updatedRental;
        }

        return null;
    }

    public void deleteRental(Long id) {
        Rental rental = rentalRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Rental not found !"));
        rentalRepository.delete(rental);
    }
}