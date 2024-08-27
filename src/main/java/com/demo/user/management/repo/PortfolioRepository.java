package com.demo.user.management.repo;

import com.demo.user.management.entity.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {
}
