package de.semlaki.project_semlaki_be;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "de.semlaki.project_semlaki_be.repository")
@EntityScan(basePackages = "de.semlaki.project_semlaki_be.domain.entity")
public class ProjectSemlakiBeApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjectSemlakiBeApplication.class, args);
	}

}
