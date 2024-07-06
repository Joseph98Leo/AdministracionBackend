//package com.royalpaws;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//public class SecurityConfig {
//
//    @SuppressWarnings("removal")
//    @Bean
//    SecurityFilterChain filterChain(HttpSecurity http, AuthenticationManager authManager) throws Exception {
//
//        //Validacion de que cuente con todos los templates nombrados antes de ejecutar
//        http
//                .authorizeHttpRequests()
//                .requestMatchers("/index", "/contactanos", "/nosotros", "/login", "/logout").permitAll()
//                .requestMatchers("").hasRole("USER")
//                .requestMatchers("/resources/**").permitAll()
//                .requestMatchers("/**").hasRole("ADMIN")
//                .anyRequest().authenticated()
//                .and()
//                .formLogin(login -> login
//                        .loginPage("/login")
//                        .defaultSuccessUrl("/index"))
//                .logout(logout -> logout
//                        .logoutUrl("/logout")
//                        .permitAll());
//		return http.build();
//	}
//
//	
//	@Bean
//	public UserDetailsService userDetailsService () {
//		InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
//		manager.createUser(User.withUsername("brandon")
//					.password(passwordEncoder().encode("123"))
//					.roles("ADMIN")
//					.build())
//		;
//		manager.createUser(User.withUsername("jose")
//				.password(passwordEncoder().encode("1010"))
//				.roles("ADMIN")
//				.build());
//		manager.createUser(User.withUsername("user")
//				.password(passwordEncoder().encode("123"))
//				.roles("USER")
//				.build());
//		return manager;
//	}
//	
//	@SuppressWarnings("removal")
//	@Bean
//	AuthenticationManager authManager(HttpSecurity http) throws Exception {
//		return http.getSharedObject(AuthenticationManagerBuilder.class).userDetailsService(userDetailsService())
//									.passwordEncoder(passwordEncoder())
//									.and()
//									.build();
//	}
//	
//	@Bean
//	PasswordEncoder passwordEncoder() {
//		return new BCryptPasswordEncoder();
//	}
//}
