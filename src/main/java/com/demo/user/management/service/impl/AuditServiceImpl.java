package com.demo.user.management.service.impl;

import com.demo.user.management.entity.Audit;
import com.demo.user.management.repo.AuditRepository;
import com.demo.user.management.service.AuditService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuditServiceImpl implements AuditService {

    private final AuditRepository auditRepository;

    public void audit(Audit audit) {
        auditRepository.save(audit);
    }

    public List<Audit> getAll() {
        return auditRepository.findAll();
    }

    public List<Audit> getAllForUserId(Long id) {
        return auditRepository.findAllByUserId(id);
    }
}
