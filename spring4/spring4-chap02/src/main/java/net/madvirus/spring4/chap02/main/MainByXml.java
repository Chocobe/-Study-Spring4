package net.madvirus.spring4.chap02.main;

import org.springframework.context.support.GenericXmlApplicationContext;

import net.madvirus.spring4.chap02.AuthException;
import net.madvirus.spring4.chap02.AuthenticationService;
import net.madvirus.spring4.chap02.PasswordChangeService;
import net.madvirus.spring4.chap02.UserNotFoundException;

public class MainByXml {
	public static void main(String[] args) {
		GenericXmlApplicationContext context = new GenericXmlApplicationContext("classpath:config.xml");
		AuthenticationService authSvc = context.getBean("authenticationService", AuthenticationService.class);
		
		runAuthAndCatchAuthEx(authSvc, "bkchoi", "1111");
		runAuthAndCatchAuthEx(authSvc, "bkchoi", "11111");
		runAuthAndCatchAuthEx(authSvc, "bkchoi", "111111");
		
		try {
			authSvc.authenticate("bkchoi",  "1234");
		
		} catch(UserNotFoundException e) { }
		
		PasswordChangeService pwChgSvc = context.getBean(PasswordChangeService.class);
		pwChgSvc.changePassword("bkchoi", "1234", "5678");
		
		runAuthAndCatchAuthEx(authSvc, "bkchoi", "1234");
		authSvc.authenticate("bkchoi", "5678");
		
		context.close();
	}
	
	
	private static void runAuthAndCatchAuthEx(AuthenticationService authSvc, String userId, String password) {
		try {
			authSvc.authenticate(userId, password);
			
		} catch(AuthException e) { }
	}
}
