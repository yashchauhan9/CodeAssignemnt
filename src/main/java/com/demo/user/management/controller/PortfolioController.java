package com.demo.user.management.controller;

import com.demo.user.management.entity.Portfolio;
import com.demo.user.management.service.PortfolioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/portfolios")
@RequiredArgsConstructor
public class PortfolioController {

    private final PortfolioService portfolioService;

    @GetMapping("/all")
    public ResponseEntity<List<Portfolio>> getAllUsers() {
        return ResponseEntity.ok(portfolioService.getAll());
    }




}
