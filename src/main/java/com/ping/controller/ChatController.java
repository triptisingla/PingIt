package com.ping.controller;

import com.ping.exception.ChatException;
import com.ping.exception.UserException;
import com.ping.model.Chat;
import com.ping.model.User;
import com.ping.request.SingleChatRequest;
import com.ping.response.ApiResponse;
import com.ping.service.ChatService;
import com.ping.service.GroupChatRequest;
import com.ping.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    private ChatService chatService;
    private UserService userService;


    public ChatController(ChatService chatService, UserService userService){
        this.chatService = chatService;
        this.userService = userService;
    }

    @PostMapping("/single")
    public ResponseEntity<Chat>createChatHandler(@RequestBody SingleChatRequest singleChatRequest, @RequestHeader("Authorization")String jwt) throws UserException {
        User reqUser = userService.findUserProfile(jwt);

        Chat chat = chatService.createChat(reqUser, singleChatRequest.getUserId());

        return new ResponseEntity<Chat>(chat, HttpStatus.OK);
    }

    @PostMapping("/group")
    public ResponseEntity<Chat>createGroupHandler(@RequestBody GroupChatRequest req, @RequestHeader("Authorization")String jwt) throws UserException {
        User reqUser = userService.findUserProfile(jwt);

        Chat chat = chatService.createGroup(req, reqUser);

        return new ResponseEntity<Chat>(chat, HttpStatus.OK);
    }


    @GetMapping("/{chatId}")
    public ResponseEntity<Chat>findChatByIdHandler(@PathVariable Integer chatId, @RequestHeader("Authorization")String jwt) throws ChatException {
        Chat chat = chatService.findChatById(chatId);
        return new ResponseEntity<Chat>(chat, HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<List<Chat>>findAllChatByUserIdHandler( @RequestHeader("Authorization")String jwt) throws UserException {
        User reqUser = userService.findUserProfile(jwt);

        List<Chat> chats = chatService.findAllChatByUserId(reqUser.getId());

        return new ResponseEntity<List<Chat>>(chats, HttpStatus.OK);
    }

    @PutMapping("/{chatId}/add/{userId}")
    public ResponseEntity<Chat>addUserToGroupHandler(@PathVariable Integer chatId, @PathVariable Integer userId, @RequestHeader("Authorization")String jwt) throws UserException, ChatException {
        User reqUser = userService.findUserProfile(jwt);

        Chat chat = chatService.addUserToGroup(userId, chatId,reqUser);

        return new ResponseEntity<Chat>(chat, HttpStatus.OK);
    }


    @PutMapping("/{chatId}/remove/{userId}")
    public ResponseEntity<Chat>removeUserFromGroupHandler(@PathVariable Integer chatId, @PathVariable Integer userId, @RequestHeader("Authorization")String jwt) throws UserException, ChatException {
        User reqUser = userService.findUserProfile(jwt);

        Chat chat = chatService.removeFromGroup(chatId, userId,reqUser);

        return new ResponseEntity<Chat>(chat, HttpStatus.OK);
    }


    @DeleteMapping("/delete/{chatId}")
    public ResponseEntity<ApiResponse>deleteChatHandler(@PathVariable Integer chatId, @RequestHeader("Authorization")String jwt) throws UserException, ChatException {
        User reqUser = userService.findUserProfile(jwt);

       chatService.deleteChat(chatId,reqUser.getId());

       ApiResponse res = new ApiResponse("chat is deleted successfully", true);

        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}


//3:54:07










