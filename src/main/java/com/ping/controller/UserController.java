package com.ping.controller;

import com.ping.exception.UserException;
import com.ping.model.User;
import com.ping.request.UpdateUserRequest;
import com.ping.response.ApiResponse;
import com.ping.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/profile")
    public ResponseEntity<User> getUserProfileHandler(@RequestHeader("Authorization") String token) throws UserException {
        User user = userService.findUserProfile(token);
        return new ResponseEntity<User>(user, HttpStatus.ACCEPTED);
    }

//    @GetMapping("/{query}")
//    public ResponseEntity <List<User>> searchUserHandler(@PathVariable("query") String q){
//        List<User> users = userService.searchUser(q);
//
//        return new ResponseEntity<List<User>>(users,HttpStatus.OK);
//    }
@GetMapping("/{query}")
public ResponseEntity<List<User>> searchUserHandler(@PathVariable("query") String q) {
    List<User> users;
    if (q == null || q.trim().isEmpty() || q.equals("__default__")) {
        users = userService.getTop10Users();
    } else {
        users = userService.searchUser(q);
    }
    return new ResponseEntity<List<User>>(users, HttpStatus.OK);
}

    @PutMapping("/update")
    public ResponseEntity<ApiResponse>updateUserHandler(@RequestBody UpdateUserRequest req, @RequestHeader("Authorization") String token) throws UserException {
        User user = userService.findUserProfile(token);

        userService.updateUser(user.getId(), req);

        ApiResponse res = new ApiResponse("user updated successfully", true);

        return new ResponseEntity<ApiResponse>(res, HttpStatus.ACCEPTED);

    }

}
