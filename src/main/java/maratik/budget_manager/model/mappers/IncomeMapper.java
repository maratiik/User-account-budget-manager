package maratik.budget_manager.model.mappers;

import lombok.RequiredArgsConstructor;
import maratik.budget_manager.api.exceptions.EntityNotFoundException;
import maratik.budget_manager.api.repositories.UserRepository;
import maratik.budget_manager.model.dto.income.IncomeDto;
import maratik.budget_manager.model.dto.income.IncomeDtoWithId;
import maratik.budget_manager.model.entities.Income;
import maratik.budget_manager.model.entities.TotalAmount;
import maratik.budget_manager.model.entities.User;
import org.hibernate.LazyInitializationException;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class IncomeMapper {

    private final UserRepository userRepository;

    public IncomeDto toIncomeDto(Income income) throws LazyInitializationException {
        return new IncomeDto(
                income.getDate(),
                income.getAmount(),
                income.getUser().getUsername()
        );
    }

    public IncomeDto toSummaryAccountDto(TotalAmount totalIncome) throws LazyInitializationException {
        return new IncomeDto(
                totalIncome.getUpdatedAt().toLocalDate(),
                totalIncome.getAmount(),
                totalIncome.getUser().getUsername()
        );
    }

    public Income toEntity(IncomeDto dto, UUID userId) {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new EntityNotFoundException("User not found"));
        Income income = new Income();
        income.setAmount(dto.amount());
        income.setDate(dto.date());
        income.setUser(user);
        return income;
    }
}
