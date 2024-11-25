package com.example.service;

import com.example.entity.Salary;
import com.example.repository.SalaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class SalaryServiceImpl implements SalaryService {

    @Autowired
    private SalaryRepository salaryRepository;
    @Value("${percentageHike}")
    private int percentageHike;

    @Override
    @Scheduled(cron = "0 0 0 1 1 ?") //exact 12 am in new year salary will increment
    public List<Salary> incrementSalaryAll() {
        List<Salary> salaryList = salaryRepository.findAll();
        List<Salary> newSalaryList = new ArrayList<>();


        for (Salary salary : salaryList) {
            double afterIncrement = salary.getSalary() + (salary.getSalary() * percentageHike) / 100;
            salary.setSalary(afterIncrement);
            newSalaryList.add(salary);
        }

        return salaryRepository.saveAll(newSalaryList);
    }

    @Override
    public Salary incrementSalary(Integer id, Salary salary) {
        Salary empSalary = salaryRepository.findByEmployeeId(id);
        double afterIncrement = empSalary.getSalary() + (empSalary.getSalary() * percentageHike) / 100;
        empSalary.setSalary(afterIncrement);
        return salaryRepository.save(empSalary);
    }

}
