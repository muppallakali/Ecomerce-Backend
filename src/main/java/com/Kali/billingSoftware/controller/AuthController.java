package com.Kali.billingSoftware.controller;

import com.Kali.billingSoftware.io.AuthRequest;
import com.Kali.billingSoftware.io.AuthResponse;
import com.Kali.billingSoftware.service.UserService;
import com.Kali.billingSoftware.service.impl.AppUserDetailService;
import com.Kali.billingSoftware.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@RestController
public class AuthController {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private AppUserDetailService appUserDetailService;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserService userService;

@PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest request) throws Exception {
        authenticate(request.getEmail(),request.getPassword());
        UserDetails userDetails=appUserDetailService.loadUserByUsername(request.getEmail());
        final String jwtToken= jwtUtil.generateToken(userDetails);
        String role=userService.getUserRole(request.getEmail());
        return new AuthResponse(request.getEmail(),role,jwtToken);
    }

    private void authenticate(String email, String password) throws Exception {
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email,password));
        }
        catch (DisabledException e){
            throw new Exception("User Disabled");
        }
        catch(BadCredentialsException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"email or password is incorrect");
        }
    }

    @PostMapping("/encode")
    public String encodePassword(@RequestBody Map<String,String>request){
        return passwordEncoder.encode(request.get("password"));
    }
}
