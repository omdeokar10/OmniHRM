package com.example.performance_management.service;

import com.example.performance_management.dto.performance.GoalDto;
import com.example.performance_management.entity.Employee;
import com.example.performance_management.entity.performance.Goal;
import com.example.performance_management.exception.CustomException;
import com.example.performance_management.mapper.GoalMapper;
import com.example.performance_management.mongoidgen.EmployeeSequenceGeneratorService;
import com.example.performance_management.repo.GoalRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GoalService {

    private final GoalRepo goalRepo;
    public GoalMapper goalMapper = new GoalMapper();
    final private EmployeeSequenceGeneratorService employeeSequenceGeneratorService;

    public GoalService(GoalRepo goalRepo, EmployeeSequenceGeneratorService employeeSequenceGeneratorService) {
        this.goalRepo = goalRepo;
        this.employeeSequenceGeneratorService = employeeSequenceGeneratorService;
    }

    public void createGoal(GoalDto goalDto) {
        Goal goal = new GoalMapper().convertToGoal(goalDto);
        goal.setId(generateGoalId());
        goalRepo.save(goal);
    }

    private Long generateGoalId() {
        return employeeSequenceGeneratorService.getGoalSequenceNumber
                (Employee.ID_KEY, Employee.ID_VAL, Goal.GENERATED_ID);
    }

    public List<GoalDto> getAllGoals() {
        return goalRepo.findAll().stream().map(goalMapper::convertToDto).collect(Collectors.toList());
    }

    public List<GoalDto> getAllGoals(String username) {
        return goalRepo.findAll().stream()
                .filter(goal -> goal.getEmployeeName().equals(username))
                .map(goalMapper::convertToDto).collect(Collectors.toList());
    }

    public GoalDto getGoalById(Long id) {
        Goal goal = getGoal(id);
        return goalMapper.convertToDto(goal);
    }

    private Goal getGoal(Long id) {
        Optional<Goal> goal = goalRepo.findById(id);
        return getGoal(goal);
    }

    private Goal getGoal(Optional<Goal> goal) {
        if(goal.isEmpty()){
            throw new CustomException("Goal not found.");
        }
        return goal.get();
    }

    private void getGoalsPerUser(String employee){
        Optional<List<Goal>> byEmployeeName = goalRepo.findByEmployeeName(employee);
        System.out.println(byEmployeeName.get());
    }
    public void updateGoal(Long id, GoalDto goalDto) {
        Goal goal = goalMapper.convertToGoal(goalDto);
        goal.setId(id);
        goalRepo.save(goal);
    }

    public void deleteGoal(Long id) {
        goalRepo.deleteById(id);
    }

    public void saveGoal(Goal goal) {
        goalRepo.save(goal);
    }
}
