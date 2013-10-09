package spc_proj.wrapper;

import java.sql.Blob;
import java.util.Date;

import spc_proj.utils.StringUtil;
import weibo4j.model.Comment;
import weibo4j.model.Status;

public class Weibo_comment {

	private String id           = null; 
	private Date created_at   = null;
	private String text         = null;
	private String comment_type = null;
	private String comment_id   = null;
	private String etc1         = null;
	private String etc2         = null;
	private String etc3         = null;
	private String insert_date  = null;
	private int blob_score   = -1;
	
	public Weibo_comment(String id, Date created_at, String text,
			String comment_type, String comment_id, String etc1,
			String etc2, String etc3, String insert_date, int blob_score) {
		super();
		this.id = id;
		this.created_at = created_at;
		this.text = text;
		this.comment_type = comment_type;
		this.comment_id = comment_id;
		this.etc1 = etc1;
		this.etc2 = etc2;
		this.etc3 = etc3;
		this.insert_date = insert_date;
		this.blob_score = blob_score;
	}

	public Weibo_comment(Comment c) {
		// TODO Auto-generated constructor stub
		this.id = c.getUser().getId();
		this.created_at = c.getCreatedAt();
		this.text = c.getText();
		this.comment_type = "C";
		this.comment_id = ""+c.getId();

		this.insert_date = StringUtil.getCurrent(1);
	}
	
	public Weibo_comment(Status s) {
		// TODO Auto-generated constructor stub
		this.id = s.getUser().getId();
		this.created_at = s.getCreatedAt();
		this.text = s.getText();
		this.comment_type = "S";
		this.comment_id = ""+s.getId();

		this.insert_date = StringUtil.getCurrent(1);
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getCreated_at() {
		return created_at;
	}

	public void setCreated_at(Date created_at) {
		this.created_at = created_at;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getComment_type() {
		return comment_type;
	}

	public void setComment_type(String comment_type) {
		this.comment_type = comment_type;
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
