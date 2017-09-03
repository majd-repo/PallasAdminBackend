package rest;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import persistence.Visitor;
import services.interfaces.VisitorServiceLocal;
import util.Encrypter;
import util.HtmlEmailSender;
import util.MailContentBuilder;

@RequestScoped
@Path("activation")
public class ActivationResource {

	@Resource(name = "java:jboss/mail/postfix")
	private Session session;

	@Inject
	private VisitorServiceLocal visitorServiceLocal;

	@GET
	@Path("{id}")
	public Response doActivateVisitor(@PathParam("id") String id) {

		// decrypt the id before sending e-mail
		Integer decrypted_id = Integer
				.parseInt(Encrypter.decrypt(id.toString()));

		Visitor visitor = visitorServiceLocal.findVisitorById(decrypted_id);
		if (visitor != null && visitor.getActive() == true) {
			return Response.ok(
					"Your PALLAS T-Secure account is already active.").build();
		}

		try {
			visitorServiceLocal.activateVisitor(decrypted_id, true);
			HtmlEmailSender.sendHtmlEmail(session, "Account activated",
					MailContentBuilder.buildActivationMail(visitor),
					visitor.getEmail());

		} catch (AddressException e) {
			// TODO Auto-generated catch block
			Logger.getLogger(ActivationResource.class.getName()).log(
					Level.WARNING, "Cannot send email", e);
			return Response.ok("Cannot send email").build();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			Logger.getLogger(ActivationResource.class.getName()).log(
					Level.WARNING, "Cannot send email", e);
			return Response.ok("Cannot send email").build();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Logger.getLogger(ActivationResource.class.getName()).log(
					Level.WARNING, "Cannot send email", e);
			return Response.ok("Cannot send email").build();
		}

		return Response
				.ok("Congratulations,<br>Your PALLAS T-Secure account has been activated.<br>Please follow instructions sent on your email to download the App and log in .")
				.build();
	}
}
