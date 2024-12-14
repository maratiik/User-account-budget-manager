package maratik.budget_manager.api.controllers;

import lombok.RequiredArgsConstructor;
import maratik.budget_manager.api.constants.Endpoints;
import maratik.budget_manager.model.dto.income.IncomeDto;
import maratik.budget_manager.model.dto.summary.FullSummaryIncomeDto;
import maratik.budget_manager.model.entities.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(Endpoints.INCOME)
@RequiredArgsConstructor
public class IncomeController {

    private final TotalIncomeService totalIncomeService;
    private final IncomeService incomeService;

    @GetMapping
    public List<IncomeDto> getAll(
            @AuthenticationPrincipal User user) {
        return incomeService.getAllByUserId(user.getId());
    }

    @GetMapping("/{id}")
    public IncomeDto getById(
            @AuthenticationPrincipal User user,
            @PathVariable UUID id) {
        return incomeService.getByIdAndUserId(id, user.getId());
    }

    @GetMapping(Endpoints.SUMMARY_URI)
    public FullSummaryIncomeDto getSummary(
            @AuthenticationPrincipal User user) {
        return totalIncomeService.getSummaryIncomeByUserId(user.getId());
    }

    @PostMapping
    public IncomeDto create(
            @AuthenticationPrincipal User user,
            @RequestBody IncomeDto dto) {
        return incomeService.save(dto, user.getId());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @AuthenticationPrincipal User user,
            @PathVariable UUID id) {
        incomeService.deleteByIdAndUserId(id, user.getId());
        return ResponseEntity.ok().build();
    }
}
