package executor;

public class Worker implements Runnable {
	String command;

	public Worker(String cmd) {
		this.command = cmd;
	}

	@Override
	public void run() {
		System.out.println(Thread.currentThread().getName()
				+ " Start. Command = " + command);
		executeCmd();
		System.out.println(Thread.currentThread().getName() + " End.");

	}

	private void executeCmd() {
		try {
			Thread.sleep(5 * 1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
