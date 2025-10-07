package in.Project.RestApi.io;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class ExpenseRequest {

    @NotBlank(message = "Expense name is Required")
    @Size(min=3,message="Expense name must be atleast 3 characters")
    private String name;
    private String note;
    @NotBlank(message = "Expense category is Required")
    private String category;
    @NotNull(message="Expense date is Required")
    private Date date;
    @NotNull(message="Expense amount is Required")
    private BigDecimal amount;
}
