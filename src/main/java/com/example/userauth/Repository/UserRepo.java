package com.example.userauth.Repository;

import com.example.userauth.Model.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<MyUser,Long> {
    Optional<MyUser> findMyUserByName(String name);
}
