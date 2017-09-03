package util;


//@Singleton
public class DbCleaner {

	// @Lock(LockType.READ)
	// @Schedule(second = "*/5", minute = "*", hour = "*", persistent = false)
	public void cleanTransfers() {
		System.out.println("cleaner work");
	}
}
