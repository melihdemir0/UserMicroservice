package tr.edu.ogu.ceng.User.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import lombok.RequiredArgsConstructor;
import tr.edu.ogu.ceng.User.entity.User;
import tr.edu.ogu.ceng.User.repository.UserRepository;

@RequiredArgsConstructor
@Service
public class UserService {

	private final UserRepository userRepository;

	private final RestClient restClient;

	public User createUser(User user) {
		restClient.get().uri("http://192.168.137.149:8001/users/1")
				.accept(org.springframework.http.MediaType.APPLICATION_JSON)
				.retrieve()
				  	.body(User.class);

		return userRepository.save(user); // Yeni kullanıcıyı kaydet
	}

	public User updateUser(User user) {

		return userRepository.save(user);

	}

	public Optional<User> getByUsername(String username) {
		return userRepository.getByUsername(username);

	}

	public Optional<User> getByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	public void deleteUserById(Long id) {
		userRepository.deleteById(id);
	}

	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	public User changeUserStatus(Long id, String newStatus) {
		User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found"));
		user.setStatus(newStatus);
		return userRepository.save(user);
	}

	public User updateUserPassword(Long id, String newPasswordHash) {
		User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found"));
		user.setPasswordHash(newPasswordHash);
		return userRepository.save(user);
	}

}