package com.openclassrooms.chatop.dto.requests;

public class MessageRequestDto {
    private Long rental_id;
    private Long user_id;
    private String message;

    public MessageRequestDto(Long rental_id, Long user_id, String message) {
        this.rental_id = rental_id;
        this.user_id = user_id;
        this.message = message;
    }

    public MessageRequestDto() {
    }

    public Long getRental_id() {
        return rental_id;
    }

    public void setRental_id(Long rental_id) {
        this.rental_id = rental_id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
