package au.com.redmars.otp.controllers;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import au.com.redmars.otp.entities.OneTimePassword;
import au.com.redmars.otp.repositoires.OneTimePasswordRepository;

@RestController
public class OneTimePasswordController {
	private static Logger log = LoggerFactory.getLogger(OneTimePasswordController.class);
	@Autowired
	private OneTimePasswordRepository oneTimePasswordRepository;
	private final Long expiryInterval = 5L * 60 * 1000; //5 minutes
	private String verificationResult;

	private Integer generateCode(){
		final Integer min = 100000;
		final Integer max = 999999;
		return (int) Math.floor(Math.random()*(max-min+1)+min);
	}

	@GetMapping("/otp/create/{id}")
	public @ResponseBody OneTimePassword getOTP(@PathVariable Integer id) {
		log.info("{} getOTP request received with id = {}", OneTimePasswordController.class.getName(),id );
		OneTimePassword otp = new OneTimePassword();
		//delete existing OTP if it exists
		oneTimePasswordRepository.findById(id)
		.ifPresent( entity -> 
			oneTimePasswordRepository.delete(entity)
		);
		otp.setId(id);
		otp.setCode(generateCode());
		otp.setExpires(new Date(System.currentTimeMillis()+expiryInterval));
		oneTimePasswordRepository.save(otp);
		return otp;
	}

	@PostMapping("/otp/verify")
	public @ResponseBody String verifyOTP(@RequestBody OneTimePassword oneTimePassword) {
		oneTimePasswordRepository.findByIdAndCode(oneTimePassword.getId(),oneTimePassword.getCode())
			.ifPresentOrElse(c -> {
				oneTimePasswordRepository.deleteById(oneTimePassword.getId());
				verificationResult="ok";}, 
							() -> {
				verificationResult="failed";} );
		return verificationResult;
	}
}
