package com.userfront.Repository;

import com.userfront.domain.SavingsAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SavingsAccountRepo extends JpaRepository<SavingsAccount, Long>{
    SavingsAccount findByAccountNumber(int number);
}
