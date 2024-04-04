package com.buddybank.mysuperbank.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Integer statusCode = (Integer) request.getAttribute("jakarta.servlet.error.status_code");
        Exception exception = (Exception) request.getAttribute("jakarta.servlet.error.exception");

        String errorMessage;
        if (statusCode != null) {
            errorMessage = HttpStatus.valueOf(statusCode).getReasonPhrase();
        } else if (exception != null) {
            errorMessage = exception.getMessage();
        } else {
            errorMessage = "An unknown error occurred";
        }

        model.addAttribute("message", errorMessage);
        return "error";
    }
}