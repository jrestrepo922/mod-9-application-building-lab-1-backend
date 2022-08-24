package com.example.mod10httprequest.controller;


import com.example.mod10httprequest.model.*;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class MessagingController {
    List<Message> userMessages = new ArrayList<Message>();
    List<Message> senderMessages = new ArrayList<Message>();

    @PostConstruct
    private void initMessages() {
        userMessages.add(
                new Message(
                        new User("Aurelie"),
                        "Message from Lilly",
                        1, 2
                )
        );

        senderMessages.add(
                new Message(
                        new User("Ludovic", true),
                        "Message from Ludo",
                        1, 0
                )
        );
        senderMessages.add(
                new Message(
                        new User("Jessica", false),
                        "Message from Jess",
                        1, 1
                )
        );

    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/api/get-user-messages")
    public List<Message> getUserMessages() {
        return userMessages;
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/api/get-sender-messages")
    public List<Message> getSenderMessages() {
        return senderMessages;
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/api/add-user-message")
    public List<Message> addUserMessage(@RequestBody Message newMessage) {
        userMessages.add(newMessage);
        return userMessages;
    }


    @CrossOrigin(origins = "http://localhost:4200")
    @DeleteMapping("/api/conversations/{conversationId}/{sequenceId}")
    public List<Message> deleteMessage(@PathVariable String conversationId, @PathVariable String sequenceId) {
        Integer conversationIdInt = Integer.parseInt(conversationId);
        Integer sequenceIdInt = Integer.parseInt(sequenceId);

        this.userMessages = userMessages.stream()
                .filter(unfilteredMessage -> !(unfilteredMessage.conversationId == conversationIdInt
                        && unfilteredMessage.sequenceNumber == sequenceIdInt) ).collect(Collectors.toList());


        return this.userMessages;
    }
}
