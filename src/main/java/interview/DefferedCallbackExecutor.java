class DefferedCallbackExecutor{
	private static Random random = new Random(System.currentTimeMillis());
	
	PriorityQueue<CallBack> q= new PriorityQueue<CallBack>(new Comparartor<CallBack>(){
		public int compare(CallBack c1,CallBack c2){
			return (int) (c1.executeAt - c2.executeAt);
		}
	});
	
	ReentrantLock lock = new ReentrantLock();
	
	Condition newCallBackArrived = lock.newCondition();
	
	private long findSleepDuration(){
		long currentTime = System.currentTimeMillis();
		return q.peek().executeAt - currentTime;
	}
	
	public void start() throws InterruptedException{
		long sleepFor = 0;
		while(true){
			lock.lock();
			while(q.size() == 0){
				newCallBackArrived.await();
			}
			
			while(q.size()!=0){
				sleepFor = findSleepDuration();

				if(sleepFor <= 0)
					break;
				
				newCallBackArrived.await(sleepFor,TimeUnit.MILLISECONDS);
			}
			
			CallBack cb = q.poll();
			System.out.println("Executed at: " +System.currentTimeMillis()/1000 +"required at " +cb.executeAt + "message"+cb.message);
			lock.unlock();
		}
	}
	
	
	public void registerCallback(CallBack callback){
		lock.lock();
		q.add(callback);
		newCallBackArrived.signal();
		lock.unlock();
	}
	
	
	static class CallBack{
		long executeAt;
		String message;
		
		public CallBack(long executeAfter, String message){
			this.executeAt = System.currentTimeMillis()+ (executeAfter *1000);
			this.message = message;
		}
	}
	
	public static void runTestTenCallbacks() throws InterruptedException {
		Set<Thread> allThreads = new HashSet<>();
		final DefferedCallbackExecutor defferedCallbackExecutor = new DefferedCallbackExecutor();
		Thread service  = new Thread(new Runnable(){
			public void run(){
				try{
					defferedCallbackExecutor.start();
				}catch(InterruptedException e){
				}
				
			}
		
		});
		service.start();
		
		for(int i=0;i<10;i++){
			Thread thread = new Thread(new Runnable(){
				public void run(){
					try{
						CallBack cb = new CallBack(1, "Hello this is "+Thread.currentThread().get());
						defferedCallbackExecutor.registerCallback(cb);
						
						.start();
					}catch(InterruptedException e){
					}
					
				}
			
			});
			
			thread.setName("Thread_"+(i+1));
			thread.start();
			allThreads.add(thread);
			Thread.sleep(1000);
		}
		
		for(Thread t: allThreads){
			t.join();
		}
	}
}