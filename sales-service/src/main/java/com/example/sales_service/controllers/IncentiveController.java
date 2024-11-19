package com.example.sales_service.controllers;

import com.example.sales_service.entities.Incentives;
import com.example.sales_service.services.IncentiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/incentive/api")
public class IncentiveController {


    @Autowired
    private IncentiveService incentiveService;

    @PostMapping("/addIncentive")
    public ResponseEntity<Incentives> addIncentives(@RequestBody Incentives incentives)
    {
         return ResponseEntity.ok(incentiveService.addIncentives(incentives));
    }
}
