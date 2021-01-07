package com.app.registration.respository;

import com.app.registration.model.OTPDetails;
import com.app.registration.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OTPDetailsRepository extends JpaRepository<OTPDetails, Long> {

    List<OTPDetails> findByUser(User user);

    OTPDetails findByUserAndStatus(User user, String otpSent);
}
