package in.Project.RestApi.repository;

import in.Project.RestApi.entity.ExpenseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * JPA respository for expense resource
 */
public interface ExpenseRepository extends JpaRepository<ExpenseEntity, Long> {

    /**
     * It will fetch the single expense details from database
     * @param expenseId
     * @return Optional
     */
    Optional<ExpenseEntity>findByExpenseId(String expenseId);

    List<ExpenseEntity> findByOwnerId(long id);

    Optional<ExpenseEntity>findByOwnerIdAndExpenseId(Long id,String expenseId);

//    @Query(value = "SELECT category, SUM(amount) FROM tbl_expenses WHERE owner_id = :ownerId GROUP BY category", nativeQuery = true)
//    List<Object[]> getCategoryWiseExpensesByUser(@Param("ownerId") Long ownerId);



}
