package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import persistence.Staff;
import persistence.Visitor;

public class MailContentBuilder {

	public static String buildCreationtionMail(Visitor visitor)
			throws IOException {
		StringBuilder accountCreateionTemplate = new StringBuilder();
		BufferedReader reader = new BufferedReader(new InputStreamReader(Thread
				.currentThread().getContextClassLoader()
				.getResourceAsStream("account-created.tpl")));
		// encrypt the id before sending e-mail
		String encrypted_id = Encrypter.encrypt(visitor.getId().toString());
		for (String line = reader.readLine(); line != null; line = reader
				.readLine()) {
			line = line.replaceAll("FirstName", visitor.getFirstName());
			line = line.replaceAll("LastName", visitor.getLastName());
			line = line.replaceAll("Id", encrypted_id);
			line = line.replaceAll("Company", visitor.getCompany().getName());
			accountCreateionTemplate.append(line);
		}

		return accountCreateionTemplate.toString();
	}

	public static String buildActivationMail(Visitor visitor)
			throws IOException {
		StringBuilder accountActivationTemplate = new StringBuilder();
		BufferedReader reader = new BufferedReader(new InputStreamReader(Thread
				.currentThread().getContextClassLoader()
				.getResourceAsStream("account-activation.tpl")));

		for (String line = reader.readLine(); line != null; line = reader
				.readLine()) {
			line = line.replaceAll("FirstName", visitor.getFirstName());
			line = line.replaceAll("LastName", visitor.getLastName());
			line = line.replaceAll("Login", visitor.getLogin());
			line = line.replaceAll("Pwd", visitor.getPassword());
			accountActivationTemplate.append(line);
		}

		return accountActivationTemplate.toString();
	}

	public static String buildAdviseMail(Staff coordinator, String title,
			String message) throws IOException {
		StringBuilder accountActivationTemplate = new StringBuilder();
		BufferedReader reader = new BufferedReader(new InputStreamReader(Thread
				.currentThread().getContextClassLoader()
				.getResourceAsStream("coordinator-advise.tpl")));

		for (String line = reader.readLine(); line != null; line = reader
				.readLine()) {
			line = line.replace("TITLE", title);
			line = line.replace("MESSAGE", message);
			line = line.replace("FirstName", coordinator.getFirstName());
			line = line.replace("LastName", coordinator.getLastName());

			accountActivationTemplate.append(line);
		}

		return accountActivationTemplate.toString();
	}
}
