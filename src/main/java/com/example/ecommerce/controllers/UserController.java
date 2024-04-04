package com.example.ecommerce.controllers;

import com.example.ecommerce.detailsService.JwtService;
import com.example.ecommerce.detailsService.UserInfoDetails;
import com.example.ecommerce.entities.Client;
import com.example.ecommerce.entities.Utilisateur;
import com.example.ecommerce.entities.Vendeur;
import com.example.ecommerce.entities.dto.*;
import com.example.ecommerce.entities.enumerations.Role;
import com.example.ecommerce.exceptions.DataNotFoundException;
import com.example.ecommerce.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private IUserService userService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtService jwtService;
    @Autowired
    PasswordEncoder passwordEncoder;
    @PostMapping("/login")
    public LoginResponseDTO Login (@RequestBody AuthRequestDTO authRequest){
        UserDetails user = userService.loadUserByUsername(authRequest.getUserName());
        if(!user.isEnabled()){
            throw new DataNotFoundException("Accound is not Active");
        }else if(!passwordEncoder.matches(authRequest.getPassword(), user.getPassword())){

            throw new DataNotFoundException("Invalid Password");
        }else{
            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUserName(), authRequest.getPassword()));
            if(authenticate.isAuthenticated()){
                return new LoginResponseDTO(jwtService.generateToken(authRequest.getUserName()));
            }else {
                throw new DataNotFoundException("Invalid username or password");
            }
        }

    }
    @GetMapping(path= "/getAll")
    //@PreAuthorize("hasAuthority('USER')")
    public List<UtilisateurResponseDTO> getAllUtilisateurs() {
            List<Utilisateur> utilisateurs = userService.getAllUsers();
            return utilisateurs.stream()
                    .map(UtilisateurResponseDTO::new)
                    .collect(Collectors.toList());

    }

    @PostMapping(path = "/create")
    public ResponseEntity<Utilisateur> addUser(@RequestBody UtilisateurRequestDTO userRequest) {

        Role role = userRequest.getRole();

        if (role == Role.Client) {
            Client client = new Client();
            client.setUserName(userRequest.getUserName());
            client.setFirstName(userRequest.getFirstName());
            client.setLastName(userRequest.getLastName());
            client.setEmail(userRequest.getEmail());
            client.setPhoneNumber(userRequest.getPhoneNumber());
            client.setPassword(userRequest.getPassword());
            client.setCreationDate(userRequest.getCreationDate());
            client.setGender(userRequest.getGender());
            client.setAdresseLivraison(userRequest.getAdresseLivraison());

            Client savedClient = (Client) userService.createUser(client);
            return ResponseEntity.ok(savedClient);
        } else if (role == Role.Vendeur) {
            Vendeur vendeur = new Vendeur();
            vendeur.setUserName(userRequest.getUserName());
            vendeur.setFirstName(userRequest.getFirstName());
            vendeur.setLastName(userRequest.getLastName());
            vendeur.setEmail(userRequest.getEmail());
            vendeur.setPhoneNumber(userRequest.getPhoneNumber());
            vendeur.setPassword(userRequest.getPassword());
            vendeur.setCreationDate(userRequest.getCreationDate());
            vendeur.setGender(userRequest.getGender());
            vendeur.setAdresseLivraison(userRequest.getAdresseLivraison());
            vendeur.setCoordonneesBancaires(userRequest.getCoordonneesBancaires());
            Vendeur savedVendeur = (Vendeur) userService.createUser(vendeur);
            return ResponseEntity.ok(savedVendeur);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }


    @GetMapping("/api/user")
    public ResponseEntity<UtilisateurDTO> getUserDetails(@RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.substring(7);
        String userName = jwtService.extractUserName(token);
        UserDetails userDetails = userService.loadUserByUsername(userName);
        UserInfoDetails userInfoDetails = (UserInfoDetails) userDetails;
        UtilisateurDTO utilisateur = UtilisateurDTO.builder().build();

        utilisateur.setId(userInfoDetails.getId());
        utilisateur.setUserName(userInfoDetails.getUsername());
        utilisateur.setFirstName(userInfoDetails.getFirstName());
        utilisateur.setLastName(userInfoDetails.getLastName());
        utilisateur.setEmail(userInfoDetails.getEmail());
        utilisateur.setPhoneNumber(userInfoDetails.getPhoneNumber());
        utilisateur.setCreationDate(userInfoDetails.getCreationDate());
        utilisateur.setAuthorities(userInfoDetails.getAuthorities());
        utilisateur.setAdresseLivraison(userInfoDetails.getAdresseLivraison());
        utilisateur.setCoordonneesBancaires(userInfoDetails.getCoordonneesBancaires());
        utilisateur.setGender(userInfoDetails.getGender());
        return ResponseEntity.ok(utilisateur);
    }
}
