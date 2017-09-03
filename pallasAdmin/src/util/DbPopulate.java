package util;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import persistence.Transfer;
import services.interfaces.TransferServiceLocal;

@Singleton
@Startup
public class DbPopulate {

	@EJB
	private TransferServiceLocal transferServiceLocal;

	public DbPopulate() {

	}

	@PostConstruct
	public void createData() {
		Transfer transfer = new Transfer();
		transfer.setCodeTransfer("A001");
	}
}
