package pl.intel.OpenVinoRest.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.intel.OpenVinoRest.domain.MetadataInfo;
import pl.intel.OpenVinoRest.domain.ModelVersion;

@Service
public class MetadataInfoService {
    final private String restApiPath = "http://localhost:9000/v1/models/face-detection/versions/1/metadata";

    RestTemplate restTemplate = new RestTemplate();
    //Rest Template:
    public MetadataInfo getMetadataInfo(){
        MetadataInfo response = restTemplate.getForObject(restApiPath, MetadataInfo.class);
        return response;
    }
}
