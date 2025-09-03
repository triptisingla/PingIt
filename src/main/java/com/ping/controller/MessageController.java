package com.ping.controller;

import com.ping.exception.ChatException;
import com.ping.exception.MessageException;
import com.ping.exception.UserException;
import com.ping.model.Message;
import com.ping.model.User;
import com.ping.request.SendmessageRequest;
import com.ping.response.ApiResponse;
import com.ping.service.MessageService;
import com.ping.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {
    private MessageService messageService;
    private UserService userService;

    public MessageController(MessageService messageService,UserService userService){
        this.messageService = messageService;
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity<Message> sendMessageHandler(@RequestBody SendmessageRequest req, @RequestHeader("Authorization") String jwt) throws UserException, ChatException {
        User user = userService.findUserProfile(jwt);
        req.setUserId(user.getId());
        Message message = messageService.sendMessage(req);

        return new ResponseEntity<Message>(message, HttpStatus.OK);

    }


    @GetMapping("/chat/{chatId}")
    public ResponseEntity<List<Message>> getChatsMessagesHandler(@PathVariable Integer chatId,@RequestHeader("Authorization") String jwt) throws UserException, ChatException {
        User user = userService.findUserProfile(jwt);
        List<Message> messages = messageService.getChatMessages(chatId,user);

        return new ResponseEntity<>(messages, HttpStatus.OK);

    }

    @DeleteMapping("/{messageId}")
    public ResponseEntity<ApiResponse> deleteMessageHandler(@PathVariable Integer messageId, @RequestHeader("Authorization") String jwt) throws UserException, MessageException {
        User user = userService.findUserProfile(jwt);
        messageService.deleteMessage(messageId,user);
        ApiResponse res = new ApiResponse("message deleted successfully", true);
        return new ResponseEntity<>(res, HttpStatus.OK);

    }
}








