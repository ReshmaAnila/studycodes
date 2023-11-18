package com.example.mail1.service;

import java.io.UnsupportedEncodingException;


import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.example.mail1.model.Register;
import com.example.mail1.repository.RegisterRepository;

import net.bytebuddy.utility.RandomString;
@Service
public class RegisterService {
	@Autowired
	
	RegisterRepository regRepo;
	@Autowired
	private JavaMailSender mailsender;
	

	public JavaMailSender getMailsender() {
		return mailsender;
	}
	public void setMailsender(JavaMailSender mailsender) {
		this.mailsender = mailsender;
	}
	public RegisterRepository getRegRepo() {
		return regRepo;
	}
	public void setRegRepo(RegisterRepository regRepo) {
		this.regRepo = regRepo;
	}
	public void register (Register reg, String url) throws UnsupportedEncodingException, MessagingException {
		String randomcode = RandomString.make(64);
				reg.setVerificationcode(randomcode);
				reg.setEnabled(false);
				regRepo.save(reg);
				SendVerificationEmail(reg,url);
	}
	private void SendVerificationEmail(Register reg, String url) throws UnsupportedEncodingException, MessagingException {
	    String toAddr = reg.getEmail();
	    String fromAddr = "reshmaanilasreekumar@gmail.com"; // Corrected email address
	    String senderName = "Reshma Resh";
	    String subject = "verifyemail";
	    String content = "Dear [[name]],<br> please click the below link to verify <br>"
	            + "<h3> "
	            + "<a href=\"" + url + "\">VERIFY</a> </h3> <br> THANK YOU";

	    MimeMessage message = mailsender.createMimeMessage();
	    MimeMessageHelper helper = new MimeMessageHelper(message);
	    helper.setFrom(fromAddr, senderName);
	    helper.setTo(toAddr);
	    helper.setSubject(subject); // Corrected method
	    content = content.replace("[[name]]", reg.getName());
	    String urls = url + "/verify?code=" + reg.getVerificationcode();
	    content = content.replace("[[URL]]", urls); // Corrected variable name
	    helper.setText(content, true);
	    mailsender.send(message);
	}

					
		public boolean verify(String code) {
		Register reg = regRepo.findByVerificationcode(code);
		if(reg==null||reg.isEnabled()) {
			return false;
		}else {
			reg.setVerificationcode(null);
			reg.setEnabled(true);
			regRepo.save(reg);
			return true;
		}
		
	}

}
