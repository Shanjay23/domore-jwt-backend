package com.asher.domore.services;

import java.util.List;
import java.util.Optional;

import com.asher.domore.dto.UserDTO;
import com.asher.domore.models.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asher.domore.models.User;
import com.asher.domore.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;

	public Optional<UserDTO> getUserById(Long id) {
		Optional<User> user = userRepository.findById(id);
		if (user.isPresent()) {
			User existingUser = user.get();
			UserDTO userDTO = new UserDTO(existingUser.getId(), existingUser.getUsername(), existingUser.getEmail(),
					existingUser.getRoles());
			return Optional.of(userDTO);
		}
		return Optional.empty();
	}

    public Project getUserProjectById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return user.get().getProject();
        }
        return null;
    }

	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	public User saveUser(User user) {
		return userRepository.save(user);
	}
}
