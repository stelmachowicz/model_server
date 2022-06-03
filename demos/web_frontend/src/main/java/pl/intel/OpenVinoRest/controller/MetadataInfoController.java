package pl.intel.OpenVinoRest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.intel.OpenVinoRest.domain.MetadataInfo;
import pl.intel.OpenVinoRest.domain.ModelVersion;
import pl.intel.OpenVinoRest.service.MetadataInfoService;
import pl.intel.OpenVinoRest.service.ModelVersionStatusService;

import java.util.List;

@RestController
@RequestMapping("/api/metadata-info")
public class MetadataInfoController {
    @Autowired
    private MetadataInfoService metadataInfoService;

    @GetMapping(path = "/open-vino/")
    private MetadataInfo getAllInfo() {
        return metadataInfoService.getMetadataInfo();
    }
}


