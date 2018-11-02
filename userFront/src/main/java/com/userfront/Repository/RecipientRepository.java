package com.userfront.Repository;

import com.userfront.domain.Recipient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipientRepository extends JpaRepository<Recipient, Long> {
    Recipient findByName(String recipientName);
    void deleteByName(String recipientName);
}
