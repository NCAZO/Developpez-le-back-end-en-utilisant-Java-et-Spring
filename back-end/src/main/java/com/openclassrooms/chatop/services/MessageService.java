package com.openclassrooms.chatop.services;

import com.openclassrooms.chatop.dto.requests.MessageRequestDto;
import com.openclassrooms.chatop.models.Message;
import com.openclassrooms.chatop.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;

@Service
public class MessageService {

    @Autowired
    private UserService userService;
    @Autowired
    private RentalService rentalService;
    @Autowired
    private MessageRepository messageRepository;

    public MessageService(UserService userService, RentalService rentalService, MessageRepository messageRepository) {
        this.userService = userService;
        this.rentalService = rentalService;
        this.messageRepository = messageRepository;
    }

    public Message createMessage(MessageRequestDto messageRequestDto) {
//        User user = userService.getUserById(userId);
//        Rental rental = rentalService.getRentalById(rentalId);
//
//        if (rental == null) {
//            throw new IllegalArgumentException("Rental not found");
//        }
//
//        Message messageCreated = new Message(null, rentalId, userId, message, new Date(), new Date());
//        messageRepository.save(messageCreated);

        Long time = Date.from(Instant.now()).getTime();

        Message newMessage = new Message();
        newMessage.setRental_id(messageRequestDto.getRental_id());
        newMessage.setUser_id(messageRequestDto.getUser_id());
        newMessage.setMessage(messageRequestDto.getMessage());
        newMessage.setCreated_at(new Date(time));
        newMessage.setUpdated_at(new Date(time));

        return messageRepository.save(newMessage);
    }
}
