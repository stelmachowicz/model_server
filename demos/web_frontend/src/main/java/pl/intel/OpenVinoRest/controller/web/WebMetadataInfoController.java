package pl.intel.OpenVinoRest.controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.intel.OpenVinoRest.domain.MetadataInfo;
import pl.intel.OpenVinoRest.service.MetadataInfoService;
import pl.intel.OpenVinoRest.service.ModelVersionStatusService;

@Controller
public class WebMetadataInfoController {
    private final MetadataInfoService metadataInfoService;

    @Autowired
    public WebMetadataInfoController(MetadataInfoService metadataInfoService) {
        this.metadataInfoService = metadataInfoService;
    }

    @GetMapping(path = "/metadata-info")
    public String getModelVersion(Model model) {
        model.addAttribute("metadataInfo", metadataInfoService.getMetadataInfo());
        return "metadata-info";
    }
}
