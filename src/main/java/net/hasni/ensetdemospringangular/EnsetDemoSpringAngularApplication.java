package net.hasni.ensetdemospringangular;

import net.hasni.ensetdemospringangular.entities.*;
import net.hasni.ensetdemospringangular.enums.FactureStatus;
import net.hasni.ensetdemospringangular.enums.PaymentStatus;
import net.hasni.ensetdemospringangular.enums.PaymentType;
import net.hasni.ensetdemospringangular.enums.TrimestreType;
import net.hasni.ensetdemospringangular.repository.*;
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
											StudentInformationsRepository studentInformationsRepository, CoursRepository coursRepository, FactureRepository factureRepository
											) {

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

			Cours cour1 = Cours.builder()
					.titre("Francais")
					.date("22/05/2024")
					.heure("10h")
					.obligatoire(true)
					.build();
			coursRepository.save(cour1);

			Cours cour2 = Cours.builder()
					.titre("Anglais")
					.date("18/06/2024")
					.heure("15h")
					.obligatoire(false)
					.build();
			coursRepository.save(cour2);

			Cours cour3 = Cours.builder()
					.titre("Informatique")
					.date("16/06/2024")
					.heure("17h")
					.obligatoire(true)
					.build();
			coursRepository.save(cour3);

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
							.cours(List.of(cour1, cour2))
							.build(),
					Student.builder()
							.firstName("Mohamed")
							.lastName("Elhannaoui")
							.code("X2")
							.cours(List.of(cour2, cour3))
							.programId("HJFI")
							.studentInformations(infosSt2)
							.build()

			);
			studentRepository.saveAll(studentList);

			PaymentType[] paymentType = PaymentType.values();
			Random random = new Random();
			// Pour chaque étudient, on va ajouter 5 payment par exemple.
			studentRepository.findAll().forEach(st -> {
				for(int i =0; i<5; i++) {
					int index = random.nextInt(paymentType.length);
					Payment payment = Payment.builder()
							//.amount(1000+(int)(Math.random()*2000))
							.type(paymentType[index])
							.status(PaymentStatus.CREATED)
							.date(LocalDate.now())
							.student(st)
							.build();
					paymentRepository.save(payment);

				}
			});

			// Pour chaque payment, on va ajouter 3 facture par exemple.
			TrimestreType[] trimestreType = TrimestreType.values();
			paymentRepository.findAll().forEach(pay -> {
				for(int i =0; i<3; i++) {
					int index = random.nextInt(1000000);
					Facture facture = Facture.builder()
							.code(String.valueOf(index))
							.trimestre(trimestreType[i])
							.montant(1000+(int)(Math.random()*2000))
							.status(FactureStatus.PENDING)
							.payment(pay)
							.build();
					factureRepository.save(facture);

				}

			});

		};
	}

	@Bean
	public PasswordEncoder passwordEncoder1() {
		return new BCryptPasswordEncoder();
	}

}
