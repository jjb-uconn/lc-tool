package us.jbec.lct.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import us.jbec.lct.models.CaptureDataStatistics;
import us.jbec.lct.models.ImageJob;
import us.jbec.lct.services.CaptureDataStatisticsService;
import us.jbec.lct.services.RemoteJobService;

import java.util.List;

@RestController
@Profile("remote")
public class RemoteStatisticsController {

    Logger LOG = LoggerFactory.getLogger(RemoteStatisticsController.class);

    private final CaptureDataStatisticsService captureDataStatisticsService;

    private final RemoteJobService remoteJobService;

    public RemoteStatisticsController(CaptureDataStatisticsService captureDataStatisticsService, RemoteJobService remoteJobService) {
        this.captureDataStatisticsService = captureDataStatisticsService;
        this.remoteJobService = remoteJobService;
    }

    @GetMapping("/calculateStatistics")
    public CaptureDataStatistics statistics(Model model) {
        try {
            List<ImageJob> imageJobs = remoteJobService.retrieveCurrentRemoteJobs(false);
            return captureDataStatisticsService.calculateStatistics(imageJobs);
        }
        catch (Exception e) {
            LOG.error("An error occurred generating statistics!", e);
            throw e;
        }
    }
}
