package com.userfront.Repository;

import com.userfront.domain.PrimaryTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrimaryTransactionRepo extends JpaRepository<PrimaryTransaction, Long> {
}
