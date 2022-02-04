package au.com.redmars.otp.repositoires;

import java.util.Date;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import au.com.redmars.otp.entities.OneTimePassword;

public interface OneTimePasswordRepository extends JpaRepository <OneTimePassword,Integer> {
	public Iterable<OneTimePassword> findByExpiresLessThanEqual(Date expires);

	public Optional<OneTimePassword> findByIdAndCode(Integer id, Integer code);
}
