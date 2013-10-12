package spc_proj.wrapper;

import java.net.URL;
import java.util.Date;

import spc_proj.utils.StringUtil;
import weibo4j.model.Status;
import weibo4j.model.User;

public class WeiboUser {

	private String id = null;
	private String screen_name = null;
	private String name = null;
	private int province = -1;
	private int city = -1;
	private String location = null;
	private String description = null;
	private String url = null;
	private URL profile_image_url = null;
	private String user_domain = null;
	private String gender = null;
	private int followers_count = -1;
	private int friends_count = -1;
	private int statuses_count = -1;
	private int favourites_count = -1;
	private Date created_at = null;
	private boolean following = false;
	private boolean verified = false;
	private int verified_type = -1;
	private boolean allow_all_act_msg = false;
	private boolean allow_all_comment = false;
	private boolean follow_me = false;
	private String avatar_large = null;
	private int online_status = -1;
	private Status status = null;
	private int bi_followers_count = -1;
	private String remark = null;
	private String lang = null;
	private String verified_reason = null;
	private String weihao = null;
	private String status_id = null;
	private String insert_date = null;
	private int crawl_level = -1;
	private int crawled = -1;
	private String uid = null;
	private String blog_url = null;
	private String id_score = null;
	private String request_id1 = null;
	private String request_id2 = null;
	private String url_score1 = null;
	private String url_score2 = null;

	
	public WeiboUser(String id, String screen_name, String name, int province,
			int city, String location, String description, String url,
			URL profile_image_url, String user_domain, String gender,
			int followers_count, int friends_count, int statuses_count,
			int favourites_count, Date created_at, boolean following,
			boolean verified, int verified_type, boolean allow_all_act_msg,
			boolean allow_all_comment, boolean follow_me, String avatar_large,
			int online_status, Status status, int bi_followers_count,
			String remark, String lang, String verified_reason, String weihao,
			String status_id, String insert_date, int crawl_level,
			int crawled, String uid, String blog_url, String id_score, String request_id1,
			String request_id2, String url_score1, String url_score2) {
		super();
		this.id = id;
		this.screen_name = screen_name;
		this.name = name;
		this.province = province;
		this.city = city;
		this.location = location;
		this.description = description;
		this.url = url;
		this.profile_image_url = profile_image_url;
		this.user_domain = user_domain;
		this.gender = gender;
		this.followers_count = followers_count;
		this.friends_count = friends_count;
		this.statuses_count = statuses_count;
		this.favourites_count = favourites_count;
		this.created_at = created_at;
		this.following = following;
		this.verified = verified;
		this.verified_type = verified_type;
		this.allow_all_act_msg = allow_all_act_msg;
		this.allow_all_comment = allow_all_comment;
		this.follow_me = follow_me;
		this.avatar_large = avatar_large;
		this.online_status = online_status;
		this.status = status;
		this.bi_followers_count = bi_followers_count;
		this.remark = remark;
		this.lang = lang;
		this.verified_reason = verified_reason;
		this.weihao = weihao;
		this.status_id = status_id;
		this.insert_date = insert_date;
		this.crawl_level = crawl_level;
		this.crawled = crawled;
		this.uid = uid;
		this.blog_url = blog_url;
		this.id_score = id_score;
		this.request_id1 = request_id1;
		this.request_id2 = request_id2;
		this.url_score1 = url_score1;
		this.url_score2 = url_score2;
	}

