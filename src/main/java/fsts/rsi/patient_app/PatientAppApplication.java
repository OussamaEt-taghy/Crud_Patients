package fsts.rsi.patient_app;

import fsts.rsi.patient_app.Repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import java.util.Locale;

@SpringBootApplication
public class PatientAppApplication implements CommandLineRunner {
	@Autowired
    public PatientRepository patientRepository;
	public static void main(String[] args) {
		SpringApplication.run(PatientAppApplication.class, args);
	}
	@Override
	public void run(String... args) throws Exception {
		//S1 constricteur Sans parmetere && en Utilise les stters
		// S2 constricteur Avec parametere
		/*Patient patient = new Patient(null,"Oussama",new Date(),false, 22);
		// S3 utilisant un builder
		Patient patient1 =Patient.builder()
				.name("Youssef")
				.dateNissance(new Date())
				.malade(true)
				.score(12)
				.build();
		Patient patient2 = new Patient(null,"Iman",new Date(),false, 24);
		Patient patient3 = new Patient(null,"Amine",new Date(),false, 29);
		Patient patient4 = new Patient(null,"Hamza",new Date(),false, 12);
		Patient patient5 = new Patient(null,"Khadija",new Date(),false, 78);
		Patient patient6 = new Patient(null,"Yassine",new Date(),false, 9);
		Patient patient7 = new Patient(null,"Omar",new Date(),false, 6);
		Patient patient8 = new Patient(null,"Hiba",new Date(),false, 34);
		Patient patient9 = new Patient(null,"Youness",new Date(),false, 8);
		Patient patient10 = new Patient(null,"Jamila",new Date(),false, 14);
		patientRepository.save(patient);
		patientRepository.save(patient1);
		patientRepository.save(patient2);
		patientRepository.save(patient3);
		patientRepository.save(patient4);
		patientRepository.save(patient5);
		patientRepository.save(patient6);
		patientRepository.save(patient7);
		patientRepository.save(patient8);
		patientRepository.save(patient9);
		patientRepository.save(patient10);*/
	}
		@Bean
	     public PasswordEncoder passwordEncoder() {
			return new BCryptPasswordEncoder();
		}
	@Bean
	CommandLineRunner commandLineRunner(JdbcUserDetailsManager jdbcUserDetailsManager, PasswordEncoder passwordEncoder) {
		return args -> {

			jdbcUserDetailsManager.createUser(User.withUsername("user1")
					.password(passwordEncoder.encode("password1"))
					.roles("USER")
					.build());
			jdbcUserDetailsManager.createUser(User.withUsername("user2")
					.password(passwordEncoder.encode("password2"))
					.roles("USER")
					.build());
			jdbcUserDetailsManager.createUser(User.withUsername("admin")
					.password(passwordEncoder.encode("admin1"))
					.roles("ADMIN", "USER")
					.build());
		};
	}


}
