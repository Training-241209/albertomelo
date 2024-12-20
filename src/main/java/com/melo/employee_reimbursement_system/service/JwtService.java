package com.melo.employee_reimbursement_system.service;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.melo.employee_reimbursement_system.model.Role;
import com.melo.employee_reimbursement_system.model.Users;
import com.melo.employee_reimbursement_system.repository.RoleRepository;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secretKey;

    @Autowired
    private RoleRepository roleRepository;

    /**
     * Generates a JWT token for the specified user.
     *
     * @param user the user for whom the token is to be generated
     * @return a JWT token as a String
     */
    public String generateToken(Users user) {
        return Jwts.builder()
                .claim("id", user.getUserId())
                .claim("email", user.getEmail())
                .claim("role", user.getRole().getRoleName())
                // Add other fields
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 15)) // 15 minutes
                .signWith(getSigningKey())
                .compact();
    }

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    /**
     * Decodes the given JWT token and retrieves the subject (email) from it.
     *
     * @param token the JWT token to decode
     * @return the subject (email) contained in the token
     * @throws io.jsonwebtoken.JwtException if the token is invalid or expired
     */
    public Users decodeToken(String token) {
        var claims = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        
        String roleName = claims.get("role", String.class);
        Role role = roleRepository.findByRoleName(roleName);
        Long userId = claims.get("id", Long.class);
        String email = claims.get("email", String.class);

        return new Users(userId, email, role);
    }
}
