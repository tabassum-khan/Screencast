/* Scheduler Thread
 * The scheduler controls the time after which it calls the takeScreenshot method in Main class.
 * It implements the concept of JAVA THREAD.
 */

package screencast;

class Scheduler implements Runnable {
	
	private boolean exit; //flag to stop the thread
	private Thread t;
	private String path;
	
	//Initialising the Scheduler which also starts the thread
	Scheduler(String path){
		t = new Thread(this);
		exit = false;
		this.path  = path;
		t.start();
	}

	@Override
	public void run() {
		System.out.println("Scheduler is running...");
		Main m = new Main();
		
		//Run the takeScreenshot() until exit is not true;
		while (!exit) {
			m.takeScreenshot(path);
			try {
				Thread.sleep(300000); // Pause the thread for 5 mins
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		System.out.println("Scheduler has stopped.");
	}
	
	// Stops the thread
	public void stop() {
		exit = true;
	}

}


//public void schedule(String path) throws InterruptedException {
//	Timer timer = new Timer();
//	System.out.println("In Scheduler class");
//	
//	timer.schedule(new TimerTask() {
//		@Override
//		public void run() {
//			System.out.println("In Scheduler run method");
//			new Main().takeScreenshot(path);
//		}
//	}, 0, 2000);
//}