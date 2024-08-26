package com.demo.user.management.controller;

import com.demo.user.management.dto.UserDto;
import com.demo.user.management.entity.Audit;
import com.demo.user.management.entity.UserStatus;
import com.demo.user.management.service.AuditService;
import com.demo.user.management.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/audit")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AuditController {

    private final AuditService auditService;

    @GetMapping("/all")
    public ResponseEntity<List<Audit>> getAllUsers() {
        return ResponseEntity.ok(auditService.getAll());
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<List<Audit>> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(auditService.getAllForUserId(id));
    }



}
