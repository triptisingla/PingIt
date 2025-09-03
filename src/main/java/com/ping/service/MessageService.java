package com.ping.service;

import com.ping.exception.ChatException;
import com.ping.exception.MessageException;
import com.ping.exception.UserException;
import com.ping.model.Message;
import com.ping.model.User;
import com.ping.request.SendmessageRequest;

import java.util.List;

public interface MessageService {

    public Message sendMessage(SendmessageRequest req) throws UserException, ChatException;

    public List<Message> getChatMessages(Integer chatId, User reqUser) throws ChatException, UserException;

    public Message findMessageById(Integer messageId) throws MessageException;

    public void deleteMessage(Integer messageId, User reqUser) throws MessageException, UserException;
}





















//10010100101001010010100101001010010100101001010010100101001010010100101001010010100101001010010100101001010010
//

//01001 --218
//nof = 109
//01001 01001 01001 01001 01001 01001
//k1 l4  3
//k2 l6  5
//k3 l9  8
//k4 10  10

//5*107 = 535+10+1 = 546

//00000 00000 00000 01
//1t l5
//2t l8
//1t l6

//double the string.
//cound cons. len of 0
//toggle 1 to 0 after

//1001010010
//k1 l3  4
//k2 l5  6
//k3 l8  9
//k4 l10 10

//219/2 = 109 + 1

//109*5 = 545 + 4 = 549


//00101010010010101001        k=219

//219/4 = 1

//54*10 = 540+4 = 544

//54+3

//53*10 = 530+19 = 549


//110001 110001    219

//72*6 = + 6


//Iwillbeanias123#


