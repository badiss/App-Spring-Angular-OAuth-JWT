package net.hasni.ensetdemospringangular;

import net.hasni.ensetdemospringangular.entities.Payment;
import net.hasni.ensetdemospringangular.entities.Student;
import net.hasni.ensetdemospringangular.entities.UtilisateurLogin;
import net.hasni.ensetdemospringangular.enums.PaymentStatus;
import net.hasni.ensetdemospringangular.enums.PaymentType;
import net.hasni.ensetdemospringangular.enums.RoleType;
import net.hasni.ensetdemospringangular.repository.PaymentRepository;
import net.hasni.ensetdemospringangular.repository.StudentRepository;
import net.hasni.ensetdemospringangular.repository.UtilisateurLoginRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

@SpringBootApplication
public class EnsetDemoSpringAngularApplication {

	public static void main(String[] args) {
		SpringApplication.run(EnsetDemoSpringAngularApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(StudentRepository studentRepository, PaymentRepository paymentRepository,
										UtilisateurLoginRepository utilisateurLoginRepository){
		return args -> {

            /*List<UtilisateurLogin> userList=List.of(
					UtilisateurLogin.builder()
							.username("badis")
							.email("badis@gmail.com")
							.role(RoleType.ADMIN)
							.password("1234")
							.build(),
					UtilisateurLogin.builder()
							.username("hajer")
							.email("hajer@gmail.com")
							.role(RoleType.USER)
							.password("1234")
							.build()

			);
			utilisateurLoginRepository.saveAll(userList);*/

		/*	List<Student> studentList=List.of(
					Student.builder()
							.firstName("Hassan")
							.lastName("Elhoumi")
							.code("X1")
							.programId("SIDG")
							.build(),
					Student.builder()
							.firstName("Mohamed")
							.lastName("Elhannaoui")
							.code("X2")
							.programId("HJFI")
							.build()

			);
			studentRepository.saveAll(studentList);


			PaymentType[] paymentType = PaymentType.values();
			Random random = new Random();
			// Pour chaque étudient, on va ajouter 10 payment par exemple.
			studentRepository.findAll().forEach(st -> {
				for(int i =0; i<10; i++) {
					int index = random.nextInt(paymentType.length);
					Payment payment = Payment.builder()
							.amount(1000+(int)(Math.random()*2000))
							.type(paymentType[index])
							.status(PaymentStatus.CREATED)
							.date(LocalDate.now())
							.student(st)
							.build();
					paymentRepository.save(payment);

				}
			});*/

		};
	}

	@Bean
	CommandLineRunner commandLineRunnerUser(JdbcUserDetailsManager jdbcUserDetailsManager, StudentRepository studentRepository, PaymentRepository paymentRepository) {

		PasswordEncoder passwordEncoder = passwordEncoder1();

		return args -> {

			jdbcUserDetailsManager.createUser(
					User.withUsername("user1").password(passwordEncoder.encode(("1234"))).authorities("USER").build()
			);

			jdbcUserDetailsManager.createUser(
					User.withUsername("admin").password(passwordEncoder.encode(("1234"))).authorities("USER", "ADMIN").build()
			);

			jdbcUserDetailsManager.createUser(
					User.withUsername("user2").password(passwordEncoder.encode(("1234"))).authorities("USER").build()
			);


			// partie Data

			List<Student> studentList=List.of(
					Student.builder()
							.firstName("Hassan")
							.lastName("Elhoumi")
							.code("X1")
							.programId("SIDG")
							.build(),
					Student.builder()
							.firstName("Mohamed")
							.lastName("Elhannaoui")
							.code("X2")
							.programId("HJFI")
							.build()

			);
			studentRepository.saveAll(studentList);


			PaymentType[] paymentType = PaymentType.values();
			Random random = new Random();
			// Pour chaque étudient, on va ajouter 10 payment par exemple.
			studentRepository.findAll().forEach(st -> {
				for(int i =0; i<10; i++) {
					int index = random.nextInt(paymentType.length);
					Payment payment = Payment.builder()
							.amount(1000+(int)(Math.random()*2000))
							.type(paymentType[index])
							.status(PaymentStatus.CREATED)
							.date(LocalDate.now())
							.student(st)
							.build();
					paymentRepository.save(payment);

				}
			});

		};
	}

	@Bean
	public PasswordEncoder passwordEncoder1() {
		return new BCryptPasswordEncoder();
	}

}
