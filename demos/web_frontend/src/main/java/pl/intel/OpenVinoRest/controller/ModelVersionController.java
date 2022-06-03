package pl.intel.OpenVinoRest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.intel.OpenVinoRest.domain.ModelVersion;
import pl.intel.OpenVinoRest.service.ModelVersionStatusService;

import java.util.List;

@RestController
@RequestMapping("/api/model-version")
public class ModelVersionController {
    @Autowired
    private ModelVersionStatusService modelVersionStatusService;

    @GetMapping
    public List<ModelVersion> getAll() {
        return modelVersionStatusService.getModelVersion();
    }

    @GetMapping(path = "/open-vino/{versionId}")
    private ModelVersion getInfo(@PathVariable int versionId) {
        return modelVersionStatusService.getModelStatusVersion(versionId);
    }

    @GetMapping(path = "/open-vino/")
    private ModelVersion getAllInfo() {
        return modelVersionStatusService.getModelStatus();
    }
}
