package de.rub.iaw.domain;

import java.util.Date;

/**
 *Entity Class for the probability calculation of a text
 *Saves the post ID, thread ID, text, post date and probabilities for every Code per content analysis program
 *
 * @author Sarah Böning
 **/

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "TextCodeProb", catalog = "rotterdam")
public class TextCodeProb implements java.io.Serializable {

	private static final long serialVersionUID = 2963887749491154824L;

	private Long id;
	// When using the CodeTextProb-API Wordpress can send the post-ID and the
	// thread-ID of the text
	private Long postID;
	private Long threadID;

	// The text that is analyzed
	private String text;

	private Date postDate;

	// Calculated probability for codes based on Britta Rannenberg's Bachelor
	// thesis when using Empath
	// 84 = 8.4, 85 = 8.5
	private Double empath_0;
	private Double empath_1;
	private Double empath_2a;
	private Double empath_2b;
	private Double empath_3;
	private Double empath_4;
	private Double empath_5;
	private Double empath_6a;
	private Double empath_6b;
	private Double empath_7;
	private Double empath_84;
	private Double empath_85;
	private Double empath_9a;
	private Double empath_9b;
	private Double empath_10;
	private Double empath_A1;
	private Double empath_A2;
	private Double empath_A3;

	private Double liwc_0;
	private Double liwc_1;
	private Double liwc_2a;
	private Double liwc_2b;
	private Double liwc_3;
	private Double liwc_4;
	private Double liwc_5;
	private Double liwc_6a;
	private Double liwc_6b;
	private Double liwc_7;
	private Double liwc_84;
	private Double liwc_85;
	private Double liwc_9a;
	private Double liwc_9b;
	private Double liwc_10;
	private Double liwc_A1;
	private Double liwc_A2;
	private Double liwc_A3;

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public TextCodeProb() {

	}

	public TextCodeProb(Long postID, String text, Date postDate) {
		this.postID = postID;
		this.text = text;
		this.postDate = postDate;
	}

