package pl.intel.OpenVinoRest.service;

import org.springframework.stereotype.Service;
import pl.intel.OpenVinoRest.domain.ModelVersion;
import pl.intel.OpenVinoRest.domain.ModelVersionStatus;
import org.springframework.web.client.RestTemplate;
import pl.intel.OpenVinoRest.repository.ModelVersionRepository;
import pl.intel.OpenVinoRest.repository.ModelVersionStatusRepository;

import java.util.List;

@Service
public class ModelVersionStatusService {

    final private String restApiPath = "http://localhost:9000/v1/models/face-detection";

    RestTemplate restTemplate = new RestTemplate();
    ModelVersionStatusRepository modelVersionStatusRepository;
    ModelVersionRepository modelVersionRepository;

    public ModelVersionStatusService(ModelVersionStatusRepository modelVersionStatusRepository,
                                     ModelVersionRepository modelVersionRepository) {
        this.modelVersionStatusRepository = modelVersionStatusRepository;
        this.modelVersionRepository = modelVersionRepository;
    }

    //Model Version:
    public List<ModelVersion> getModelVersion(){
        return modelVersionRepository.findAll();
    }

    // ModelVersionStatus:
    public List<ModelVersionStatus> getModelVersionStatus(){
        return modelVersionStatusRepository.findAll();
    }

    //Rest Template:
    public ModelVersion getModelStatus(){
        ModelVersion response = restTemplate.getForObject(restApiPath, ModelVersion.class);
        return response;
    }
    public ModelVersion getModelStatusVersion(int versionId){
        String apiPath = restApiPath + "/versions/" + versionId;
        ModelVersion response = restTemplate.getForObject(apiPath, ModelVersion.class);
        return response;
    }
}
