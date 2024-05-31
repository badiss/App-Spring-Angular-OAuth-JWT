package net.hasni.ensetdemospringangular;

import net.hasni.ensetdemospringangular.entities.Payment;
import net.hasni.ensetdemospringangular.entities.Student;
import net.hasni.ensetdemospringangular.entities.StudentInformations;
import net.hasni.ensetdemospringangular.enums.PaymentStatus;
import net.hasni.ensetdemospringangular.enums.PaymentType;
import net.hasni.ensetdemospringangular.repository.PaymentRepository;
import net.hasni.ensetdemospringangular.repository.StudentInformationsRepository;
import net.hasni.ensetdemospringangular.repository.StudentRepository;
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
	CommandLineRunner commandLineRunnerUser(JdbcUserDetailsManager jdbcUserDetailsManager, StudentRepository studentRepository, PaymentRepository paymentRepository,
											StudentInformationsRepository studentInformationsRepository) {

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

			StudentInformations infosSt1 = StudentInformations.builder()
					.address("34 av du générale leclerc")
					.city("Plessis-Robinson")
					.email("hassan@gmail.com")
					.phoneNumber("0658741412")
					.classe("5")
					.build();
			studentInformationsRepository.save(infosSt1);


			StudentInformations infosSt2 = StudentInformations.builder()
					.address("22 av du tartar")
					.city("cChatenay Malabry")
					.email("mohamed@gmail.com")
					.phoneNumber("0685411215")
					.classe("6")
					.build();
			studentInformationsRepository.save(infosSt2);

			List<Student> studentList=List.of(
					Student.builder()
							.firstName("Hassan")
							.lastName("Elhoumi")
							.code("X1")
							.programId("SIDG")
							.studentInformations(infosSt1)
							.build(),
					Student.builder()
							.firstName("Mohamed")
							.lastName("Elhannaoui")
							.code("X2")
							.programId("HJFI")
							.studentInformations(infosSt2)
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