	public WeiboUser(Status s) {
		// TODO Auto-generated constructor stub
		this.id = s.getUser().getId();
		this.screen_name = s.getUser().getScreenName();
		this.name = s.getUser().getName();
		this.province = s.getUser().getProvince();
		this.city = s.getUser().getCity();
		this.location = s.getUser().getLocation();
		this.description = s.getUser().getDescription();
		this.url = "http://www.weibo.com/u/" + s.getUser().getId();
		this.profile_image_url = s.getUser().getProfileImageURL();
		this.user_domain = s.getUser().getUserDomain();
		this.gender = s.getUser().getGender();
		this.followers_count = s.getUser().getFollowersCount();
		this.friends_count = s.getUser().getFriendsCount();
		this.statuses_count = s.getUser().getStatusesCount();
		this.favourites_count = s.getUser().getFavouritesCount();
		this.created_at = s.getUser().getCreatedAt();
		this.following = s.getUser().isFollowing();
		this.verified = s.getUser().isVerified();
		this.verified_type = s.getUser().getVerifiedType();
		this.allow_all_act_msg = s.getUser().isallowAllActMsg();
		this.allow_all_comment = s.getUser().isAllowAllComment();
		this.follow_me = s.getUser().isFollowMe();
		this.avatar_large = s.getUser().getAvatarLarge();
		this.online_status = s.getUser().getOnlineStatus();
		this.status = s;
		this.bi_followers_count = s.getUser().getBiFollowersCount();
		this.remark = s.getUser().getRemark();
		this.lang = s.getUser().getLang();
		this.verified_reason = s.getUser().getVerifiedReason();
		this.weihao = s.getUser().getWeihao();
		this.status_id = s.getId();
		
		this.blog_url = s.getUser().getUrl();
		this.insert_date = StringUtil.getCurrent(1);
	}
	
	public WeiboUser(User u) {
		// TODO Auto-generated constructor stub
		this.id = u.getId();
		this.screen_name = u.getScreenName();
		this.name = u.getName();
		this.province = u.getProvince();
		this.city = u.getCity();
		this.location = u.getLocation();
		this.description = u.getDescription();
		this.url = "http://www.weibo.com/u/" + u.getId();
		this.profile_image_url = u.getProfileImageURL();
		this.user_domain = u.getUserDomain();
		this.gender = u.getGender();
		this.followers_count = u.getFollowersCount();
		this.friends_count = u.getFriendsCount();
		this.statuses_count = u.getStatusesCount();
		this.favourites_count = u.getFavouritesCount();
		this.created_at = u.getCreatedAt();
		this.following = u.isFollowing();
		this.verified = u.isVerified();
		this.verified_type = u.getVerifiedType();
		this.allow_all_act_msg = u.isallowAllActMsg();
		this.allow_all_comment = u.isAllowAllComment();
		this.follow_me = u.isFollowMe();
		this.avatar_large = u.getAvatarLarge();
		this.online_status = u.getOnlineStatus();
		this.status = u.getStatus();
		this.bi_followers_count = u.getBiFollowersCount();
		this.remark = u.getRemark();
		this.lang = u.getLang();
		this.verified_reason = u.getVerifiedReason();
		this.weihao = u.getWeihao();
		this.status_id = u.getStatusId();
		
		this.blog_url = u.getUrl();
		this.insert_date = StringUtil.getCurrent(1);
	}
	
