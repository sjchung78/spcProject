package spc_proj.wrapper;

import java.sql.Blob;

public class Weibo_comment {

	private String id           = null; 
	private String up_date      = null;
	private String ucomment     = null;
	private String comment_type = null;
	private String uid          = null;
	private String comment_id   = null;
	private String etc1         = null;
	private String etc2         = null;
	private String etc3         = null;
	private String insert_date  = null;
	private int blob_score   = -1;
	
	public Weibo_comment(String id, String up_date, String ucomment,
			String comment_type, String uid, String comment_id, String etc1,
			String etc2, String etc3, String insert_date, int blob_score) {
		super();
		this.id = id;
		this.up_date = up_date;
		this.ucomment = ucomment;
		this.comment_type = comment_type;
		this.uid = uid;
		this.comment_id = comment_id;
		this.etc1 = etc1;
		this.etc2 = etc2;
		this.etc3 = etc3;
		this.insert_date = insert_date;
		this.blob_score = blob_score;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUp_date() {
		return up_date;
	}

	public void setUp_date(String up_date) {
		this.up_date = up_date;
	}

	public String getUcomment() {
		return ucomment;
	}

	public void setUcomment(String ucomment) {
		this.ucomment = ucomment;
	}

	public String getComment_type() {
		return comment_type;
	}

	public void setComment_type(String comment_type) {
		this.comment_type = comment_type;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getComment_id() {
		return comment_id;
	}

	public void setComment_id(String comment_id) {
		this.comment_id = comment_id;
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

	public int getBlob_score() {
		return blob_score;
	}

	public void setBlob_score(int blob_score) {
		this.blob_score = blob_score;
	}

	
}
