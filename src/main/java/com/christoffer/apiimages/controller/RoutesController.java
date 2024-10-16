package com.christoffer.apiimages.controller;

import com.christoffer.apiimages.service.AI.ConversationService;
import com.christoffer.apiimages.service.AI.RoutesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/images")
public class RoutesController {

    @Autowired
    private RoutesService routesService;

    @Autowired
    private ConversationService conversationService;

    @GetMapping(value = "/version")
    public String getVersion() {
        return routesService.giveVersion();
    }

    @PostMapping(value = "/generateImage", consumes = MediaType.TEXT_PLAIN_VALUE, produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> generateImage(@RequestBody String prompt) throws IOException, InterruptedException {
        byte[] image = routesService.generateimageFromPrompt(prompt);
        return ResponseEntity.ok().body(image);
    }

    @PostMapping(value = "/generate", consumes = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<byte[]> generate(@RequestBody String prompt) throws IOException, InterruptedException {
        byte[] image = conversationService.getConversationResponse(prompt).getBytes();
        return ResponseEntity.ok().body(image);
    }

}
