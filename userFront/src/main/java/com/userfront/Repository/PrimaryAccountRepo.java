package com.userfront.Repository;

import com.userfront.domain.PrimaryAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrimaryAccountRepo extends JpaRepository<PrimaryAccount, Long> {

    PrimaryAccount findByAccountNumber(int number);
}
