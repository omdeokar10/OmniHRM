package com.example.performance_management.controller;

import com.example.performance_management.dto.GoalDto;
import com.example.performance_management.entity.Goal;
import com.example.performance_management.exception.CustomException;
import com.example.performance_management.mapper.GoalMapper;
import com.example.performance_management.service.GoalService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Employee access entity.
 */
@RestController
@RequestMapping("/api/goals")
@CrossOrigin("*")
public class GoalController {

    private final GoalService goalService;

    public GoalController(GoalService goalService) {
        this.goalService = goalService;
    }

    @PostMapping
    public ResponseEntity<GoalDto> createGoal(@RequestBody GoalDto goalDTO) {
        Goal goal = GoalMapper.toEntity(goalDTO);
        Goal createdGoal = goalService.createGoal(goal);
        GoalDto createdGoalDTO = GoalMapper.toDTO(createdGoal);
        return ResponseEntity.ok(createdGoalDTO);
    }

    @GetMapping
    public List<GoalDto> getAllGoals() {
        return goalService.getAllGoals().stream()
                .map(GoalMapper::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GoalDto> getGoalById(@PathVariable Long id) {
        GoalDto goalDto = goalService.getGoalById(id)
                .map(GoalMapper::toDTO)
                .orElseThrow(() -> new CustomException("Goal not found"));

        return ResponseEntity.ok(goalDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GoalDto> updateGoal(@PathVariable Long id, @RequestBody GoalDto goalDTO) {
        Goal goal = GoalMapper.toEntity(goalDTO);
        Goal updatedGoal = goalService.updateGoal(id, goal);
        if (updatedGoal != null) {
            GoalDto updatedGoalDTO = GoalMapper.toDTO(updatedGoal);
            return ResponseEntity.ok(updatedGoalDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGoal(@PathVariable Long id) {
        goalService.deleteGoal(id);
        return ResponseEntity.noContent().build();
    }

}
