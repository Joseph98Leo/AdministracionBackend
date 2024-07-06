//package com.royalpaws.controller;
//
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ModelAttribute;
//
//@ControllerAdvice
//public class GlobalControllerAdvice {
//	//Para colocar nombre del usuario que accedio; luego de Bienvenido:
//	@ModelAttribute
//    public void addAttributes(Model model) {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        String username = auth.getName();
//        model.addAttribute("username", username.toUpperCase());
//    }
//	
//}
