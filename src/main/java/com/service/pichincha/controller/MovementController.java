package com.service.pichincha.controller;

import com.service.pichincha.dto.MovementsDTO;
import com.service.pichincha.service.MovementsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MovementController {

    @Autowired
    private MovementsService movementsService;

    @PostMapping("/saveMovement")
    public void saveMovement(@RequestBody MovementsDTO movementsDTO) {
        movementsService.saveMovement(movementsDTO);
    }

    @PutMapping("/updateMovement")
    public MovementsDTO updateMovement(@RequestBody MovementsDTO movementsDTO) {
        return movementsService.updateMovement(movementsDTO);
    }

    @GetMapping("/getAllMovements")
    public List<MovementsDTO> getAllMovements() {
        return movementsService.getAllMovements();
    }

    @DeleteMapping("/deleteMovement")
    public void deleteMovement(@RequestParam Long movementId) {
        movementsService.deleteMovement(movementId);
    }

}