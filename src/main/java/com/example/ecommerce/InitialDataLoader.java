package com.example.ecommerce;

import com.example.ecommerce.Repositories.UserRepository;
import com.example.ecommerce.entities.Admin;
import com.example.ecommerce.entities.enumerations.Gender;
import com.example.ecommerce.entities.enumerations.Role;
import com.example.ecommerce.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class InitialDataLoader implements CommandLineRunner {
    @Autowired
    UserRepository userRepository;
    @Autowired
    IUserService userService;

    @Override
    public void run(String... args) throws Exception {
        if(userRepository.findByUserName("admin").isPresent()){
            System.out.println("L'admin existe déjà, aucune action nécessaire. ");
       }
        else{
            Admin admin = Admin.builder().build();
            admin.setUserName("admin");
            admin.setFirstName("mohamed");
            admin.setLastName("naffeti");
            admin.setEmail("mohamednaffati08@gmail.com");
            admin.setGender(Gender.Homme);
            admin.setPassword("admin");
            admin.setCreationDate(LocalDate.now());
            admin.setPhoneNumber("23232323");
            userService.createAdmin(admin);
            System.out.println("Admin ajouté avec succès !");
        }
    }
}
