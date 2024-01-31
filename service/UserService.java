package com.hotelbooking.service;

import java.util.Collections;

import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
@RequiredArgsConstructor
public class UserService IUserService{
	private UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final RoleRepository roleRepository;
	
	@Override
	public User registerUser(User user) {
		if(userRepository.existByEmail(user.getEmail())) {
			throw new UserAlreadyExistsException(user.getEmail() + "already exists");
		}
		
		user.getPassword(passwordEncoder.encode(user.getPassword()));
		Role userRole=roleRepository.findName("ROLE_USER").get();
		user.setRoles(Collections.singletonList(userRole));
		
		return userRepository.save(user);
	}
	
	@Override
	public List<User>getUsers() {
		return userRepository.findAll();
	}
	
	@Transactional
	@Override
	public void deleteUser(String email) {
		user theUer=getUser(email);
		if(theUser != null) {
	    userRepository.deleteByEmail(email);	
		}
		
	}
	
	@Override
	public User getUser(String email) {
	return userRepository.findByEmail(email)
			.orElsethrow(()-> new UsernameNotFoundException("User Not Found"));
 	
	}
	
}
