package com.userfront.Repository;

import com.userfront.domain.SavingsTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SavingsTransactionRepo extends JpaRepository<SavingsTransaction, Long>{
}