	public TextCodeProb(Long postID, Long threadID, String text, Date postDate) {
		this.postID = postID;
		this.threadID = threadID;
		this.text = text;
		this.postDate = postDate;
	}

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "TEXTCODEPROB_ID", unique = true, nullable = false)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "WP_POST_ID")
	public Long getPostID() {
		return postID;
	}

	public void setPostID(Long postID) {
		this.postID = postID;
	}

	@Column(name = "WP_THREAD_ID")
	public Long getThreadID() {
		return threadID;
	}

	public void setThreadID(Long threadID) {
		this.threadID = threadID;
	}

	@Column(name = "TEXT", length = 16777215, nullable = false)
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Column(name = "POSTDATE")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getPostDate() {
		return postDate;
	}

	public void setPostDate(Date postDate) {
		this.postDate = postDate;
	}

	@Column(name = "EMPATH_0")
	public Double getEmpath_0() {
		return empath_0;
	}

	public void setEmpath_0(Double empath_0) {
		this.empath_0 = empath_0;
	}

	@Column(name = "EMPATH_1")
	public Double getEmpath_1() {
		return empath_1;
	}

	public void setEmpath_1(Double empath_1) {
		this.empath_1 = empath_1;
	}

	@Column(name = "EMPATH_2_A")
	public Double getEmpath_2a() {
		return empath_2a;
	}

	public void setEmpath_2a(Double empath_2a) {
		this.empath_2a = empath_2a;
	}

	@Column(name = "EMPATH_2_B")
	public Double getEmpath_2b() {
		return empath_2b;
	}

	public void setEmpath_2b(Double empath_2b) {
		this.empath_2b = empath_2b;
	}

	@Column(name = "EMPATH_3")
	public Double getEmpath_3() {
		return empath_3;
	}

	public void setEmpath_3(Double empath_3) {
		this.empath_3 = empath_3;
	}

	@Column(name = "EMPATH_4")
	public Double getEmpath_4() {
		return empath_4;
	}

	public void setEmpath_4(Double empath_4) {
		this.empath_4 = empath_4;
	}

	@Column(name = "EMPATH_5")
	public Double getEmpath_5() {
		return empath_5;
	}

	public void setEmpath_5(Double empath_5) {
		this.empath_5 = empath_5;
	}

	@Column(name = "EMPATH_6_A")
	public Double getEmpath_6a() {
		return empath_6a;
	}

	public void setEmpath_6a(Double empath_6a) {
		this.empath_6a = empath_6a;
	}

	@Column(name = "EMPATH_6_B")
	public Double getEmpath_6b() {
		return empath_6b;
	}

	public void setEmpath_6b(Double empath_6b) {
		this.empath_6b = empath_6b;
	}

	@Column(name = "EMPATH_7")
	public Double getEmpath_7() {
		return empath_7;
	}

	public void setEmpath_7(Double empath_7) {
		this.empath_7 = empath_7;
	}

	@Column(name = "EMPATH_8_4")
	public Double getEmpath_84() {
		return empath_84;
	}

	public void setEmpath_84(Double empath_84) {
		this.empath_84 = empath_84;
	}

	@Column(name = "EMPATH_8_5")
	public Double getEmpath_85() {
		return empath_85;
	}

	public void setEmpath_85(Double empath_85) {
		this.empath_85 = empath_85;
	}

	@Column(name = "EMPATH_9_A")
	public Double getEmpath_9a() {
		return empath_9a;
	}

	public void setEmpath_9a(Double empath_9a) {
		this.empath_9a = empath_9a;
	}

	@Column(name = "EMPATH_9_B")
	public Double getEmpath_9b() {
		return empath_9b;
	}

	public void setEmpath_9b(Double empath_9b) {
		this.empath_9b = empath_9b;
	}

	@Column(name = "EMPATH_10")
	public Double getEmpath_10() {
		return empath_10;
	}

	public void setEmpath_10(Double empath_10) {
		this.empath_10 = empath_10;
	}

	@Column(name = "EMPATH_A_1")
	public Double getEmpath_A1() {
		return empath_A1;
	}

	public void setEmpath_A1(Double empath_A1) {
		this.empath_A1 = empath_A1;
	}

	@Column(name = "EMPATH_A_2")
	public Double getEmpath_A2() {
		return empath_A2;
	}

	public void setEmpath_A2(Double empath_A2) {
		this.empath_A2 = empath_A2;
	}

	@Column(name = "EMPATH_A_3")
	public Double getEmpath_A3() {
		return empath_A3;
	}

	public void setEmpath_A3(Double empath_A3) {
		this.empath_A3 = empath_A3;
	}

	@Column(name = "LIWC_0")
	public Double getLiwc_0() {
		return liwc_0;
	}

	public void setLiwc_0(Double liwc_0) {
		this.liwc_0 = liwc_0;
	}

	@Column(name = "LIWC_1")
	public Double getLiwc_1() {
		return liwc_1;
	}

	public void setLiwc_1(Double liwc_1) {
		this.liwc_1 = liwc_1;
	}

	@Column(name = "LIWC_2_A")
	public Double getLiwc_2a() {
		return liwc_2a;
	}

	public void setLiwc_2a(Double liwc_2a) {
		this.liwc_2a = liwc_2a;
	}

	@Column(name = "LIWC_2_B")
	public Double getLiwc_2b() {
		return liwc_2b;
	}

	public void setLiwc_2b(Double liwc_2b) {
		this.liwc_2b = liwc_2b;
	}

	@Column(name = "LIWC_3")
	public Double getLiwc_3() {
		return liwc_3;
	}

	public void setLiwc_3(Double liwc_3) {
		this.liwc_3 = liwc_3;
	}

	@Column(name = "LIWC_4")
	public Double getLiwc_4() {
		return liwc_4;
	}

	public void setLiwc_4(Double liwc_4) {
		this.liwc_4 = liwc_4;
	}

	@Column(name = "LIWC_5")
	public Double getLiwc_5() {
		return liwc_5;
	}

	public void setLiwc_5(Double liwc_5) {
		this.liwc_5 = liwc_5;
	}

	@Column(name = "LIWC_6_A")
	public Double getLiwc_6a() {
		return liwc_6a;
	}

	public void setLiwc_6a(Double liwc_6a) {
		this.liwc_6a = liwc_6a;
	}

	@Column(name = "LIWC_6_B")
	public Double getLiwc_6b() {
		return liwc_6b;
	}

	public void setLiwc_6b(Double liwc_6b) {
		this.liwc_6b = liwc_6b;
	}

	@Column(name = "LIWC_7")
	public Double getLiwc_7() {
		return liwc_7;
	}

	public void setLiwc_7(Double liwc_7) {
		this.liwc_7 = liwc_7;
	}

	@Column(name = "LIWC_8_4")
	public Double getLiwc_84() {
		return liwc_84;
	}

	public void setLiwc_84(Double liwc_84) {
		this.liwc_84 = liwc_84;
	}

	@Column(name = "LIWC_8_5")
	public Double getLiwc_85() {
		return liwc_85;
	}

	public void setLiwc_85(Double liwc_85) {
		this.liwc_85 = liwc_85;
	}

	@Column(name = "LIWC_9_A")
	public Double getLiwc_9a() {
		return liwc_9a;
	}

	public void setLiwc_9a(Double liwc_9a) {
		this.liwc_9a = liwc_9a;
	}

	@Column(name = "LIWC_9_B")
	public Double getLiwc_9b() {
		return liwc_9b;
	}

	public void setLiwc_9b(Double liwc_9b) {
		this.liwc_9b = liwc_9b;
	}

	@Column(name = "LIWC_10")
	public Double getLiwc_10() {
		return liwc_10;
	}

	public void setLiwc_10(Double liwc_10) {
		this.liwc_10 = liwc_10;
	}

	@Column(name = "LIWC_A_1")
	public Double getLiwc_A1() {
		return liwc_A1;
	}

	public void setLiwc_A1(Double liwc_A1) {
		this.liwc_A1 = liwc_A1;
	}

	@Column(name = "LIWC_A_2")
	public Double getLiwc_A2() {
		return liwc_A2;
	}

	public void setLiwc_A2(Double liwc_A2) {
		this.liwc_A2 = liwc_A2;
	}

	@Column(name = "LIWC_A_3")
	public Double getLiwc_A3() {
		return liwc_A3;
	}

	public void setLiwc_A3(Double liwc_A3) {
		this.liwc_A3 = liwc_A3;
	}
}
