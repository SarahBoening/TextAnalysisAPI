package de.rub.iaw.util;

/**
 * Used for input validation of new prompts
 * @author blunk
 */
public enum PromptingEnum_Target {
	ALL,		// All below included
	AUTHOR,		// Someone who started a thread/discussion/topic
	REPLIER,	// Someone who replied in a thread/discussion/topic, but is not author
	VIEWER,		// Someone who just looked at the topic but neither started it nor replied in it 
	MENTOR,
	MENTEE,
	JOB_ROLE,	// Differentiate according to job roles in target application
}