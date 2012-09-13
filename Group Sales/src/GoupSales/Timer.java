package GoupSales;

import java.util.ArrayList;
import java.util.List;

public class Timer extends Thread {
	private int seconds;
	private boolean kill = false;
	private List<ITimerListener> listeners =new ArrayList<ITimerListener>();
	
	
	Timer(int seconds){
		this.seconds = seconds;
	}
	
	public void addListener(ITimerListener listener){
		listeners.add(listener);
	}
	
	@Override
	public void run(){
		while(!kill){
				try {
					sleep(seconds*1000);
					for(ITimerListener l : listeners) l.onTimer(new TimerEvent());
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}
	
	public void end(){
		kill = true;
	}

}
