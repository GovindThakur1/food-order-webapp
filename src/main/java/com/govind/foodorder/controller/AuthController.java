package com.govind.foodorder.controller;

import com.govind.foodorder.request.LoginRequest;
import com.govind.foodorder.response.ApiResponse;
import com.govind.foodorder.response.JwtResponse;
import com.govind.foodorder.security.jwt.JwtUtil;
import com.govind.foodorder.security.user.ShopUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@Valid @RequestBody LoginRequest request) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwtToken = jwtUtil.generateTokenForUser(authentication);

        ShopUserDetails userDetails = (ShopUserDetails) authentication.getPrincipal();
        JwtResponse response = new JwtResponse(userDetails.getId(), jwtToken);

        return ResponseEntity.ok(new ApiResponse("Login success", response));
    }
}
