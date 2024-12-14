package maratik.budget_manager.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "account_income")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AccountIncome {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "date", nullable = false, updatable = false)
    private LocalDate date = LocalDate.now();

    @Column(name = "amount", precision = 10, scale = 2, nullable = false)
    private BigDecimal amount;

    @Column(name = "proportion", nullable = false)
    private float proportion;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", updatable = false, nullable = false)
    private User user;

    public BigDecimal calculateAmount(BigDecimal incomeAmount) {
        amount = incomeAmount
                .multiply(BigDecimal.valueOf(proportion));
        return amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountIncome that = (AccountIncome) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "AccountIncome{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", amount=" + amount +
                ", proportion=" + proportion +
                '}';
    }
}
