package com.masaGreen.presta.security;

import com.masaGreen.presta.models.entities.AppUser;
import com.masaGreen.presta.models.entities.Role;
import com.masaGreen.presta.repositories.AppUserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final AppUserRepository appUserRepository;
    @Override
    public UserDetails loadUserByUsername(String idNumber) throws UsernameNotFoundException {
        AppUser appUser = appUserRepository.findByIdNumber(idNumber).orElseThrow(
                ()-> new EntityNotFoundException("idNumber not registered"));

        return new User(
                appUser.getFirstName(),
                appUser.getPin(),
                mapRolesToGrantedAuthorities(appUser.getRoles()));
    }

    private Collection<SimpleGrantedAuthority> mapRolesToGrantedAuthorities(Set<Role> roles){
        return  roles.stream().map(role-> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toSet());
    }
}
