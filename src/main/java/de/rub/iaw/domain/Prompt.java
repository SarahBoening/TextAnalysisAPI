package de.rub.iaw.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "prompt", catalog = "rotterdam")
public class Prompt implements java.io.Serializable {

	private static final long serialVersionUID = 7263602923183046118L;

	private Long id;

	// Enums with Hibernate & MySQL are not feasible :/

	// What is the prompt trying to achieve?
	private String goal;

	// Where should the prompt be displayed?
	// TODO this should allow multiple locations
	private String location;

	// Who should get the prompt?
	private String target;

	// Individual text of the prompt: Instruction or Question
	// May be a strange way to do it, but it has the advantage that you can also
	// insert new prompts in bulk in MySQL Workbench without joining the hell
	// out of the db
	// When adding new languages:
	// - Fix getTranslation below in Prompt.java
	// - Fix checkInput method in PromptController.java
	// - Fix PromptingEnum_LanguageCodes.java
	private String string_english;
	private String string_slovenian;
	private String string_german;
	private String string_croatian;

	// Differentiate between different settings in which a prompt is useful
	// e.g. MOOC, Notes, Forum
	private String setting;

	// Which category?
	private String category;

	private String instructional_character;

	// image
	// duration in which the prompt is displayed?
	// bools for highlighted, flashing etc?

	public Prompt() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	// http://stackoverflow.com/questions/3179773/type-mapping-mysql-typ-text-to-java-hibernate
	// Type text instead of length?
	@Column(name = "STRING_ENGLISH", length = 16777215)
	public String getString_english() {
		return string_english;
	}

	public void setString_english(String string_english) {
		this.string_english = string_english;
	}

	@Column(name = "STRING_SLOVENIAN", length = 16777215)
	public String getString_slovenian() {
		return string_slovenian;
	}

	public void setString_slovenian(String string_slovenian) {
		this.string_slovenian = string_slovenian;
	}

	@Column(name = "STRING_GERMAN", length = 16777215)
	public String getString_german() {
		return string_german;
	}

	public void setString_german(String string_german) {
		this.string_german = string_german;
	}

	@Column(name = "STRING_CROATIAN", length = 16777215)
	public String getString_croatian() {
		return string_croatian;
	}

	public void setString_croatian(String string_croatian) {
		this.string_croatian = string_croatian;
	}

	@Column(name = "CATEGORY")
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	@Column(name = "SETTING")
	public String getSetting() {
		return setting;
	}

	public void setSetting(String setting) {
		this.setting = setting;
	}

	@Column(name = "LOCATION")
	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	@Column(name = "TARGET")
	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	@Column(name = "GOAL")
	public String getGoal() {
		return goal;
	}

	public void setGoal(String goal) {
		this.goal = goal;
	}

	@Column(name = "INSTRUCTIONALCHARACTER")
	public String getInstructionalCharacter() {
		return instructional_character;
	}

	public void setInstructionalCharacter(String instructionalCharacter) {
		this.instructional_character = instructionalCharacter;
	}

	public String getTranslation(String language) {

		switch (language) {
		case "de":
			return this.getString_german();

		case "en":
			return this.getString_english();

		case "hr":
			return this.getString_croatian();

		case "sl":
			return this.getString_slovenian();

		default:
			return this.getString_english();
		}
	}

}
