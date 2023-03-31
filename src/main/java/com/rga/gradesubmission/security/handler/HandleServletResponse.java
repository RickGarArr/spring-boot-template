package com.rga.gradesubmission.security.handler;

import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import org.springframework.http.MediaType;
import com.rga.gradesubmission.domain.exception.ErrorResponse;
import com.rga.gradesubmission.security.SecurityConstants;

public class HandleServletResponse {

    private static final String TOKEN_KEY = "token";

    public static void handleException(HttpServletResponse response, int status, Exception ex) {
        try {
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding("UTF-8");
            response.setStatus(status);
            ErrorResponse errorResponse = new ErrorResponse(Arrays.asList(ex.getMessage()));
            JSONObject json = new JSONObject();
            for (Method method : errorResponse.getClass().getMethods()) {
                String methodName = method.getName();
                if (methodName.startsWith("get") && !methodName.contentEquals("getClass") && method.invoke(errorResponse) != null) {
                    Object getterValue = method.invoke(errorResponse);
                    String property = methodName.replace("get", "");
                    if (getterValue.getClass().equals(java.time.LocalDateTime.class) || getterValue.getClass().equals(java.time.LocalDate.class) || getterValue.getClass().equals(java.util.Date.class))
                        getterValue = localDateTimeToDateWithSlash((LocalDateTime) getterValue);
                    char c[] = property.toCharArray();
                    c[0] = Character.toLowerCase(c[0]);
                    json.put(new String(c), getterValue);
                }
            }
            PrintWriter out = response.getWriter();
            out.print(json.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void handleException(HttpServletResponse response, int status, String message) {
        try {
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding("UTF-8");
            response.setStatus(status);
            ErrorResponse errorResponse = new ErrorResponse(Arrays.asList(message));
            JSONObject json = new JSONObject();
            for (Method method : errorResponse.getClass().getMethods()) {
                String methodName = method.getName();
                if (methodName.startsWith("get") && !methodName.contentEquals("getClass") && method.invoke(errorResponse) != null) {
                    Object getterValue = method.invoke(errorResponse);
                    String property = methodName.replace("get", "");
                    if (getterValue.getClass().equals(java.time.LocalDateTime.class) || getterValue.getClass().equals(java.time.LocalDate.class) || getterValue.getClass().equals(java.util.Date.class))
                        getterValue = localDateTimeToDateWithSlash((LocalDateTime) getterValue);
                    char c[] = property.toCharArray();
                    c[0] = Character.toLowerCase(c[0]);
                    json.put(new String(c), getterValue);
                }
            }
            PrintWriter out = response.getWriter();
            out.print(json.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void handleTokenResponse(HttpServletResponse response, int status, String token) {
        try {
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding("UTF-8");
            response.setStatus(status);
            JSONObject json = new JSONObject();
            json.put(TOKEN_KEY, SecurityConstants.BEARER + token);
            PrintWriter out = response.getWriter();
            out.print(json.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String localDateTimeToDateWithSlash(LocalDateTime localDateTime) {
        return DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss").format(localDateTime);
    }
}
