package in.Project.RestApi.service.impl;

import in.Project.RestApi.dto.ExpenseDTO;
import in.Project.RestApi.entity.ExpenseEntity;
import in.Project.RestApi.entity.ProfileEntity;
import in.Project.RestApi.exceptions.ResourceNotFoundException;
import in.Project.RestApi.repository.ExpenseRepository;
import in.Project.RestApi.service.AuthService;
import in.Project.RestApi.service.ExpenseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;

import java.nio.file.ReadOnlyFileSystemException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final ModelMapper modelMapper;
    private final AuthService authService;
    @Override
    public List<ExpenseDTO> getAllExpenses() {

        long loggedInProfileId=authService.getLoggedInProfile().getId();
        List<ExpenseEntity> list= expenseRepository.findByOwnerId(loggedInProfileId);
        log.info("Printing the data from list{}",list);
        List<ExpenseDTO> listofExpenses =list.stream().map(expenseEntity -> mapToExpenseDTO(expenseEntity)).collect(Collectors.toList());

        return listofExpenses;
    }

    /**
     * It will fetch the single expense details from database
     * @param expenseId
     * @return ExpenseDTO
     */
    @Override
    public ExpenseDTO getExpenseByExpenseId(String expenseId) {
        ExpenseEntity expenseEntity = getOptionalExpense(expenseId);
        log.info("Printing the expense entity deatils {}",expenseEntity);
        return mapToExpenseDTO(expenseEntity);
    }

    @Override
    public ExpenseDTO saveExpenseDetails(ExpenseDTO expenseDTO) {
        ProfileEntity profileEntity=authService.getLoggedInProfile();
        ExpenseEntity newExpenseEntity=mapToExpenseEntity(expenseDTO);
        newExpenseEntity.setExpenseId(UUID.randomUUID().toString());
        newExpenseEntity.setOwner(profileEntity);
        newExpenseEntity=expenseRepository.save(newExpenseEntity);
        log.info("Printing the new expense entity details {}",newExpenseEntity);
        return mapToExpenseDTO(newExpenseEntity);
    }

    @Override
    public ExpenseDTO updateExpenseDetails(ExpenseDTO expenseDTO, String expenseId) {
        ExpenseEntity existingExpense= getExpenseEntity(expenseId);
        ExpenseEntity updateExpenseEntity=mapToExpenseEntity(expenseDTO);
        updateExpenseEntity.setId(existingExpense.getId());
        updateExpenseEntity.setExpenseId(existingExpense.getExpenseId());
        updateExpenseEntity.setCreatedAt(existingExpense.getCreatedAt());
        updateExpenseEntity.setUpdatedAt(existingExpense.getUpdatedAt());
        updateExpenseEntity.setOwner(authService.getLoggedInProfile());
        updateExpenseEntity=expenseRepository.save(updateExpenseEntity);
        log.info("Printing the updated expense entity details{}",updateExpenseEntity);
        return mapToExpenseDTO(updateExpenseEntity);
    }

//    @Override
//    public Map<String, Double> getCategoryWiseExpenses() {
//        Long loggedInUserId = authService.getLoggedInProfile().getId(); // ✅ Get logged-in user ID
//        List<Object[]> results = expenseRepository.getCategoryWiseExpensesByUser(loggedInUserId);
//
//        Map<String, Double> categoryWiseData = new HashMap<>();
//        for (Object[] row : results) {
//            String category = (String) row[0];
//            Double totalAmount = ((Number) row[1]).doubleValue();
//            categoryWiseData.put(category, totalAmount);
//        }
//
//        log.info("Category-wise data for user {}: {}", loggedInUserId, categoryWiseData);
//        return categoryWiseData;
//    }


    private ExpenseEntity mapToExpenseEntity(ExpenseDTO expenseDTO) {
        return modelMapper.map(expenseDTO,ExpenseEntity.class);
    }

    @Override
    public void deleteExpenseByExpenseId(String expenseId) {
        ExpenseEntity expenseEntity= getExpenseEntity(expenseId);
        log.info("Printing the expense entity {}",expenseEntity);
        expenseRepository.delete(expenseEntity);
    }
    private ExpenseEntity getOptionalExpense(String expenseId) {
        return expenseRepository.findByExpenseId(expenseId)
                .orElseThrow(() -> new ResourceNotFoundException("Expense not found for the expense id" + expenseId));
    }

    private ExpenseEntity getExpenseEntity(String expenseId) {
        Long id= authService.getLoggedInProfile().getId();
        return expenseRepository.findByOwnerIdAndExpenseId(id,expenseId)
                .orElseThrow(() -> new ResourceNotFoundException("Expense not found for the expense Id"));
    }

    private ExpenseDTO mapToExpenseDTO(ExpenseEntity expenseEntity) {
        return modelMapper.map(expenseEntity,ExpenseDTO.class);
    }

}


