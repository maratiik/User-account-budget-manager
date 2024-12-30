package maratik.budget_manager.api.controllers;

import lombok.RequiredArgsConstructor;
import maratik.budget_manager.api.constants.Endpoints;
import maratik.budget_manager.api.services.SummaryService;
import maratik.budget_manager.model.dto.SummaryDto;
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

    private final SummaryService summaryService;

    @GetMapping
    public SummaryDto getSummary(
            @AuthenticationPrincipal User user) {
        return summaryService.getFullSummary(user.getId());
    }

    @GetMapping(Endpoints.SHARED_URI)
    public SummaryDto getSharedSummary(
            @AuthenticationPrincipal User user,
            @RequestParam(name = "name", required = false) List<String> names) {
        return summaryService.getSharedIncomeSummary(user.getId(), names);
    }

    @GetMapping(Endpoints.INCOME_URI)
    public SummaryDto getIncomeSummary(
            @AuthenticationPrincipal User user) {
        return summaryService.getIncomeSummary(user.getId());
    }
}
