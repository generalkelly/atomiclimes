package io.atomiclimes.security.server.controller;

import org.springframework.boot.actuate.trace.http.HttpTrace.Principal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AtomicLimesUserController {
	@GetMapping("/user/me")
	public Principal user(Principal principal) {
		String authenticatedUser = SecurityContextHolder.getContext().getAuthentication().getName();
		return new Principal(authenticatedUser);
	}
//	@ResponseBody
//	public String currentUserName(Authentication authentication) {
//		return authentication.getName();
//	}
}