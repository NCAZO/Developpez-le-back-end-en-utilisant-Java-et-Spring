package com.openclassrooms.chatop.repository;

import com.openclassrooms.chatop.models.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
