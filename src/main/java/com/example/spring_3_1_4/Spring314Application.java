package com.example.spring_3_1_4;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@SpringBootApplication
public class Spring314Application implements CommandLineRunner {
	static final String URL_USERS = "http://91.241.64.178:7081/api/users";
	static final String URL_USERS_DELETE = "http://91.241.64.178:7081/api/users/3";

	public static void main(String[] args) {
		SpringApplication.run(Spring314Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = restTemplate.getForEntity(URL_USERS, String.class);

		HttpHeaders headers = new HttpHeaders();
		headers.set("Cookie", Objects.requireNonNull(response.getHeaders().get("Set-Cookie")).get(0));

		User user = new User(3L, "James", "Brown", (byte) 33);
		HttpEntity<User> entity = new HttpEntity<>(user, headers);
		String result = restTemplate.exchange(URL_USERS, HttpMethod.POST, entity, String.class).getBody();

		user.setName("Thomas");
		user.setLastName("Shelby");
		result += restTemplate.exchange(URL_USERS, HttpMethod.PUT, entity, String.class).getBody();

		result += restTemplate.exchange(URL_USERS_DELETE, HttpMethod.DELETE, entity, String.class).getBody();

		System.out.println(result);
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class User {
		private Long id;
		private String name;
		private String lastName;
		private Byte age;

		public User(Long id, String name, String lastName, Byte age) {
			this.id = id;
			this.name = name;
			this.lastName = lastName;
			this.age = age;
		}

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getLastName() {
			return lastName;
		}

		public void setLastName(String lastName) {
			this.lastName = lastName;
		}

		public Byte getAge() {
			return age;
		}

		public void setAge(Byte age) {
			this.age = age;
		}

		@Override
		public String toString() {
			return "User{" +
					"id=" + id +
					", name='" + name + '\'' +
					", lastName='" + lastName + '\'' +
					", age=" + age +
					'}';
		}
	}
}