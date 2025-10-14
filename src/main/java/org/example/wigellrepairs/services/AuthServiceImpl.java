package org.example.wigellrepairs.services;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    private Authentication getAuth(){
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public String getUserName(){
        return getAuth().getName();
    }

    public String getRole(){
        String raw = getAuth().getAuthorities().toString();
        int start = raw.indexOf("_")+1;
        int end = raw.indexOf("]");
        return raw.substring(start, end);
    }
}