	public String getId() {
		return id==null?"":id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getScreen_name() {
		return screen_name==null?"":screen_name;
	}

	public void setScreen_name(String screen_name) {
		this.screen_name = screen_name;
	}

	public String getName() {
		return name==null?"":name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getProvince() {
		return province;
	}

	public void setProvince(int province) {
		this.province = province;
	}

	public int getCity() {
		return city;
	}

	public void setCity(int city) {
		this.city = city;
	}

	public String getLocation() {
		return location==null?"":location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getDescription() {
		return description==null?"":description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUrl() {
		return url==null?"":url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public URL getProfile_image_url() {
		return (URL) (profile_image_url==null?"":profile_image_url);
	}

	public void setProfile_image_url(URL profile_image_url) {
		this.profile_image_url = profile_image_url;
	}

	public String getUser_domain() {
		return user_domain==null?"":user_domain;
	}

	public void setUser_domain(String user_domain) {
		this.user_domain = user_domain;
	}

	public String getGender() {
		return gender==null?"":gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public int getFollowers_count() {
		return followers_count;
	}

	public void setFollowers_count(int followers_count) {
		this.followers_count = followers_count;
	}

	public int getFriends_count() {
		return friends_count;
	}

	public void setFriends_count(int friends_count) {
		this.friends_count = friends_count;
	}

	public int getStatuses_count() {
		return statuses_count;
	}

	public void setStatuses_count(int statuses_count) {
		this.statuses_count = statuses_count;
	}

	public int getFavourites_count() {
		return favourites_count;
	}

	public void setFavourites_count(int favourites_count) {
		this.favourites_count = favourites_count;
	}

	public Date getCreated_at() {
		return (Date) (created_at==null?"":created_at);
	}

	public void setCreated_at(Date created_at) {
		this.created_at = created_at;
	}

	public boolean isFollowing() {
		return following;
	}

	public void setFollowing(boolean following) {
		this.following = following;
	}

	public boolean isVerified() {
		return verified;
	}

	public void setVerified(boolean verified) {
		this.verified = verified;
	}

	public int getVerified_type() {
		return verified_type;
	}

	public void setVerified_type(int verified_type) {
		this.verified_type = verified_type;
	}

	public boolean isAllow_all_act_msg() {
		return allow_all_act_msg;
	}

	public void setAllow_all_act_msg(boolean allow_all_act_msg) {
		this.allow_all_act_msg = allow_all_act_msg;
	}

	public boolean isAllow_all_comment() {
		return allow_all_comment;
	}

	public void setAllow_all_comment(boolean allow_all_comment) {
		this.allow_all_comment = allow_all_comment;
	}

	public boolean isFollow_me() {
		return follow_me;
	}

	public void setFollow_me(boolean follow_me) {
		this.follow_me = follow_me;
	}

	public String getAvatar_large() {
		return avatar_large==null?"":avatar_large;
	}

	public void setAvatar_large(String avatar_large) {
		this.avatar_large = avatar_large;
	}

	public int getOnline_status() {
		return online_status;
	}

	public void setOnline_status(int online_status) {
		this.online_status = online_status;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public int getBi_followers_count() {
		return bi_followers_count;
	}

	public void setBi_followers_count(int bi_followers_count) {
		this.bi_followers_count = bi_followers_count;
	}

	public String getRemark() {
		return remark==null?"":remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getLang() {
		return lang==null?"":lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public String getVerified_reason() {
		return verified_reason==null?"":verified_reason;
	}

	public void setVerified_reason(String verified_reason) {
		this.verified_reason = verified_reason;
	}

	public String getWeihao() {
		return weihao==null?"":weihao;
	}

	public void setWeihao(String weihao) {
		this.weihao = weihao;
	}

	public String getStatus_id() {
		return status_id==null?"":status_id;
	}

	public void setStatus_id(String status_id) {
		this.status_id = status_id;
	}

	public String getInsert_date() {
		return insert_date==null?"":insert_date;
	}

	public void setInsert_date(String insert_date) {
		this.insert_date = insert_date;
	}

	public int getCrawl_level() {
		return crawl_level;
	}

	public void setCrawl_level(int crawl_level) {
		this.crawl_level = crawl_level;
	}

	public int getCrawled() {
		return crawled==-1?0:crawled;
	}

	public void setCrawled(int crawled) {
		this.crawled = crawled;
	}

	public String getUid() {
		return uid==null?"":uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getBlog_url() {
		return blog_url==null?"":blog_url;
	}

	public void setBlog_url(String blog_url) {
		this.blog_url = blog_url;
	}

	public String getId_score() {
		return id_score;
	}

	public void setId_score(String id_score) {
		this.id_score = id_score;
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

	public String getUrl_score1() {
		return url_score1;
	}

	public void setUrl_score1(String url_score1) {
		this.url_score1 = url_score1;
	}

	public String getUrl_score2() {
		return url_score2;
	}

	public void setUrl_score2(String url_score2) {
		this.url_score2 = url_score2;
	}

	
}
