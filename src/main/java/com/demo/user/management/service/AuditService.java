package com.demo.user.management.service;

import com.demo.user.management.entity.Audit;

import java.util.List;

public interface AuditService {
    void audit(Audit audit);
    List<Audit> getAll();
    List<Audit> getAllForUserId(Long id);
}
