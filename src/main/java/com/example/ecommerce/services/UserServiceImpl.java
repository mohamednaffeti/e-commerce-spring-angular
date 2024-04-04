package com.example.ecommerce.services;
import com.example.ecommerce.detailsService.UserInfoDetails;
import com.example.ecommerce.Repositories.UserRepository;
import com.example.ecommerce.entities.Admin;
import com.example.ecommerce.entities.Client;
import com.example.ecommerce.entities.Utilisateur;
import com.example.ecommerce.entities.Vendeur;
import com.example.ecommerce.exceptions.DataNotFoundException;
import com.example.ecommerce.exceptions.UserAlreadyExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements IUserService, UserDetailsService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
    @Override
    public List<Utilisateur> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Utilisateur createUser(Utilisateur user) {
        if(userRepository.findByUserName(user.getUserName()).isPresent()){
            throw new DataNotFoundException("Username already exist to another user");

        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public Utilisateur createAdmin(Utilisateur admin) {
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        return userRepository.save(admin);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public Utilisateur findById(Long id) {
        Utilisateur user = userRepository.findById(id).orElse(null);
        if(user==null){
            throw new DataNotFoundException("User not found");
        }else{
            return user;
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws DataNotFoundException {
        Optional<Utilisateur> userInfo = userRepository.findByUserName(username);
        Utilisateur utilisateur = userInfo.orElseThrow(() -> new DataNotFoundException("User not found: " + username));

        if (utilisateur instanceof Client) {
            return new UserInfoDetails((Client) utilisateur);
        }
        else if (utilisateur instanceof Vendeur) {
            return new UserInfoDetails((Vendeur) utilisateur);
        }
        else if (utilisateur instanceof Admin) {
            return new UserInfoDetails((Admin) utilisateur);
        }
        else{

            throw new DataNotFoundException("Invalid user type for: " + username);
        }
    }
}
