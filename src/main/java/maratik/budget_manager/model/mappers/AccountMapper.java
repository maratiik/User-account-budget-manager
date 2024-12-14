package maratik.budget_manager.model.mappers;

import lombok.RequiredArgsConstructor;
import maratik.budget_manager.api.exceptions.EntityNotFoundException;
import maratik.budget_manager.api.repositories.AccountIncomeRepository;
import maratik.budget_manager.api.repositories.UserRepository;
import maratik.budget_manager.model.dto.account.AccountDto;
import maratik.budget_manager.model.entities.AccountIncome;
import maratik.budget_manager.model.entities.TotalAmount;
import maratik.budget_manager.model.entities.User;
import org.hibernate.LazyInitializationException;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AccountMapper {

    private final AccountIncomeRepository accountIncomeRepository;
    private final UserRepository userRepository;

    public AccountDto toAccountDto(AccountIncome income) throws LazyInitializationException {
        return new AccountDto(
                income.getName(),
                income.getDate(),
                income.getAmount(),
                income.getProportion(),
                income.getUser().getUsername()
        );
    }

    public AccountDto toSummaryAccountDto(TotalAmount totalIncome) throws LazyInitializationException {
        float proportion = 100;
        if (totalIncome.getName() != null && !totalIncome.getName().isEmpty()) {
            proportion = accountIncomeRepository.findByName(totalIncome.getName()).getFirst().getProportion();
        }
        return new AccountDto(
                totalIncome.getName(),
                totalIncome.getUpdatedAt().toLocalDate(),
                totalIncome.getAmount(),
                proportion,
                totalIncome.getUser().getUsername()
        );
    }

    public AccountIncome toEntity(AccountDto dto, UUID userId) {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new EntityNotFoundException("User not found"));
        AccountIncome account = new AccountIncome();
        account.setAmount(dto.amount());
        account.setDate(dto.date());
        account.setName(dto.name());
        account.setProportion(dto.proportion());
        account.setUser(user);
        return account;
    }
}
