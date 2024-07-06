//package com.royalpaws;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@Configuration
//public class ConfigureImage implements WebMvcConfigurer{
//	
//	@Override
//	public void addResourceHandlers(ResourceHandlerRegistry registry) {
//		
//		// llamar al objecto ResourceHandlerRegistry
//		// 1. la carpeta contenedore.2.La ruta fisica
//		
//		// EXTERNO
//		//registry.addResourceHandler("/datosImg/**").addResourceLocations("file:/C:/Users/User/Desktop/ImagenesDAWI/datosImg/");
//		
//		// USB(LECTURA)
//		registry.addResourceHandler("/datosImg/**").addResourceLocations("file:/E:/Cibertec New/5to Ciclo/Desarrollo de Aplicaciones Web/ImagenesDAWI/datosImg/");
//	}
//
//}
