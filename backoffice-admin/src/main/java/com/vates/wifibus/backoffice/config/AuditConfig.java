package com.vates.wifibus.backoffice.config;

import java.util.Optional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * 
 * @author Gaston Napoli
 *
 */
@Configuration
@EnableJpaAuditing
public class AuditConfig {

	@Bean
	public AuditorAware<String> createAuditorProvider() {

		return new SecurityAuditor();

	}

	@Bean
	public AuditingEntityListener createAuditingListener() {

		return new AuditingEntityListener();

	}

	public static class SecurityAuditor implements AuditorAware<String> {

		@Override
		public String getCurrentAuditor() {

			Optional<Authentication> nullableAuth = Optional
					.ofNullable(SecurityContextHolder.getContext().getAuthentication());

			String username = nullableAuth.map(auth -> auth.getName()).orElse("<UNDEFINED>");

			return username;

		}

	}

}
