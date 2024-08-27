package com.demo.user.management.service.impl;

import com.demo.user.management.entity.Portfolio;
import com.demo.user.management.repo.PortfolioRepository;
import com.demo.user.management.service.PortfolioService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PortfolioServiceImpl implements PortfolioService {

    private final PortfolioRepository portfolioRepository;

    @Override
    public List<Portfolio> getAll() {
        return portfolioRepository.findAll();
    }
}
