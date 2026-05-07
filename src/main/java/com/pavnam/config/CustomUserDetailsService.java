package com.pavnam.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import com.pavnam.model.Users;
import com.pavnam.repository.UserRepository;


public class CustomUserDetailsService implements UserDetailsService {

	
	@Autowired
	UserRepository userRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Users user=userRepo.findByEmail(username);
		if (user != null) {
            return user;
        }else {
            throw new UsernameNotFoundException("Not found: " + username);
        }}


}