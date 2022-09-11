package com.example.carms.common.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
class PostgresLockRepository {

    private final EntityManager entityManager;

    private static final String LOCK = "lock";

    public void lock(String lock) {
        entityManager.createNativeQuery("SELECT 1 FROM pg_advisory_xact_lock(hashText(:lock))")
                .setParameter(LOCK, lock)
                .getSingleResult();
    }
}
