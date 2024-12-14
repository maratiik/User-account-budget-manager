package maratik.budget_manager.api.controllers;

import lombok.RequiredArgsConstructor;
import maratik.budget_manager.api.constants.Endpoints;
import maratik.budget_manager.model.dto.account.AccountDto;
import maratik.budget_manager.model.dto.account.AccountDtoWithId;
import maratik.budget_manager.model.dto.summary.FullSummaryIncomeDto;
import maratik.budget_manager.model.entities.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(Endpoints.ACCOUNT)
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;
    private final TotalIncomeService totalIncomeService;

    @GetMapping
    public List<AccountDto> getAll(
            @AuthenticationPrincipal User user,
            @RequestParam(name = "name", required = false) List<String> names) {
        return accountService.getAllByUserIdAndNames(user.getId(), names);
    }

    @GetMapping("/{id}")
    public AccountDto getById(
            @AuthenticationPrincipal User user,
            @PathVariable UUID id) {
        return accountService.getByIdAndUserId(id, user.getId());
    }

    @GetMapping(Endpoints.SUMMARY_URI)
    public FullSummaryIncomeDto getSummary(
            @AuthenticationPrincipal User user,
            @RequestParam(name = "name", required = false) List<String> names) {
        return totalIncomeService.getAccountSummaryIncomeByUserIdAndNameSet(user.getId(), names);
    }

    @PostMapping
    public AccountDto create(
            @AuthenticationPrincipal User user,
            @RequestBody AccountDto dto) {
        return accountService.save(dto, user.getId());
    }

    @PutMapping
    public AccountDto update(
            @AuthenticationPrincipal User user,
            @RequestBody AccountDtoWithId dto) {
        return accountService.update(dto, user.getId());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @AuthenticationPrincipal User user,
            @PathVariable UUID id) {
        accountService.deleteByIdAndUserId(id, user.getId());
        return ResponseEntity.ok().build();
    }
}
