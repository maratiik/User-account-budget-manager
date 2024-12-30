package maratik.budget_manager.api.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import maratik.budget_manager.api.constants.Endpoints;
import maratik.budget_manager.api.services.SummaryService;
import maratik.budget_manager.api.services.UserSavingsAccountService;
import maratik.budget_manager.model.dto.UserSavingsAccountDto;
import maratik.budget_manager.model.entities.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(Endpoints.USER_SAVINGS)
@RequiredArgsConstructor
public class UserSavingsController {

    private final UserSavingsAccountService userSavingsAccountService;
    private final SummaryService summaryService;

    @GetMapping
    public List<UserSavingsAccountDto> getAll(
            @AuthenticationPrincipal User user) {
        return userSavingsAccountService.findAll(user.getId());
    }

    @GetMapping("/{id}")
    public UserSavingsAccountDto getById(
            @AuthenticationPrincipal User user,
            @PathVariable UUID id) {
        return userSavingsAccountService.findById(user.getId(), id);
    }

    @PostMapping
    public UserSavingsAccountDto create(
            @AuthenticationPrincipal User user,
            @Valid @RequestBody UserSavingsAccountDto dto) {
        return userSavingsAccountService.save(dto, user.getId());
    }
}
