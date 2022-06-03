package pl.intel.OpenVinoRest.controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pl.intel.OpenVinoRest.domain.ModelVersionStatus;
import pl.intel.OpenVinoRest.service.ModelVersionStatusService;

@Controller
public class WebModelVersionController {
    private final ModelVersionStatusService modelVersionStatusService;

    @Autowired
    public WebModelVersionController(ModelVersionStatusService modelVersionStatusService) {
        this.modelVersionStatusService = modelVersionStatusService;
    }

    @GetMapping(path = "/version-status")
    public String getModelVersion(Model model) {
        model.addAttribute("versionStatus", modelVersionStatusService.getModelStatus());
        return "version-status";
    }

    @GetMapping(path = "/version-status/{versionId}")
    public String getModelVersion(Model model, @PathVariable int versionId) {
        model.addAttribute("versionStatus", modelVersionStatusService.getModelStatusVersion(versionId));
        return "version-status";
    }
}

