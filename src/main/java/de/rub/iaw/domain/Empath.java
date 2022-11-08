package de.rub.iaw.domain;

/**
 * Entity Class for the Empath API
 * Saves the scores per catagory and ID of the analyzed text
 *
 * @author Sarah Böning
 **/

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

//Only not null properties are serialized

@Entity
@Table(name = "Empath", catalog = "rotterdam")
public class Empath implements java.io.Serializable {

	private static final long serialVersionUID = -64872088546461718L;

	private Long id;

	// Foreign Key TCP_ID_FK for easy evaluation of data
	private TextCodeProb textCodeProb;

	// All 194 Empath categories
	private Double achievement;
	private Double affection;
	private Double aggression;
	private Double air_travel;
	private Double alcohol;
	private Double ancient;
	private Double anger;
	private Double animal;
	private Double anonymity;
	private Double anticipation;
	private Double appearance;
	private Double art;
	private Double attractive;
	private Double banking;
	private Double beach;
	private Double beauty;
	private Double blue_collar_job;
	private Double body;
	private Double breaking;
	private Double business;
	private Double car;
	private Double celebration;
	private Double cheerfulness;
	private Double childish;
	private Double children;
	private Double cleaning;
	private Double clothing;
	private Double cold;
	private Double college;
	private Double communication;
	private Double competing;
	private Double computer;
	private Double confusion;
	private Double contentment;
	private Double cooking;
	private Double crime;
	private Double dance;
	private Double death;
	private Double deception;
	private Double disappointment;
	private Double disgust;
	private Double dispute;
	private Double divine;
	private Double domestic_work;
	private Double dominant_heirarchical;
	private Double dominant_personality;
	private Double driving;
	private Double eating;
	private Double economics;
	private Double emotional;
	private Double envy;
	private Double exasperation;
	private Double exercise;
	private Double exotic;
	private Double fabric;
	private Double family;
	private Double farming;
	private Double fashion;
	private Double fear;
	private Double feminine;
	private Double fight;
	private Double fire;
	private Double friends;
	private Double fun;
	private Double furniture;
	private Double gain;
	private Double giving;
	private Double government;
	private Double hate;
	private Double healing;
	private Double health;
	private Double hearing;
	private Double help;
	private Double heroic;
	private Double hiking;
	private Double hipster;
	private Double home;
	private Double horror;
	private Double hygiene;
	private Double independence;
	private Double injury;
	private Double internet;
	private Double irritability;
	private Double journalism;
	private Double joy;
	private Double kill;
	private Double law;
	private Double leader;
	private Double legend;
	private Double leisure;
	private Double liquid;
	private Double listen;
	private Double love;
	private Double lust;
	private Double magic;
	private Double masculine;
	private Double medical_emergency;
	private Double medieval;
	private Double meeting;
	private Double messaging;
	private Double military;
	private Double money;
	private Double monster;
	private Double morning;
	private Double movement;
	private Double music;
	private Double musical;
	private Double negative_emotion;
	private Double neglect;
	private Double negotiate;
	private Double nervousness;
	private Double night;
	private Double noise;
	private Double occupation;
	private Double ocean;
	private Double office;
	private Double optimism;
	private Double order;
	private Double pain;
	private Double party;
	private Double payment;
	private Double pet;
	private Double philosophy;
	private Double phone;
	private Double plant;
	private Double play;
	private Double politeness;
	private Double politics;
	private Double poor;
	private Double positive_emotion;
	private Double power;
	private Double pride;
	private Double prison;
	private Double programming;
	private Double rage;
	private Double reading;
	private Double real_estate;
	private Double religion;
	private Double restaurant;
	private Double ridicule;
	private Double royalty;
	private Double rural;
	private Double sadness;
	private Double sailing;
	private Double school;
	private Double science;
	private Double sexual;
	private Double shame;
	private Double shape_and_size;
	private Double ship;
	private Double shopping;
	private Double sleep;
	private Double smell;
	private Double social_media;
	private Double sound;
	private Double speaking;
	private Double sports;
	private Double stealing;
	private Double strength;
	private Double suffering;
	private Double superhero;
	private Double surprise;
	private Double swearing_terms;
	private Double swimming;
	private Double sympathy;
	private Double technology;
	private Double terrorism;
	private Double timidity;
	private Double tool;
	private Double torment;
	private Double tourism;
	private Double toy;
	private Double traveling;
	private Double trust;
	private Double ugliness;
	private Double urban;
	private Double vacation;
	private Double valuable;
	private Double vehicle;
	private Double violence;
	private Double war;
	private Double warmth;
	private Double water;
	private Double weakness;
	private Double wealthy;
	private Double weapon;
	private Double weather;
	private Double wedding;
	private Double white_collar_job;
	private Double work;
	private Double worship;
	private Double writing;
	private Double youth;
	private Double zest;

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public Empath() {

	}

