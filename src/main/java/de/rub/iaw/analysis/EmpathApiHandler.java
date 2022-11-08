package de.rub.iaw.analysis;

/**
 * Gets Empath scores by calling analyze from the EmpathClient class and saves them
 *
 * @author Sarah Böning
 **/

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.rub.iaw.domain.Empath;
import de.rub.iaw.exception.EmpathApiException;

public class EmpathApiHandler {

	// For logging exceptions into log file
	private static Logger log = LogManager.getLogger(LiwcApiHandler.class.getName());

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private static String callApi(String text) {
		// returns a Map that looks like: {key=value, key=value, etc.}
		return EmpathClient.analyze(text).toString();
	}

	private static Empath saveScores(Empath empath, String scores) {

		// scores looks like "{key=value, key=value, etc.}". Transforms it into
		// [category, value, etc.] in oder to set the values for the
		// corresponding category(= key) with the PropertyUtils
		String[] resultArray = scores.replaceAll("=", " ").replaceAll(",", "").replaceAll("\\{", "")
				.replaceAll("\\}", "").split(" ");
		for (int i = 0; i < resultArray.length; i++) {
			if (i % 2 == 0) {
				try {
					// calls empath's setters
					PropertyUtils.setProperty(empath, resultArray[i], Double.parseDouble(resultArray[i + 1]));
				} catch (IllegalAccessException e) {
					log.error("ERR_EMPATH_API_HANDLER_ILLEGAL_ACCESS_EXCEPTION");
					throw new EmpathApiException("ERR_EMPATH_API_HANDLER_ILLEGAL_ACCESS_EXCEPTION");
				} catch (InvocationTargetException e) {
					log.error("ERR_EMPATH_API_HANDLER_INVOCATION_TARGET_EXCEPTION");
					throw new EmpathApiException("ERR_EMPATH_API_HANDLER_INVOCATION_TARGET_EXCEPTION");
				} catch (NoSuchMethodException e) {
					log.error("ERR_EMPATH_API_HANDLER_NO_SUCH_METHOD_EXCEPTION");
					throw new EmpathApiException("ERR_EMPATH_API_HANDLER_NO_SUCH_METHOD_EXCEPTION");
				}
			}
		}
		return empath;
	}

	public static Empath getEmpathScoresFromApi(Empath empath, String text) {

		return saveScores(empath, callApi(text));
	}
}