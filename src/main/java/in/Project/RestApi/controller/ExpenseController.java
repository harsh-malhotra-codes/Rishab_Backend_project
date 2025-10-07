package in.Project.RestApi.controller;

import in.Project.RestApi.dto.ExpenseDTO;
import in.Project.RestApi.io.ExpenseRequest;
import in.Project.RestApi.io.ExpenseResponse;
import in.Project.RestApi.service.ExpenseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ExpenseController {


    private final ExpenseService expenseService;
    private final ModelMapper modelMapper;

    /**
     * It will fetch the  expense from database
     * @return list
     */
    @GetMapping("/expenses")
    public List<ExpenseResponse> getExpenses()
    {
        log.info("API get /expenses called");
        List<ExpenseDTO> list= expenseService.getAllExpenses();
        log.info("Printing the data from service{}", list);

        List<ExpenseResponse> response=list.stream().map(expenseDTO -> mapToExpenseResponse(expenseDTO)).collect(Collectors.toList());

        return response;
    }

    /**
     * It will fetch the single expense from database
     * @return ExpenseResponse
     */
    @GetMapping("/expenses/{expenseId}")
    public ExpenseResponse getExpenseById(@PathVariable String expenseId){
        log.info("Printing the expense id {}",expenseId);
        ExpenseDTO expenseDTO=expenseService.getExpenseByExpenseId(expenseId);
        return mapToExpenseResponse(expenseDTO);
    }


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/expenses")
    public ExpenseResponse saveExpenseDetails(@Valid @RequestBody ExpenseRequest expenseRequest) {
        log.info("API POST /expenses called{}",expenseRequest);
        ExpenseDTO expenseDTO = mapToExpenseDTO(expenseRequest);
        expenseDTO= expenseService.saveExpenseDetails(expenseDTO);
        log.info("Printing the expense dto {}",expenseDTO);
        return mapToExpenseResponse(expenseDTO);
    }

    private ExpenseDTO mapToExpenseDTO(@Valid ExpenseRequest expenseRequest) {
        return modelMapper.map(expenseRequest,ExpenseDTO.class);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/expenses/{expenseId}")
    public void deleteExpenseByExpenseId(@PathVariable String expenseId){
        log.info("API Delete /expenses/{} called",expenseId);
        expenseService.deleteExpenseByExpenseId(expenseId);
    }
    private ExpenseResponse mapToExpenseResponse(ExpenseDTO expenseDTO) {
        return modelMapper.map(expenseDTO,ExpenseResponse.class);
    }

    @PutMapping("/expenses/{expenseId}")
    public ExpenseResponse updateExpenseDetails(@RequestBody ExpenseRequest expenseRequest, @PathVariable String expenseId){
        log.info("API PUT /expenses/ {} request body {}",expenseId,expenseRequest);
        ExpenseDTO updatedExpenseDTO = mapToExpenseDTO(expenseRequest);
        updatedExpenseDTO=expenseService.updateExpenseDetails(updatedExpenseDTO,expenseId);
        log.info("Printing the updated expense details{}",updatedExpenseDTO);
        return mapToExpenseResponse(updatedExpenseDTO);
    }

//    @GetMapping("/reports")
//    public ResponseEntity<Map<String, Double>> getCategoryWiseReport() {
//        return ResponseEntity.ok(expenseService.getCategoryWiseExpenses());
//    }
}
