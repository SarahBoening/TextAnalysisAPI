package de.rub.iaw.analysis;

/**
 * This class is a shortened Java implementation of Ethan Fast's Empath client written in Python.
 * (source: see https://github.com/Ejhfast/empath-client)
 * For the license please see '.../rub_rotterdam/src/main/resources/empath-client/license.txt'
 * Empath is a text analysis tool using relative dictionary based-word counts
 *
 * @author Sarah Böning
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jboss.dna.common.text.Inflector;

import de.rub.iaw.PropertiesConfiguration;
import de.rub.iaw.exception.EmpathApiException;

public class EmpathClient {

	// When the properties are changed please be aware that the database will get inconsistent as different values will be saved in the same columns
	private static Properties properties = PropertiesConfiguration.PropertyLoadHelper
			.loadProperties("application.properties");

	// For logging exceptions into log file
	private static Logger log = LogManager.getLogger(LiwcApiHandler.class.getName());

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	// reads all categories from categories.tsv and constructs the dictionary
	// based on empath's core.py method load()
	private static Map<String, ArrayList<String>> loadCategories() {

		Map<String, ArrayList<String>> dic = new HashMap<String, ArrayList<String>>();

		try {
			// https://stackoverflow.com/questions/19414453/how-to-get-resources-directory-path-programmatically
			BufferedReader categories = new BufferedReader(new FileReader(new File(EmpathClient.class.getClassLoader()
					.getResource("empath-client/empath/data/categories.tsv").toURI())));
			String line = categories.readLine();
			while (line != null) {
				// splits by tabs. 1. word of the line is the category the rest
				// are its terms
				// white spaces from the split are removed
				String[] column = line.split("\\t");
				ArrayList<String> termList = new ArrayList<String>();
				for (int i = 1; i < column.length; i++) {
					termList.add(column[i].replaceAll("\\s+", ""));
				}
				dic.put(column[0].replaceAll("\\s+", ""), termList);
				line = categories.readLine();
			}
			categories.close();
		} catch (FileNotFoundException e) {
			log.error("ERR_EMPATH_CLIENT_FILE_NOT_FOUND_EXCEPTION");
			throw new EmpathApiException("ERR_EMPATH_CLIENT_FILE_NOT_FOUND_EXCEPTION");
		} catch (IOException e) {
			log.error("ERR_EMPATH_CLIENT_IO_EXCEPTION");
			throw new EmpathApiException("ERR_EMPATH_CLIENT_IO_EXCEPTION");
		} catch (URISyntaxException e) {
			log.error("ERR_EMPATH_CLIENT_URI_SYNTAX_EXCEPTION");
			throw new EmpathApiException("ERR_EMPATH_CLIENT_URI_SYNTAX_EXCEPTION");
		}
		return dic;
	}

	// This method analyzes the text
	// based on empath's core.py method analyze()
	public static Map<String, Double> analyze(String text) {

		Map<String, ArrayList<String>> dic = loadCategories();
		// inverse dictionary to count the token of words
		Map<String, ArrayList<String>> invCats = new HashMap<String, ArrayList<String>>();
		Map<String, Double> result = new HashMap<String, Double>();
		Set<String> categories = dic.keySet();

		// text is split into individual words, white spaces need to be removed
		// for proper
		// analysis
		// therefore the word count is easily determined via .length
		String[] doc = text.split("\\s+");
		double wordCount = (double) doc.length;

		for (String cat : categories) {
			// initializes the result map
			result.put(cat, 0.0);

			ArrayList<String> terms = dic.get(cat);

			// construction of the inverse dictionary
			// Takes every word that belongs to a category as key and adds the
			// categories as a value list
			for (String t : terms) {
				if (!invCats.containsKey(t))
					invCats.put(t, new ArrayList<String>());
				ArrayList<String> catList = invCats.get(t);
				if (!catList.contains(cat)) {
					catList.add(cat);
					invCats.put(t, catList);
				}
			}
		}

		for (String word : doc) {
			word = word.replaceAll("\\s+", "");
			String singular = "";
			ArrayList<String> invCategories = new ArrayList<String>();
			// Bug fixes: removes all punctuation => words at the end of a
			// sentence/before a comma get recognized, sets word to lower case
			// => capitalized words get recognized, word gets "singularized" =>
			// plurals get recognized
			if (properties.getProperty("empath.analyze.correction").toLowerCase().equals("true")) {
				// fixes punctuation and capitalization
				word = word.toLowerCase().replaceAll("\\p{P}+", "");
				// fixes plural issues
				Inflector inflector = new Inflector();
				singular = inflector.singularize(word);
				invCategories = invCats.get(singular);
			} else {
				invCategories = invCats.get(word);
			}
			// Checks for every word of the text, that is analyzed if the
			// word is in the inverse dictionary. If it is the count of all
			// categories the word belongs to is increased
			if (invCategories != null) {
				// counts the frequency of a word in all categories
				for (String cat : invCategories) {
					Double count = result.get(cat) + 1.0;
					result.put(cat, count);
				}
			}
		}

		// Checks if normalize is true or not specified
		if (properties.getProperty("empath.analyze.normalize").toLowerCase().equals("true")
				|| properties.getProperty("empath.analyze.normalize").isEmpty()) {
			// if normalize = True: frequency => relative frequency
			for (String cat : categories) {
				if (wordCount == 0.0)
					return null;
				else {
					Double relativeFreq = result.get(cat) / wordCount;
					result.put(cat, relativeFreq);
				}
			}
		}
		return result;
	}
}
