package com.service.pichincha.repository;

import com.service.pichincha.entities.Movements;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MovementsRepository extends CrudRepository<Movements, Long> {
    @Query(value = "SELECT m.* FROM movements m " +
            "WHERE m.account_id = :accountId " +
            "AND m.transaction_type = :transactionType " +
            "ORDER BY m.movement_date DESC LIMIT 1", nativeQuery = true)
    Optional<Movements> findLastMove(Long accountId, String transactionType);

    @Query("SELECT m FROM Movements m ORDER BY m.movementDate DESC")
    List<Movements> finAllMovements();
}
