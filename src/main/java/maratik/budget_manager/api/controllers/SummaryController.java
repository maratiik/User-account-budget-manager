package maratik.budget_manager.api.controllers;

import lombok.RequiredArgsConstructor;
import maratik.budget_manager.api.constants.Endpoints;
import maratik.budget_manager.api.services.TotalIncomeService;
import maratik.budget_manager.model.dto.summary.FullSummaryIncomeDto;
import maratik.budget_manager.model.entities.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(Endpoints.SUMMARY)
@RequiredArgsConstructor
public class SummaryController {

    private final TotalIncomeService totalIncomeService;

    @GetMapping
    public FullSummaryIncomeDto getSummary(
            @AuthenticationPrincipal User user,
            @RequestParam(name = "name", required = false) List<String> names) {
        return totalIncomeService.getTotalIncome(user.getId(), names);
    }
}
