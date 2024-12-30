package maratik.budget_manager.api.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import maratik.budget_manager.api.constants.Endpoints;
import maratik.budget_manager.api.services.IncomeService;
import maratik.budget_manager.api.services.SummaryService;
import maratik.budget_manager.model.dto.IncomeDto;
import maratik.budget_manager.model.dto.SummaryDto;
import maratik.budget_manager.model.entities.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(Endpoints.INCOME)
@RequiredArgsConstructor
public class IncomeController {

    private final SummaryService summaryService;
    private final IncomeService incomeService;

    @GetMapping
    public List<IncomeDto> getAll(
            @AuthenticationPrincipal User user) {
        return incomeService.findAll(user.getId());
    }

    @GetMapping("/{id}")
    public IncomeDto getById(
            @AuthenticationPrincipal User user,
            @PathVariable UUID id) {
        return incomeService.findById(id, user.getId());
    }

    @GetMapping(Endpoints.SUMMARY_URI)
    public SummaryDto getSummary(
            @AuthenticationPrincipal User user) {
        return summaryService.getIncomeSummary(user.getId());
    }

    @PostMapping
    public IncomeDto create(
            @AuthenticationPrincipal User user,
            @Valid @RequestBody IncomeDto dto) {
        return incomeService.save(dto, user.getId());
    }

    @PutMapping
    public IncomeDto update(
            @AuthenticationPrincipal User user,
            @RequestBody IncomeDto dto) {
        return incomeService.update(dto, user.getId());
    }
}
