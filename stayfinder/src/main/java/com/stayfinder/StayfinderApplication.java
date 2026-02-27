package com.stayfinder;

import com.stayfinder.repository.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StayfinderApplication {

    private final UserRepository userRepository;

    StayfinderApplication(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

	public static void main(String[] args) {
		SpringApplication.run(StayfinderApplication.class, args);
		System.out.print("hii");
	}

}
