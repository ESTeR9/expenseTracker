package com.personal.expensetracker.repository;

import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ExecutionException;

import static java.util.Objects.nonNull;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private static final Firestore DB_FS = FirestoreClient.getFirestore();

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        UserDetails userDetails = isValidUser(username,password);
        if (nonNull(userDetails)) {
            return new UsernamePasswordAuthenticationToken(username, password, userDetails.getAuthorities());
        }
        return authentication;
    }

    @Override
    public boolean supports(Class<?> authenticationType) {
        return authenticationType.equals(UsernamePasswordAuthenticationToken.class);
    }

    private UserDetails isValidUser(String username, String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        try{
            Map<String, Object> userDetails = DB_FS.collection("users")
                    .whereEqualTo("name",username)
                    .get().get().getDocuments().get(0).getData();
            if(username.equals(userDetails.get("name")) &&
                    passwordEncoder.matches(password,userDetails.get("password").toString())) {
                UserDetails user = User
                        .withUsername(username)
                        .password(passwordEncoder.encode(password))
                        .roles("USER")
                        .build();
                return user;
            }
            return null;
        }
        catch (ExecutionException | InterruptedException | IndexOutOfBoundsException e){
            return null;
        }
    }
}
