package com.ping.controller;


import com.ping.config.TokenProvider;
import com.ping.exception.UserException;
import com.ping.model.User;
import com.ping.repository.UserRepository;
import com.ping.request.LoginRequest;
import com.ping.response.AuthResponse;
import com.ping.service.CustomUserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private TokenProvider tokenProvider;
    private CustomUserService customUserService;

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder, CustomUserService customUserService, TokenProvider tokenProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
       this.tokenProvider = tokenProvider;
        this.customUserService = customUserService;
    }

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse>createUserHandler(@Valid @RequestBody User user) throws UserException{
        String email = user.getEmail();
        String password = user.getPassword();
        String full_name = user.getFull_name();

        User isUser = userRepository.findByEmail(email);
        if(isUser!=null){
            throw new UserException("Email is used with another account "+ email);
        }

//        Optional<User> isEmailExist = userRepository.findByEmail(email);
//
//        //check if user with the given email already exists
//        if(isEmailExist.isPresent()){
//            System.out.println("-------- exist "+ isEmailExist.get().getEmail());
//            throw new UserException("Email is already Used with another account");
//        }

        //Create new User
        User createUser = new User();
        createUser.setEmail(email);
        createUser.setFull_name(full_name);
        createUser.setPassword(passwordEncoder.encode(password));

        userRepository.save(createUser);


        Authentication authentication = new UsernamePasswordAuthenticationToken(email,password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);

        AuthResponse authResponse = new AuthResponse(jwt,true);

//        authResponse.setStatue(true);
//        authResponse.setJwt(token);

        return new ResponseEntity<AuthResponse>(authResponse, HttpStatus.ACCEPTED);
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> loginHandler (@RequestBody LoginRequest loginRequest){
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        System.out.println(email +" ------- "+password);

        Authentication authentication = authenticate(email, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);
        AuthResponse authResponse = new AuthResponse(jwt,true);

        return new ResponseEntity<AuthResponse>(authResponse, HttpStatus.ACCEPTED);
    }

    private Authentication authenticate(String username, String password){
        UserDetails userDetails = customUserService.loadUserByUsername(username);

        System.out.println("sign in userDetails - "+ userDetails);

        if(userDetails==null){
            System.out.println("sign in userDetails - null "+ userDetails);
            throw new BadCredentialsException("Invalid username or password");
        }
        if(!passwordEncoder.matches(password, userDetails.getPassword())){
            System.out.println("sign in userDetails - password  not match "+ userDetails);
            throw new BadCredentialsException("Invalid username or password");
        }
        return new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
    }
}

//1:59:14