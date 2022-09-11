package com.example.userms.common.service;

import com.example.userms.common.constant.DbTable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostgresLockService {

    private final PostgresLockRepository postgresLockRepository;

    @Transactional(propagation = Propagation.MANDATORY)
    public void lock(DbTable table, List<String> columns) {
        postgresLockRepository.lock(createLockName(table, columns));
    }

    private static String createLockName(DbTable table, List<String> columns) {
        return table.name() + columns.stream()
                .sorted()
                .collect(Collectors.joining("-"));
    }
}
