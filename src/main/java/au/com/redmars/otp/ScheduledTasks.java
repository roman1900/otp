package au.com.redmars.otp;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import au.com.redmars.otp.repositoires.OneTimePasswordRepository;

@Component
public class ScheduledTasks {
	private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

	@Autowired
	OneTimePasswordRepository oneTimePasswordRepository;

	@Scheduled(fixedDelayString = "${scheduler.clean.otpdb}")
	public void cleanUpOtpTable() {
		log.info("OTP table cleanup : commencing");
		long count = oneTimePasswordRepository.count();
		log.info("OTP table cleanup : {} records exist", count);
		oneTimePasswordRepository.deleteAll(oneTimePasswordRepository.findByExpiresLessThanEqual(new Date()));
		count = count - oneTimePasswordRepository.count();
		log.info("OTP table cleanup : {} records deleted", count);
		log.info("OTP table cleanup : completed");
	}

}
