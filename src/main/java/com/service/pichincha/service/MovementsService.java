package com.service.pichincha.service;

import com.service.pichincha.dto.MovementsDTO;

import java.util.List;

public interface MovementsService {
    void saveMovement(MovementsDTO movementsDTO);

    MovementsDTO updateMovement(MovementsDTO movementsDTO);

    List<MovementsDTO> getAllMovements();

    void deleteMovement(Long movementId);
}
