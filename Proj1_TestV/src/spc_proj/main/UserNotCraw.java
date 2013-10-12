package spc_proj.main;

public class UserNotCraw {

	private String screenName;
	private int crawled;
	private int crawlLevel;

	public UserNotCraw(String screenName, int crawlLevel) {
		super();
		this.screenName = screenName;
		this.crawlLevel = crawlLevel;
	}

	public String getScreenName() {
		return screenName;
	}

	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}

	public int getCrawled() {
		return crawled;
	}

	public void setCrawled(int crawled) {
		this.crawled = crawled;
	}

	public int getCrawlLevel() {
		return crawlLevel;
	}

	public void setCrawlLevel(int crawlLevel) {
		this.crawlLevel = crawlLevel;
	}
	
}
