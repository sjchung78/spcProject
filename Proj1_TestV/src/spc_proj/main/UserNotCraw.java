package spc_proj.main;

public class UserNotCraw {

	private String screenName;
	private int crawled;
	private int crawlLevel;
	private int statusesCount;
	private int friendsCount; 
	private int followersCount;


	public UserNotCraw(String screenName, int crawled, int crawlLevel,
			int statusesCount, int friendsCount, int followersCount) {
		super();
		this.screenName = screenName;
		this.crawled = crawled;
		this.crawlLevel = crawlLevel;
		this.statusesCount = statusesCount;
		this.friendsCount = friendsCount;
		this.followersCount = followersCount;
	}

	public int getStatusesCount() {
		return statusesCount;
	}

	public void setStatusesCount(int statusesCount) {
		this.statusesCount = statusesCount;
	}

	public int getFriendsCount() {
		return friendsCount;
	}

	public void setFriendsCount(int friendsCount) {
		this.friendsCount = friendsCount;
	}

	public int getFollowersCount() {
		return followersCount;
	}

	public void setFollowersCount(int followersCount) {
		this.followersCount = followersCount;
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
