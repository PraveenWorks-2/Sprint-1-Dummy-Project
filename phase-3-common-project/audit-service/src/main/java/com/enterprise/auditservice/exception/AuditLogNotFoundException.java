package com.enterprise.auditservice.exception;

public class AuditLogNotFoundException extends RuntimeException {

    public AuditLogNotFoundException(String message) {
        super(message);
    }

    public AuditLogNotFoundException(Long id) {
        super("Audit log not found with id: " + id);
    }
}