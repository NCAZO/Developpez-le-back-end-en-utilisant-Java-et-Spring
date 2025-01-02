package com.openclassrooms.chatop.controllers;

import com.openclassrooms.chatop.dto.requests.MessageRequestDto;
import com.openclassrooms.chatop.dto.responses.MessageResponseDto;
import com.openclassrooms.chatop.models.Message;
import com.openclassrooms.chatop.services.MessageService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @PostMapping("/messages")
    public ResponseEntity<MessageResponseDto> createMessage(@Valid @RequestBody MessageRequestDto messageRequestDto){
        Message newMessage = messageService.createMessage(messageRequestDto);
        MessageResponseDto messageResponseDto = new MessageResponseDto("Message send with success");
        return ResponseEntity.ok(messageResponseDto);
    }

}
