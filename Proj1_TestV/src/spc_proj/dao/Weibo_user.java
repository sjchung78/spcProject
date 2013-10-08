package spc_proj.dao;

public class Weibo_user {

	private String id           = null;
	private String screen_name  = null;
	private String uid          = null;
	private String blog_url     = null;
	private String etc1         = null;
	private String etc2         = null;
	private String etc3         = null;
	private String insert_date  = null;
	private String request_id1  = null;
	private String request_id2  = null;
	private int url_score1   = -1;
	private int url_score2   = -1;
	private int follower_cnt = -1;
	private int friend_cnt   = -1;
	private int id_score     = -1;
	
	public Weibo_user(String id, String screen_name, String uid,
			String blog_url, String etc1, String etc2, String etc3,
			String insert_date, String request_id1, String request_id2,
			int url_score1, int url_score2, int follower_cnt, int friend_cnt,
			int id_score) {
		super();
		this.id = id;
		this.screen_name = screen_name;
		this.uid = uid;
		this.blog_url = blog_url;
		this.etc1 = etc1;
		this.etc2 = etc2;
		this.etc3 = etc3;
		this.insert_date = insert_date;
		this.request_id1 = request_id1;
		this.request_id2 = request_id2;
		this.url_score1 = url_score1;
		this.url_score2 = url_score2;
		this.follower_cnt = follower_cnt;
		this.friend_cnt = friend_cnt;
		this.id_score = id_score;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getScreen_name() {
		return screen_name;
	}

	public void setScreen_name(String screen_name) {
		this.screen_name = screen_name;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getBlog_url() {
		return blog_url;
	}

	public void setBlog_url(String blog_url) {
		this.blog_url = blog_url;
	}

	public String getEtc1() {
		return etc1;
	}

	public void setEtc1(String etc1) {
		this.etc1 = etc1;
	}

	public String getEtc2() {
		return etc2;
	}

	public void setEtc2(String etc2) {
		this.etc2 = etc2;
	}

	public String getEtc3() {
		return etc3;
	}

	public void setEtc3(String etc3) {
		this.etc3 = etc3;
	}

	public String getInsert_date() {
		return insert_date;
	}

	public void setInsert_date(String insert_date) {
		this.insert_date = insert_date;
	}

	public String getRequest_id1() {
		return request_id1;
	}

	public void setRequest_id1(String request_id1) {
		this.request_id1 = request_id1;
	}

	public String getRequest_id2() {
		return request_id2;
	}

	public void setRequest_id2(String request_id2) {
		this.request_id2 = request_id2;
	}

	public int getUrl_score1() {
		return url_score1;
	}

	public void setUrl_score1(int url_score1) {
		this.url_score1 = url_score1;
	}

	public int getUrl_score2() {
		return url_score2;
	}

	public void setUrl_score2(int url_score2) {
		this.url_score2 = url_score2;
	}

	public int getFollower_cnt() {
		return follower_cnt;
	}

	public void setFollower_cnt(int follower_cnt) {
		this.follower_cnt = follower_cnt;
	}

	public int getFriend_cnt() {
		return friend_cnt;
	}

	public void setFriend_cnt(int friend_cnt) {
		this.friend_cnt = friend_cnt;
	}

	public int getId_score() {
		return id_score;
	}

	public void setId_score(int id_score) {
		this.id_score = id_score;
	}

	
}