	public Empath(TextCodeProb textCodeProb) {
		this.textCodeProb = textCodeProb;
	}

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "EMPATH_ID", unique = true, nullable = false)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@OneToOne
	@JoinColumn(name = "TCP_ID_FK", referencedColumnName = "TEXTCODEPROB_ID", nullable = false)
	public TextCodeProb getTextCodeProb() {
		return textCodeProb;
	}

	public void setTextCodeProb(TextCodeProb textCodeProb) {
		this.textCodeProb = textCodeProb;
	}

	@Column(name = "ACHIEVEMENT")
	public Double getAchievement() {
		return achievement;
	}

	public void setAchievement(Double achievement) {
		this.achievement = achievement;
	}

	@Column(name = "AFFECTION")
	public Double getAffection() {
		return affection;
	}

	public void setAffection(Double affection) {
		this.affection = affection;
	}

	@Column(name = "AGGRESSION")
	public Double getAggression() {
		return aggression;
	}

	public void setAggression(Double aggression) {
		this.aggression = aggression;
	}

	@Column(name = "AIR_TRAVEL")
	public Double getAir_travel() {
		return air_travel;
	}

	public void setAir_travel(Double air_travel) {
		this.air_travel = air_travel;
	}

	@Column(name = "ALCOHOL")
	public Double getAlcohol() {
		return alcohol;
	}

	public void setAlcohol(Double alcohol) {
		this.alcohol = alcohol;
	}

	@Column(name = "ANCIENT")
	public Double getAncient() {
		return ancient;
	}

	public void setAncient(Double ancient) {
		this.ancient = ancient;
	}

	@Column(name = "ANGER")
	public Double getAnger() {
		return anger;
	}

	public void setAnger(Double anger) {
		this.anger = anger;
	}

	@Column(name = "ANIMAL")
	public Double getAnimal() {
		return animal;
	}

	public void setAnimal(Double animal) {
		this.animal = animal;
	}

	@Column(name = "ANONYMITY")
	public Double getAnonymity() {
		return anonymity;
	}

	public void setAnonymity(Double anonymity) {
		this.anonymity = anonymity;
	}

	@Column(name = "ANTICIPATION")
	public Double getAnticipation() {
		return anticipation;
	}

	public void setAnticipation(Double anticipation) {
		this.anticipation = anticipation;
	}

	@Column(name = "APPEARENCE")
	public Double getAppearance() {
		return appearance;
	}

	public void setAppearance(Double appearance) {
		this.appearance = appearance;
	}

	@Column(name = "ART")
	public Double getArt() {
		return art;
	}

	public void setArt(Double art) {
		this.art = art;
	}

	@Column(name = "ATTRACTIVE")
	public Double getAttractive() {
		return attractive;
	}

	public void setAttractive(Double attractive) {
		this.attractive = attractive;
	}

	@Column(name = "BANKING")
	public Double getBanking() {
		return banking;
	}

	public void setBanking(Double banking) {
		this.banking = banking;
	}

	@Column(name = "BEACH")
	public Double getBeach() {
		return beach;
	}

	public void setBeach(Double beach) {
		this.beach = beach;
	}

	@Column(name = "BEAUTY")
	public Double getBeauty() {
		return beauty;
	}

	public void setBeauty(Double beauty) {
		this.beauty = beauty;
	}

	@Column(name = "BLUE_COLLAR_JOB")
	public Double getBlue_collar_job() {
		return blue_collar_job;
	}

	public void setBlue_collar_job(Double blue_collar_job) {
		this.blue_collar_job = blue_collar_job;
	}

	@Column(name = "BODY")
	public Double getBody() {
		return body;
	}

	public void setBody(Double body) {
		this.body = body;
	}

	@Column(name = "BREAKING")
	public Double getBreaking() {
		return breaking;
	}

	public void setBreaking(Double breaking) {
		this.breaking = breaking;
	}

	@Column(name = "BUSINESS")
	public Double getBusiness() {
		return business;
	}

	public void setBusiness(Double business) {
		this.business = business;
	}

	@Column(name = "CAR")
	public Double getCar() {
		return car;
	}

	public void setCar(Double car) {
		this.car = car;
	}

	@Column(name = "CELEBRATION")
	public Double getCelebration() {
		return celebration;
	}

	public void setCelebration(Double celebration) {
		this.celebration = celebration;
	}

	@Column(name = "CHEERFULLNESS")
	public Double getCheerfulness() {
		return cheerfulness;
	}

	public void setCheerfulness(Double cheerfulness) {
		this.cheerfulness = cheerfulness;
	}

	@Column(name = "CHILDISH")
	public Double getChildish() {
		return childish;
	}

	public void setChildish(Double childish) {
		this.childish = childish;
	}

	@Column(name = "CHILDREN")
	public Double getChildren() {
		return children;
	}

	public void setChildren(Double children) {
		this.children = children;
	}

	@Column(name = "CLEANING")
	public Double getCleaning() {
		return cleaning;
	}

	public void setCleaning(Double cleaning) {
		this.cleaning = cleaning;
	}

	@Column(name = "CLOTHING")
	public Double getClothing() {
		return clothing;
	}

	public void setClothing(Double clothing) {
		this.clothing = clothing;
	}

	@Column(name = "COLD")
	public Double getCold() {
		return cold;
	}

	public void setCold(Double cold) {
		this.cold = cold;
	}

	@Column(name = "COLLEGE")
	public Double getCollege() {
		return college;
	}

	public void setCollege(Double college) {
		this.college = college;
	}

	@Column(name = "COMMUNICATION")
	public Double getCommunication() {
		return communication;
	}

	public void setCommunication(Double communication) {
		this.communication = communication;
	}

	@Column(name = "COMPETING")
	public Double getCompeting() {
		return competing;
	}

	public void setCompeting(Double competing) {
		this.competing = competing;
	}

	@Column(name = "COMPUTER")
	public Double getComputer() {
		return computer;
	}

	public void setComputer(Double computer) {
		this.computer = computer;
	}

	@Column(name = "CONFUSION")
	public Double getConfusion() {
		return confusion;
	}

	public void setConfusion(Double confusion) {
		this.confusion = confusion;
	}

	@Column(name = "CONTENTMENT")
	public Double getContentment() {
		return contentment;
	}

	public void setContentment(Double contentment) {
		this.contentment = contentment;
	}

	@Column(name = "COOKING")
	public Double getCooking() {
		return cooking;
	}

	public void setCooking(Double cooking) {
		this.cooking = cooking;
	}

	@Column(name = "CRIME")
	public Double getCrime() {
		return crime;
	}

	public void setCrime(Double crime) {
		this.crime = crime;
	}

	@Column(name = "DANCE")
	public Double getDance() {
		return dance;
	}

	public void setDance(Double dance) {
		this.dance = dance;
	}

	@Column(name = "DEATH")
	public Double getDeath() {
		return death;
	}

	public void setDeath(Double death) {
		this.death = death;
	}

	@Column(name = "DECEPTION")
	public Double getDeception() {
		return deception;
	}

	public void setDeception(Double deception) {
		this.deception = deception;
	}

	@Column(name = "DISSAPPOINTMENT")
	public Double getDisappointment() {
		return disappointment;
	}

	public void setDisappointment(Double disappointment) {
		this.disappointment = disappointment;
	}

	@Column(name = "DISGUST")
	public Double getDisgust() {
		return disgust;
	}

	public void setDisgust(Double disgust) {
		this.disgust = disgust;
	}

	@Column(name = "DISPUTE")
	public Double getDispute() {
		return dispute;
	}

	public void setDispute(Double dispute) {
		this.dispute = dispute;
	}

	@Column(name = "DIVINE")
	public Double getDivine() {
		return divine;
	}

	public void setDivine(Double divine) {
		this.divine = divine;
	}

	@Column(name = "DOMESTIC_WORK")
	public Double getDomestic_work() {
		return domestic_work;
	}

	public void setDomestic_work(Double domestic_work) {
		this.domestic_work = domestic_work;
	}

	@Column(name = "DOMINENT_HEIRARCHICAL")
	public Double getDominant_heirarchical() {
		return dominant_heirarchical;
	}

	public void setDominant_heirarchical(Double dominant_heirarchical) {
		this.dominant_heirarchical = dominant_heirarchical;
	}

	@Column(name = "DOMINANT_PERSONALITY")
	public Double getDominant_personality() {
		return dominant_personality;
	}

	public void setDominant_personality(Double dominant_personality) {
		this.dominant_personality = dominant_personality;
	}

	@Column(name = "DRIVING")
	public Double getDriving() {
		return driving;
	}

	public void setDriving(Double driving) {
		this.driving = driving;
	}

	@Column(name = "EATING")
	public Double getEating() {
		return eating;
	}

	public void setEating(Double eating) {
		this.eating = eating;
	}

	@Column(name = "ECONOMICS")
	public Double getEconomics() {
		return economics;
	}

	public void setEconomics(Double economics) {
		this.economics = economics;
	}

	@Column(name = "EMOTIONAL")
	public Double getEmotional() {
		return emotional;
	}

	public void setEmotional(Double emotional) {
		this.emotional = emotional;
	}

	@Column(name = "ENVY")
	public Double getEnvy() {
		return envy;
	}

	public void setEnvy(Double envy) {
		this.envy = envy;
	}

	@Column(name = "EXASPERATION")
	public Double getExasperation() {
		return exasperation;
	}

	public void setExasperation(Double exasperation) {
		this.exasperation = exasperation;
	}

	@Column(name = "EXERCISE")
	public Double getExercise() {
		return exercise;
	}

	public void setExercise(Double exercise) {
		this.exercise = exercise;
	}

	@Column(name = "EXOTIC")
	public Double getExotic() {
		return exotic;
	}

	public void setExotic(Double exotic) {
		this.exotic = exotic;
	}

	@Column(name = "FABRIC")
	public Double getFabric() {
		return fabric;
	}

	public void setFabric(Double fabric) {
		this.fabric = fabric;
	}

	@Column(name = "FAMILY")
	public Double getFamily() {
		return family;
	}

	public void setFamily(Double family) {
		this.family = family;
	}

	@Column(name = "FARMING")
	public Double getFarming() {
		return farming;
	}

	public void setFarming(Double farming) {
		this.farming = farming;
	}

	@Column(name = "FASHION")
	public Double getFashion() {
		return fashion;
	}

	public void setFashion(Double fashion) {
		this.fashion = fashion;
	}

	@Column(name = "FEAR")
	public Double getFear() {
		return fear;
	}

	public void setFear(Double fear) {
		this.fear = fear;
	}

	@Column(name = "FEMININE")
	public Double getFeminine() {
		return feminine;
	}

	public void setFeminine(Double feminine) {
		this.feminine = feminine;
	}

	@Column(name = "FIGHT")
	public Double getFight() {
		return fight;
	}

	public void setFight(Double fight) {
		this.fight = fight;
	}

	@Column(name = "FIRE")
	public Double getFire() {
		return fire;
	}

	public void setFire(Double fire) {
		this.fire = fire;
	}

	@Column(name = "FRIENDS")
	public Double getFriends() {
		return friends;
	}

	public void setFriends(Double friends) {
		this.friends = friends;
	}

	@Column(name = "FUN")
	public Double getFun() {
		return fun;
	}

	public void setFun(Double fun) {
		this.fun = fun;
	}

	@Column(name = "FURNITURE")
	public Double getFurniture() {
		return furniture;
	}

	public void setFurniture(Double furniture) {
		this.furniture = furniture;
	}

	@Column(name = "GAIN")
	public Double getGain() {
		return gain;
	}

	public void setGain(Double gain) {
		this.gain = gain;
	}

	@Column(name = "GIVING")
	public Double getGiving() {
		return giving;
	}

	public void setGiving(Double giving) {
		this.giving = giving;
	}

	@Column(name = "GOVERNMENT")
	public Double getGovernment() {
		return government;
	}

	public void setGovernment(Double government) {
		this.government = government;
	}

	@Column(name = "HATE")
	public Double getHate() {
		return hate;
	}

	public void setHate(Double hate) {
		this.hate = hate;
	}

	@Column(name = "HEALING")
	public Double getHealing() {
		return healing;
	}

	public void setHealing(Double healing) {
		this.healing = healing;
	}

	@Column(name = "HEALTH")
	public Double getHealth() {
		return health;
	}

	public void setHealth(Double health) {
		this.health = health;
	}

	@Column(name = "HEARING")
	public Double getHearing() {
		return hearing;
	}

	public void setHearing(Double hearing) {
		this.hearing = hearing;
	}

	@Column(name = "HELP")
	public Double getHelp() {
		return help;
	}

	public void setHelp(Double help) {
		this.help = help;
	}

	@Column(name = "HEORIC")
	public Double getHeroic() {
		return heroic;
	}

	public void setHeroic(Double heroic) {
		this.heroic = heroic;
	}

	@Column(name = "HIKING")
	public Double getHiking() {
		return hiking;
	}

	public void setHiking(Double hiking) {
		this.hiking = hiking;
	}

	@Column(name = "HIPSTER")
	public Double getHipster() {
		return hipster;
	}

	public void setHipster(Double hipster) {
		this.hipster = hipster;
	}

	@Column(name = "HOME")
	public Double getHome() {
		return home;
	}

	public void setHome(Double home) {
		this.home = home;
	}

	@Column(name = "HORROR")
	public Double getHorror() {
		return horror;
	}

	public void setHorror(Double horror) {
		this.horror = horror;
	}

	@Column(name = "HYGINE")
	public Double getHygiene() {
		return hygiene;
	}

	public void setHygiene(Double hygiene) {
		this.hygiene = hygiene;
	}

	@Column(name = "INDEPENDENCE")
	public Double getIndependence() {
		return independence;
	}

	public void setIndependence(Double independence) {
		this.independence = independence;
	}

	@Column(name = "INJURY")
	public Double getInjury() {
		return injury;
	}

	public void setInjury(Double injury) {
		this.injury = injury;
	}

	@Column(name = "INTERNET")
	public Double getInternet() {
		return internet;
	}

	public void setInternet(Double internet) {
		this.internet = internet;
	}

	@Column(name = "IRRITABILITY")
	public Double getIrritability() {
		return irritability;
	}

	public void setIrritability(Double irritability) {
		this.irritability = irritability;
	}

	@Column(name = "JOURNALISM")
	public Double getJournalism() {
		return journalism;
	}

	public void setJournalism(Double journalism) {
		this.journalism = journalism;
	}

	@Column(name = "JOY")
	public Double getJoy() {
		return joy;
	}

	public void setJoy(Double joy) {
		this.joy = joy;
	}

	@Column(name = "KLL")
	public Double getKill() {
		return kill;
	}

	public void setKill(Double kill) {
		this.kill = kill;
	}

	@Column(name = "LAW")
	public Double getLaw() {
		return law;
	}

	public void setLaw(Double law) {
		this.law = law;
	}

	@Column(name = "LEADER")
	public Double getLeader() {
		return leader;
	}

	public void setLeader(Double leader) {
		this.leader = leader;
	}

	@Column(name = "LEGEND")
	public Double getLegend() {
		return legend;
	}

	public void setLegend(Double legend) {
		this.legend = legend;
	}

	@Column(name = "LEISURE")
	public Double getLeisure() {
		return leisure;
	}

	public void setLeisure(Double leisure) {
		this.leisure = leisure;
	}

	@Column(name = "LIQUID")
	public Double getLiquid() {
		return liquid;
	}

	public void setLiquid(Double liquid) {
		this.liquid = liquid;
	}

	@Column(name = "LISTEN")
	public Double getListen() {
		return listen;
	}

	public void setListen(Double listen) {
		this.listen = listen;
	}

	@Column(name = "LOVE")
	public Double getLove() {
		return love;
	}

	public void setLove(Double love) {
		this.love = love;
	}

	@Column(name = "LUST")
	public Double getLust() {
		return lust;
	}

	public void setLust(Double lust) {
		this.lust = lust;
	}

	@Column(name = "MAGIC")
	public Double getMagic() {
		return magic;
	}

	public void setMagic(Double magic) {
		this.magic = magic;
	}

	@Column(name = "MASCULINE")
	public Double getMasculine() {
		return masculine;
	}

	public void setMasculine(Double masculine) {
		this.masculine = masculine;
	}

	@Column(name = "MEDICAL_EMERGENCY")
	public Double getMedical_emergency() {
		return medical_emergency;
	}

	public void setMedical_emergency(Double medical_emergency) {
		this.medical_emergency = medical_emergency;
	}

	@Column(name = "MEDIVAL")
	public Double getMedieval() {
		return medieval;
	}

	public void setMedieval(Double medieval) {
		this.medieval = medieval;
	}

	@Column(name = "MEETING")
	public Double getMeeting() {
		return meeting;
	}

	public void setMeeting(Double meeting) {
		this.meeting = meeting;
	}

	@Column(name = "MESSAGING")
	public Double getMessaging() {
		return messaging;
	}

	public void setMessaging(Double messaging) {
		this.messaging = messaging;
	}

	@Column(name = "MILITARY")
	public Double getMilitary() {
		return military;
	}

	public void setMilitary(Double military) {
		this.military = military;
	}

	@Column(name = "MONEY")
	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	@Column(name = "MONSTER")
	public Double getMonster() {
		return monster;
	}

	public void setMonster(Double monster) {
		this.monster = monster;
	}

	@Column(name = "MORNING")
	public Double getMorning() {
		return morning;
	}

	public void setMorning(Double morning) {
		this.morning = morning;
	}

	@Column(name = "MOVEMENT")
	public Double getMovement() {
		return movement;
	}

	public void setMovement(Double movement) {
		this.movement = movement;
	}

	@Column(name = "MUSIC")
	public Double getMusic() {
		return music;
	}

	public void setMusic(Double music) {
		this.music = music;
	}

	@Column(name = "MUSICAL")
	public Double getMusical() {
		return musical;
	}

	public void setMusical(Double musical) {
		this.musical = musical;
	}

	@Column(name = "NEGATIVE_EMOTION")
	public Double getNegative_emotion() {
		return negative_emotion;
	}

	public void setNegative_emotion(Double negative_emotion) {
		this.negative_emotion = negative_emotion;
	}

	@Column(name = "NEGLECT")
	public Double getNeglect() {
		return neglect;
	}

	public void setNeglect(Double neglect) {
		this.neglect = neglect;
	}

	@Column(name = "NEGOTIATE")
	public Double getNegotiate() {
		return negotiate;
	}

	public void setNegotiate(Double negotiate) {
		this.negotiate = negotiate;
	}

	@Column(name = "NERVOUSNESS")
	public Double getNervousness() {
		return nervousness;
	}

	public void setNervousness(Double nervousness) {
		this.nervousness = nervousness;
	}

	@Column(name = "NIGHT")
	public Double getNight() {
		return night;
	}

	public void setNight(Double night) {
		this.night = night;
	}

	@Column(name = "NOISE")
	public Double getNoise() {
		return noise;
	}

	public void setNoise(Double noise) {
		this.noise = noise;
	}

	@Column(name = "OCCUPATION")
	public Double getOccupation() {
		return occupation;
	}

	public void setOccupation(Double occupation) {
		this.occupation = occupation;
	}

	@Column(name = "OCEAN")
	public Double getOcean() {
		return ocean;
	}

	public void setOcean(Double ocean) {
		this.ocean = ocean;
	}

	@Column(name = "OFFICE")
	public Double getOffice() {
		return office;
	}

	public void setOffice(Double office) {
		this.office = office;
	}

	@Column(name = "OPTIMISM")
	public Double getOptimism() {
		return optimism;
	}

	public void setOptimism(Double optimism) {
		this.optimism = optimism;
	}

	@Column(name = "ORDR")
	public Double getOrder() {
		return order;
	}

	public void setOrder(Double order) {
		this.order = order;
	}

	@Column(name = "PAIN")
	public Double getPain() {
		return pain;
	}

	public void setPain(Double pain) {
		this.pain = pain;
	}

	@Column(name = "PARTY")
	public Double getParty() {
		return party;
	}

	public void setParty(Double party) {
		this.party = party;
	}

	@Column(name = "PAYMENT")
	public Double getPayment() {
		return payment;
	}

	public void setPayment(Double payment) {
		this.payment = payment;
	}

	@Column(name = "PET")
	public Double getPet() {
		return pet;
	}

	public void setPet(Double pet) {
		this.pet = pet;
	}

	@Column(name = "PHILOSOPHY")
	public Double getPhilosophy() {
		return philosophy;
	}

	public void setPhilosophy(Double philosophy) {
		this.philosophy = philosophy;
	}

	@Column(name = "PHONE")
	public Double getPhone() {
		return phone;
	}

	public void setPhone(Double phone) {
		this.phone = phone;
	}

	@Column(name = "PLANT")
	public Double getPlant() {
		return plant;
	}

	public void setPlant(Double plant) {
		this.plant = plant;
	}

	@Column(name = "PLAY")
	public Double getPlay() {
		return play;
	}

	public void setPlay(Double play) {
		this.play = play;
	}

	@Column(name = "POLITENESS")
	public Double getPoliteness() {
		return politeness;
	}

	public void setPoliteness(Double politeness) {
		this.politeness = politeness;
	}

	@Column(name = "POLITICS")
	public Double getPolitics() {
		return politics;
	}

	public void setPolitics(Double politics) {
		this.politics = politics;
	}

	@Column(name = "POOR")
	public Double getPoor() {
		return poor;
	}

	public void setPoor(Double poor) {
		this.poor = poor;
	}

	@Column(name = "POSITIVE_EMOTION")
	public Double getPositive_emotion() {
		return positive_emotion;
	}

	public void setPositive_emotion(Double positive_emotion) {
		this.positive_emotion = positive_emotion;
	}

	@Column(name = "POWER")
	public Double getPower() {
		return power;
	}

	public void setPower(Double power) {
		this.power = power;
	}

	@Column(name = "PRIDE")
	public Double getPride() {
		return pride;
	}

	public void setPride(Double pride) {
		this.pride = pride;
	}

	@Column(name = "PRISON")
	public Double getPrison() {
		return prison;
	}

	public void setPrison(Double prison) {
		this.prison = prison;
	}

	@Column(name = "PROGRAMMING")
	public Double getProgramming() {
		return programming;
	}

	public void setProgramming(Double programming) {
		this.programming = programming;
	}

	@Column(name = "RAGE")
	public Double getRage() {
		return rage;
	}

	public void setRage(Double rage) {
		this.rage = rage;
	}

	@Column(name = "READING")
	public Double getReading() {
		return reading;
	}

	public void setReading(Double reading) {
		this.reading = reading;
	}

	@Column(name = "REAL_ESTATE")
	public Double getReal_estate() {
		return real_estate;
	}

	public void setReal_estate(Double real_estate) {
		this.real_estate = real_estate;
	}

	@Column(name = "RELIGION")
	public Double getReligion() {
		return religion;
	}

	public void setReligion(Double religion) {
		this.religion = religion;
	}

	@Column(name = "RESTAURANT")
	public Double getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(Double restaurant) {
		this.restaurant = restaurant;
	}

	@Column(name = "RIDICULE")
	public Double getRidicule() {
		return ridicule;
	}

	public void setRidicule(Double ridicule) {
		this.ridicule = ridicule;
	}

	@Column(name = "ROYALTY")
	public Double getRoyalty() {
		return royalty;
	}

	public void setRoyalty(Double royalty) {
		this.royalty = royalty;
	}

	@Column(name = "RURAL")
	public Double getRural() {
		return rural;
	}

	public void setRural(Double rural) {
		this.rural = rural;
	}

	@Column(name = "SADNESS")
	public Double getSadness() {
		return sadness;
	}

	public void setSadness(Double sadness) {
		this.sadness = sadness;
	}

	@Column(name = "SAILING")
	public Double getSailing() {
		return sailing;
	}

	public void setSailing(Double sailing) {
		this.sailing = sailing;
	}

	@Column(name = "SCHOOL")
	public Double getSchool() {
		return school;
	}

	public void setSchool(Double school) {
		this.school = school;
	}

	@Column(name = "SCIENCE")
	public Double getScience() {
		return science;
	}

	public void setScience(Double science) {
		this.science = science;
	}

	@Column(name = "SEXUAL")
	public Double getSexual() {
		return sexual;
	}

	public void setSexual(Double sexual) {
		this.sexual = sexual;
	}

	@Column(name = "SHAME")
	public Double getShame() {
		return shame;
	}

	public void setShame(Double shame) {
		this.shame = shame;
	}

	@Column(name = "SHAPE_AND_SIZE")
	public Double getShape_and_size() {
		return shape_and_size;
	}

	public void setShape_and_size(Double shape_and_size) {
		this.shape_and_size = shape_and_size;
	}

	@Column(name = "SHIP")
	public Double getShip() {
		return ship;
	}

	public void setShip(Double ship) {
		this.ship = ship;
	}

	@Column(name = "SHOPPING")
	public Double getShopping() {
		return shopping;
	}

	public void setShopping(Double shopping) {
		this.shopping = shopping;
	}

	@Column(name = "SLEEP")
	public Double getSleep() {
		return sleep;
	}

	public void setSleep(Double sleep) {
		this.sleep = sleep;
	}

	@Column(name = "SMELL")
	public Double getSmell() {
		return smell;
	}

	public void setSmell(Double smell) {
		this.smell = smell;
	}

	@Column(name = "SOCIAL_MEDIA")
	public Double getSocial_media() {
		return social_media;
	}

	public void setSocial_media(Double social_media) {
		this.social_media = social_media;
	}

	@Column(name = "SOUND")
	public Double getSound() {
		return sound;
	}

	public void setSound(Double sound) {
		this.sound = sound;
	}

	@Column(name = "SPEAKING")
	public Double getSpeaking() {
		return speaking;
	}

	public void setSpeaking(Double speaking) {
		this.speaking = speaking;
	}

	@Column(name = "SPORTS")
	public Double getSports() {
		return sports;
	}

	public void setSports(Double sports) {
		this.sports = sports;
	}

	@Column(name = "STEALING")
	public Double getStealing() {
		return stealing;
	}

	public void setStealing(Double stealing) {
		this.stealing = stealing;
	}

	@Column(name = "STRENGTH")
	public Double getStrength() {
		return strength;
	}

	public void setStrength(Double strength) {
		this.strength = strength;
	}

	@Column(name = "SUFFERING")
	public Double getSuffering() {
		return suffering;
	}

	public void setSuffering(Double suffering) {
		this.suffering = suffering;
	}

	@Column(name = "SUPERHERO")
	public Double getSuperhero() {
		return superhero;
	}

	public void setSuperhero(Double superhero) {
		this.superhero = superhero;
	}

	@Column(name = "SURPRISE")
	public Double getSurprise() {
		return surprise;
	}

	public void setSurprise(Double surprise) {
		this.surprise = surprise;
	}

	@Column(name = "SWEARING_TERM")
	public Double getSwearing_terms() {
		return swearing_terms;
	}

	public void setSwearing_terms(Double swearing_terms) {
		this.swearing_terms = swearing_terms;
	}

	@Column(name = "SWIMMING")
	public Double getSwimming() {
		return swimming;
	}

	public void setSwimming(Double swimming) {
		this.swimming = swimming;
	}

	@Column(name = "SYMPATHY")
	public Double getSympathy() {
		return sympathy;
	}

	public void setSympathy(Double sympathy) {
		this.sympathy = sympathy;
	}

	@Column(name = "TECHNOLOGY")
	public Double getTechnology() {
		return technology;
	}

	public void setTechnology(Double technology) {
		this.technology = technology;
	}

	@Column(name = "TERRORISM")
	public Double getTerrorism() {
		return terrorism;
	}

	public void setTerrorism(Double terrorism) {
		this.terrorism = terrorism;
	}

	@Column(name = "TIMIDITY")
	public Double getTimidity() {
		return timidity;
	}

	public void setTimidity(Double timidity) {
		this.timidity = timidity;
	}

	@Column(name = "TOOL")
	public Double getTool() {
		return tool;
	}

	public void setTool(Double tool) {
		this.tool = tool;
	}

	@Column(name = "TORMENT")
	public Double getTorment() {
		return torment;
	}

	public void setTorment(Double torment) {
		this.torment = torment;
	}

	@Column(name = "TOURISM")
	public Double getTourism() {
		return tourism;
	}

	public void setTourism(Double tourism) {
		this.tourism = tourism;
	}

	@Column(name = "TOY")
	public Double getToy() {
		return toy;
	}

	public void setToy(Double toy) {
		this.toy = toy;
	}

	@Column(name = "TRAVELLING")
	public Double getTraveling() {
		return traveling;
	}

	public void setTraveling(Double traveling) {
		this.traveling = traveling;
	}

	@Column(name = "TRUST")
	public Double getTrust() {
		return trust;
	}

	public void setTrust(Double trust) {
		this.trust = trust;
	}

	@Column(name = "UGLINESS")
	public Double getUgliness() {
		return ugliness;
	}

	public void setUgliness(Double ugliness) {
		this.ugliness = ugliness;
	}

	@Column(name = "URBAN")
	public Double getUrban() {
		return urban;
	}

	public void setUrban(Double urban) {
		this.urban = urban;
	}

	@Column(name = "VACATION")
	public Double getVacation() {
		return vacation;
	}

	public void setVacation(Double vacation) {
		this.vacation = vacation;
	}

	@Column(name = "VALUABLE")
	public Double getValuable() {
		return valuable;
	}

	public void setValuable(Double valuable) {
		this.valuable = valuable;
	}

	@Column(name = "VEHICLE")
	public Double getVehicle() {
		return vehicle;
	}

	public void setVehicle(Double vehicle) {
		this.vehicle = vehicle;
	}

	@Column(name = "VIOLENCE")
	public Double getViolence() {
		return violence;
	}

	public void setViolence(Double violence) {
		this.violence = violence;
	}

	@Column(name = "WAR")
	public Double getWar() {
		return war;
	}

	public void setWar(Double war) {
		this.war = war;
	}

	@Column(name = "WARMTH")
	public Double getWarmth() {
		return warmth;
	}

	public void setWarmth(Double warmth) {
		this.warmth = warmth;
	}

	@Column(name = "WATER")
	public Double getWater() {
		return water;
	}

	public void setWater(Double water) {
		this.water = water;
	}

	@Column(name = "WEAKNESS")
	public Double getWeakness() {
		return weakness;
	}

	public void setWeakness(Double weakness) {
		this.weakness = weakness;
	}

	@Column(name = "WEALTHY")
	public Double getWealthy() {
		return wealthy;
	}

	public void setWealthy(Double wealthy) {
		this.wealthy = wealthy;
	}

	@Column(name = "WEAPON")
	public Double getWeapon() {
		return weapon;
	}

	public void setWeapon(Double weapon) {
		this.weapon = weapon;
	}

	@Column(name = "WEATHER")
	public Double getWeather() {
		return weather;
	}

	public void setWeather(Double weather) {
		this.weather = weather;
	}

	@Column(name = "WEDDING")
	public Double getWedding() {
		return wedding;
	}

	public void setWedding(Double wedding) {
		this.wedding = wedding;
	}

	@Column(name = "WHITE_COLLAR_JOB")
	public Double getWhite_collar_job() {
		return white_collar_job;
	}

	public void setWhite_collar_job(Double white_collar_job) {
		this.white_collar_job = white_collar_job;
	}

	@Column(name = "WORK")
	public Double getWork() {
		return work;
	}

	public void setWork(Double work) {
		this.work = work;
	}

	@Column(name = "WORSHIP")
	public Double getWorship() {
		return worship;
	}

	public void setWorship(Double worship) {
		this.worship = worship;
	}

	@Column(name = "WRITING")
	public Double getWriting() {
		return writing;
	}

	public void setWriting(Double writing) {
		this.writing = writing;
	}

	@Column(name = "YOUTH")
	public Double getYouth() {
		return youth;
	}

	public void setYouth(Double youth) {
		this.youth = youth;
	}

	@Column(name = "ZEST")
	public Double getZest() {
		return zest;
	}

	public void setZest(Double zest) {
		this.zest = zest;
	}
}
