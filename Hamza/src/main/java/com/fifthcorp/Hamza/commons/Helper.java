package com.fifthcorp.Hamza.commons;

import jakarta.servlet.http.HttpServletRequest;

public class Helper {
	private static String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3OD";

    public static boolean validateToken(HttpServletRequest request) {
        String reqToken = request.getHeader("Authorization");
        
        if (reqToken != null && reqToken.startsWith("Bearer ")) {
        	reqToken = reqToken.substring(7);
            return true;
        }
        return false;
    }
    
    public static String getToken() {
        return token;
    }
}
