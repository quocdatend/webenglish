package com.webenglish.webenglish.config;


import com.webenglish.webenglish.Service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests(authorize -> authorize
                                .requestMatchers( "/", "/Home", "/Home/**", "/Login_Signup", "/logout", "/css/**", "/img/**", "/scss/**", "/vendor/**", "/assets/**","/Signup", "/ForgetPass","/ForgetPass/**").permitAll()
                                .requestMatchers("/User/**", "/Movies/**").hasAnyAuthority("USER","PRE")
//                        .requestMatchers("/User/**").hasAnyAuthority("PRE")
                                .requestMatchers("/Admin/**", "/AdminMovies/**").hasAnyAuthority("ADMIN")
                                .anyRequest().authenticated()
                )

                //.oauth2Login(o->o.loginPage("/login").defaultSuccessUrl("/products", true)) // Login google , facebook

                .formLogin(formLogin -> formLogin
                        .loginPage("/Login_Signup")
                        .loginProcessingUrl("/Login_Signup")
                        .successForwardUrl("/RedirectPage")
                        .failureUrl("/Login_Signup?error")
                        .permitAll())
                .logout(logout -> logout
                        .logoutUrl("/Login_Signup/logout") // URL tùy chỉnh để đăng xuất
                        .logoutSuccessUrl("/Home") // URL sau khi đăng xuất thành công
                        .invalidateHttpSession(true) // Hủy phiên làm việc.
                        .clearAuthentication(true) // Xóa xác thực.
                        .deleteCookies("JSESSIONID")
                        .permitAll())
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .accessDeniedPage("/403") // Trang báo lỗi khi truy cập không được phép.
                )
                .sessionManagement(sessionManagement -> sessionManagement
                        .maximumSessions(1) // Giới hạn số phiên đăng nhập.
                        .expiredUrl("/login") // Trang khi phiên hết hạn.
                )
                .httpBasic(withDefaults())
                .authenticationProvider(authenticationProvider());
        return http.build();
    }
    @Autowired
    private CustomUserDetailsService uds;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(uds);
        //authenticationProvider.setUserDetailsService(ads);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

}
