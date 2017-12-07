
public class Story {

	public static enum StoryTypes{
		POLITICS, SCIENCE, SPORTS, ENTERTAINMENT, FINANCE, LOCAL
	}
	
	private int clicks = 0;
	private StoryTypes type;
	
	public Story(){
		
	}
	
	public Story(StoryTypes type){
		clicks = 0;
		this.type = type;
	}
	
	public int getClicks(){
		return clicks;
	}
	
	public void setClicks(int numClicks) {
		clicks = numClicks;
	}
	
	public void addOne(){
		clicks++;
	}
	
	public void setType(StoryTypes type){
		this.type = type;
	}
	
	public StoryTypes getType(){
		return type;
	}
}
