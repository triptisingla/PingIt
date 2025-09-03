package com.ping.service;

import com.ping.exception.ChatException;
import com.ping.exception.MessageException;
import com.ping.exception.UserException;
import com.ping.model.Chat;
import com.ping.model.Message;
import com.ping.model.User;
import com.ping.repository.MessageRepository;
import com.ping.request.SendmessageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class MessageServiceImplementation implements MessageService{

    private MessageRepository messageRepository;
    private UserService userService;
    private ChatService chatService;

    public MessageServiceImplementation(MessageRepository messageRepository,UserService userService, ChatService chatService){
        this.messageRepository = messageRepository;
        this.userService = userService;
        this.chatService = chatService;
    }

    @Override
    public Message sendMessage(SendmessageRequest req) throws UserException, ChatException {
        User user = userService.findUserById(req.getUserId());
        Chat chat = chatService.findChatById(req.getChatId());

        Message message = new Message();
        message.setChat(chat);
        message.setUser(user);
        message.setContent(req.getContent());
        message.setTimestamp(LocalDateTime.now());

        return message;
    }

    @Override
    public List<Message> getChatMessages(Integer chatId, User reqUser) throws ChatException, UserException {
        Chat chat = chatService.findChatById(chatId);

        if(!chat.getUsers().contains(reqUser)){
            throw new UserException("You are not related to this chat"+ chat.getId());
        }

        List<Message>messages = messageRepository.findByChatId(chat.getId());
        return messages;
    }

    @Override
    public Message findMessageById(Integer messageId) throws MessageException {
        Optional<Message> opt = messageRepository.findById(messageId);
        if(opt.isPresent()){
            return opt.get();
        }
        throw new MessageException("message not found with id "+ messageId);
    }

    @Override
    public void deleteMessage(Integer messageId, User reqUser) throws MessageException, UserException {
        Message message = findMessageById(messageId);

        if(message.getUser().getId().equals(reqUser.getId())){
            messageRepository.deleteById(messageId);
        }

        throw new UserException("you can't delete another user's message "+ reqUser.getFull_name());
    }
}













