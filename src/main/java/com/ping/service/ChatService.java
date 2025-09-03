package com.ping.service;

import com.ping.exception.ChatException;
import com.ping.exception.UserException;
import com.ping.model.Chat;
import com.ping.model.User;

import java.util.List;

public interface ChatService {

    public Chat createChat(User reqUser, Integer userId2) throws UserException;

    public Chat findChatById(Integer chatId) throws ChatException;

    public List<Chat> findAllChatByUserId(Integer userId) throws UserException;

    public Chat createGroup(GroupChatRequest req, User reqUer) throws UserException;

    public Chat addUserToGroup(Integer userId, Integer chatId, User reqUser) throws UserException, ChatException;

    public Chat renameGroup(Integer chatId, String groupName, User reqUser)throws UserException, ChatException;

    public Chat removeFromGroup(Integer chatId, Integer userId, User reqUser) throws UserException, ChatException;

    public void deleteChat(Integer chatId, Integer userId) throws ChatException, UserException;

}


//2.32.52
