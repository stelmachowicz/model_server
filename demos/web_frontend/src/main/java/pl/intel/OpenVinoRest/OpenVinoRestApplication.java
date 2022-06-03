package pl.intel.OpenVinoRest;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import pl.intel.OpenVinoRest.domain.ModelVersionStatus;
import pl.intel.OpenVinoRest.domain.Status;
import pl.intel.OpenVinoRest.repository.ModelVersionStatusRepository;
import pl.intel.OpenVinoRest.repository.StatusRepository;
import pl.intel.OpenVinoRest.service.ModelVersionStatusService;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class OpenVinoRestApplication {

	public static void main(String[] args) {
		SpringApplication.run(OpenVinoRestApplication.class, args);
	}

	@Bean
	public CommandLineRunner setupApp(ModelVersionStatusService modelVersionStatusService, ModelVersionStatusRepository modelVersionRepository,
									  StatusRepository statusRepository) {
		return args -> {

			Status status = new Status("1", "1");
			statusRepository.save(status);
			ModelVersionStatus modelVersionF = new ModelVersionStatus("abc", "cde", status);
			ModelVersionStatus modelVersionS = new ModelVersionStatus("cba", "edc", status);
			modelVersionRepository.save(modelVersionF);
			modelVersionRepository.save(modelVersionS);


			List<ModelVersionStatus> modelVersionStatuses = new ArrayList<>();
			modelVersionStatuses.add(modelVersionF);
			modelVersionStatuses.add(modelVersionS);

			System.out.println(modelVersionStatusService.getModelVersion());
		};
	}
}
