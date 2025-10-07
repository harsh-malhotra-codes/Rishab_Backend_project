package in.Project.RestApi.service;

import in.Project.RestApi.dto.ExpenseDTO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface ExpenseService {
    List<ExpenseDTO> getAllExpenses();

    /**
     * It will fetch the single expense details from database
     * @param expenseId
     * @return ExpenseDTO
     */
    ExpenseDTO getExpenseByExpenseId(String expenseId);

    void deleteExpenseByExpenseId(String expenseId);


    ExpenseDTO saveExpenseDetails(ExpenseDTO expenseDTO);

    ExpenseDTO updateExpenseDetails(ExpenseDTO expenseDTO,String expenseId);


//    Map<String, Double> getCategoryWiseExpenses();


}
