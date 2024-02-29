package com.example.userauth.Config;

import com.example.userauth.Service.MyUserDetailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    private final MyUserDetailService myUserDetailService;

    public SecurityConfiguration(MyUserDetailService myUserDetailService) {
        this.myUserDetailService = myUserDetailService;
    }
    @Bean
    public MyUserDetailService userDetailService(){
        return this.myUserDetailService;
    }
    @Bean
    public PasswordEncoder passwordEncoder(){ //Cấu hình password encoder
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)throws Exception{
        http
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(registry ->{
                  registry.requestMatchers("/","/home","/register/**").permitAll();
                  registry.requestMatchers("/user/**").hasRole("USER");
//                registry.requestMatchers("/user/**").hasRole("ADMIN");
                  registry.requestMatchers("/admin/**").hasRole("ADMIN");
                  registry.anyRequest().authenticated();
            })
//            .formLogin(AbstractAuthenticationFilterConfigurer::permitAll);
            .formLogin(httpSecurityFormLoginConfigurer -> httpSecurityFormLoginConfigurer.loginPage("/login")
                .successHandler(new AuthenticationSuccessHandler()) //xử lý làm gì tiếp theo sau khi login
                .permitAll());
//        http.formLogin().defaultSuccessUrl("/hello");
        return http.build();
   }
   @Bean
    AuthenticationProvider authenticationProvider(){
       DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailService());
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }
//   @Bean
//    public UserDetailsService userDetailsService(){
//       UserDetails user = User.builder()
//               .username("nk")
//               .password("$2a$12$Xr6tR19p2k9VepXAWEO9.uDdpbtNon0536Wj4q2HWzxJVjOHeMfdi")
//               .roles("USER")
//               .build();
//        UserDetails admin = User.builder()
//                .username("admin")
//                .password("$2a$12$bO6bsVK5afHt4/KvBj8BJOjR7xXGvM/G5RlbLnccTyzc9as8ND.V2")
//                .roles("ADMIN","USER")
//                .build();
//        return new InMemoryUserDetailsManager(user,admin);
//   }
}
