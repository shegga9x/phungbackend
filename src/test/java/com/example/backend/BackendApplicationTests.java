package com.example.backend;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.TimeUnit;

import org.awaitility.Awaitility;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import com.icegreen.greenmail.configuration.GreenMailConfiguration;
import com.icegreen.greenmail.junit5.GreenMailExtension;
import com.icegreen.greenmail.util.GreenMailUtil;
import com.icegreen.greenmail.util.ServerSetupTest;

import jakarta.mail.internet.MimeMessage;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BackendApplicationTests {

	@RegisterExtension
	static GreenMailExtension greenMail = new GreenMailExtension(ServerSetupTest.SMTP)
			.withConfiguration(GreenMailConfiguration.aConfig().withUser("duke", "springboot"))
			.withPerMethodLifecycle(false);

	@Autowired
	private JavaMailSender javaMailSender;

	@Test
	void shouldUseGreenMail() throws Exception {

		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setFrom("shegga9x@gmail.com");
		mail.setSubject("A new message for you");
		mail.setText("Hello GreenMail!");
		mail.setTo("shegga10x@gmail.com");

		javaMailSender.send(mail);

		// awaitility
		Awaitility.await().atMost(2, TimeUnit.SECONDS).untilAsserted(() -> {
			javax.mail.internet.MimeMessage[] receivedMessages = greenMail.getReceivedMessages();
			assertEquals(1, receivedMessages.length);

			javax.mail.internet.MimeMessage receivedMessage = receivedMessages[0];
			assertEquals("Hello GreenMail!", GreenMailUtil.getBody(receivedMessage));
			assertEquals(1, receivedMessage.getAllRecipients().length);
			assertEquals("test@greenmail.io", receivedMessage.getAllRecipients()[0].toString());
		});
	}
}