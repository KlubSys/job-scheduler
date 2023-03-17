package com.klub.jobs.scheduler.controller.restapi;

import com.klub.jobs.scheduler.exception.ForbiddenException;
import com.klub.jobs.scheduler.model.JobSchedulerWorker;
import com.klub.jobs.scheduler.service.IpAddressServiceInterface;
import com.klub.jobs.scheduler.service.master.KlubJobMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;
import java.util.HashMap;

@RestController
@Validated
@RequestMapping("/api/master")
public class MasterController {

    private final KlubJobMasterService masterService;
    private final IpAddressServiceInterface ipAddressService;

    @Autowired
    public MasterController(KlubJobMasterService masterService,
                            IpAddressServiceInterface ipAddressService) {
        this.masterService = masterService;
        this.ipAddressService = ipAddressService;
    }

    @PostMapping("workers")
    public HashMap<String, Object> registerWorker(HttpServletRequest request,
                                                  @RequestParam("id") @NotBlank  String id
    ) throws ForbiddenException {
        HashMap<String, Object> response = new HashMap<>();
        JobSchedulerWorker worker = masterService.registerWorker(id,
                ipAddressService.getClientIp(request));
        response.put("shard_id", worker.getShardId());

        return response;
    }

    @GetMapping("workers")
    public HashMap<String, Object> status(){
        HashMap<String, Object> response = new HashMap<>();
        response.put("workers", masterService.getWorkers());
        response.put("next_shard_id", masterService.getNextShardId());

        return response;
    }
}
