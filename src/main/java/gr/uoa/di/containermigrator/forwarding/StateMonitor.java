package gr.uoa.di.containermigrator.forwarding;

/**
 * @author Kyriakos Lesgidis
 * @email klesgidis@di.uoa.gr
 */
public class StateMonitor {
	private static StateMonitor ourInstance = new StateMonitor();

	public static StateMonitor getInstance() {
		return ourInstance;
	}

	private boolean isMigrating;

	private StateMonitor() {
		this.isMigrating = false;
	}

	public synchronized void checkState() throws InterruptedException {
		while (this.isMigrating)
			wait();
	}

	public synchronized void migrationState(boolean value) {
		this.isMigrating = value;
		notifyAll();
	}
}
