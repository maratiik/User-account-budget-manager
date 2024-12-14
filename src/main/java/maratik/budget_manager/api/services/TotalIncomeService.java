package maratik.budget_manager.api.services;

import lombok.RequiredArgsConstructor;
import maratik.budget_manager.api.repositories.TotalAmountRepository;
import maratik.budget_manager.model.dto.summary.FullSummaryIncomeDto;
import maratik.budget_manager.model.entities.TotalAmount;
import maratik.budget_manager.model.mappers.SummaryIncomeMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TotalIncomeService {

    private final TotalAmountRepository totalAmountRepository;
    private final SummaryIncomeMapper summaryIncomeMapper;

    public FullSummaryIncomeDto getTotalIncome(
            UUID userId,
            List<String> names
    ) {
        if (names.isEmpty()) {
            return getFullSummaryIncomeByUserId(userId);
        }

        return getAccountSummaryIncomeByUserIdAndNameSet(userId, names);
    }

    public FullSummaryIncomeDto getFullSummaryIncomeByUserId(UUID userId) {
        return summaryIncomeMapper.toSummaryDto(
                totalAmountRepository.findAllByUserId(userId)
        );
    }

    public FullSummaryIncomeDto getAccountSummaryIncomeByUserIdAndNameSet(UUID userId, Collection<String> names) {
        if (names == null || names.isEmpty()) {
            return summaryIncomeMapper.toSummaryDto(
                    totalAmountRepository.findAllByUserIdNameNotNull(userId)
            );
        }
        return summaryIncomeMapper.toSummaryDto(
                totalAmountRepository.findAllByUserIdAndNameSet(userId, names)
        );
    }

    public FullSummaryIncomeDto getSummaryIncomeByUserId(UUID userId) {
        return summaryIncomeMapper.toSummaryDto(
                totalAmountRepository.findAllByUserIdNameNull(userId)
        );
    }

    public void saveOrUpdate(String name, BigDecimal amountToAdd, UUID userId) {
        Optional<TotalAmount> totalOptional;
        if (name == null || name.isEmpty()) {
            totalOptional = totalAmountRepository.findByUserIdNameNull(userId);
        } else {
            totalOptional = totalAmountRepository.findByUserIdAndName(userId, name);
        }

        TotalAmount totalAmount;
        if (totalOptional.isPresent()) {
            totalAmount = totalOptional.get();
            totalAmount.add(amountToAdd);
        } else {
            totalAmount = new TotalAmount();
            totalAmount.setName(name);
            totalAmount.setAmount(amountToAdd);
        }

        totalAmountRepository.save(totalAmount);
    }

    public void deleteAmount(String name, BigDecimal amountToRemove, UUID userId) {
        Optional<TotalAmount> totalOptional;
        if (name == null || name.isEmpty()) {
            totalOptional = totalAmountRepository.findByUserIdNameNull(userId);
        } else {
            totalOptional = totalAmountRepository.findByUserIdAndName(userId, name);
        }

        if (totalOptional.isPresent()) {
            TotalAmount totalAmount;
            totalAmount = totalOptional.get();
            totalAmount.subtract(amountToRemove);
            totalAmountRepository.save(totalAmount);
        }
    }
}
