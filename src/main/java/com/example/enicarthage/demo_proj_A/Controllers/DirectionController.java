package com.example.enicarthage.demo_proj_A.Controllers;

import com.example.enicarthage.demo_proj_A.DTO.DirectionDTO;
import com.example.enicarthage.demo_proj_A.Models.Direction;
import com.example.enicarthage.demo_proj_A.Services.DirectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/directions")
public class DirectionController {

    @Autowired
    private DirectionService directionService;

    @PostMapping
    public DirectionDTO createDirection(@RequestBody DirectionDTO direction) {
        return directionService.createDirection(direction);
    }

    @GetMapping
    public List<DirectionDTO> getAllDirections() {
        return directionService.getAllDirections();
    }

    @GetMapping("/{id}")
    public DirectionDTO getDirectionById(@PathVariable Long id) {
        return directionService.getDirectionById(id);
    }

    @PutMapping("/{id}")
    public DirectionDTO updateDirection(@PathVariable Long id, @RequestBody DirectionDTO updatedDirection) {
        return directionService.updateDirection(id, updatedDirection);
    }

    @DeleteMapping("/{id}")
    public void deleteDirection(@PathVariable Long id) {
        directionService.deleteDirection(id);
    }
}
