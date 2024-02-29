package com.example.userauth.Service;

import com.example.userauth.Model.MyUser;
import com.example.userauth.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetailService implements UserDetailsService {
    @Autowired
    public UserRepo userRepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException { //phương thức để lấy ra các thông tin của người dùng
        Optional<MyUser> user = userRepo.findMyUserByName(username);
        if(user.isPresent()){
            MyUser userObj = user.get();
            return User.builder()
                    .username(userObj.getName())
                    .password(userObj.getPassword())
                    .roles(getRole(userObj))
                    .build();
        }else{
            throw new UsernameNotFoundException(username);
        }
    }
//USER,ADMIN
    private String[] getRole(MyUser userObj) {
        if(userObj.getRole().isEmpty()){
            return new String[]{"USER"};
        }
        return userObj.getRole().split(",");
    }
}
