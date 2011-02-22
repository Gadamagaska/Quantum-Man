package core;


import java.awt.Image;
import java.util.ArrayList;

public class Animation {

	private ArrayList<Scene> scenes;
	private int sceneIndex;
	private long movieTime;
	private long totalTime;
	
	public Animation(){
		scenes = new ArrayList<Scene>();
		totalTime = 0;
		start();
	}
	
	public synchronized void start() {
		movieTime = 0;
		sceneIndex = 0;
	}
	
	public synchronized void update(long timePassed){
		if(scenes.size() > 1){
			movieTime += timePassed;
			if(movieTime >= totalTime){
				movieTime = 0;
				sceneIndex = 0;
			}
			while(movieTime > getScene(sceneIndex).getEndTime()){
				sceneIndex++;
			}
		}
	}
	
	public synchronized Image getImage(){
		if(scenes.size() == 0){
			return null;
		}else{
			return getScene(sceneIndex).getPic();
		}
	}

	private Scene getScene(int sceneIndex) {
		return scenes.get(sceneIndex);
	}

	public synchronized void addScene(Image i, long t){
		totalTime += t;
		scenes.add(new Scene(i,totalTime));
	}
	
	//// PRIVATE INNER CLASS /////
	private class Scene {

		private long endTime;
		private Image img;
		
		public Scene(Image i,long totalTime){
			endTime = totalTime;
			img = i;
		}
		
		public long getEndTime(){
			return endTime;
		}
		
		public Image getPic(){
			return img;
		}
	}

}
