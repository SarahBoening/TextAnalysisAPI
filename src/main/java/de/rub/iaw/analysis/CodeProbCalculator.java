package de.rub.iaw.analysis;

import de.rub.iaw.PropertiesConfiguration;
import de.rub.iaw.domain.Empath;

import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mariuszgromada.math.mxparser.*;
import de.rub.iaw.domain.Liwc;
import de.rub.iaw.domain.TextCodeProb;
import de.rub.iaw.exception.CodeProbCalculatorException;

/**
 * Calculates the probability of a text being assigned to a certain code. The
 * probability pi(x) is calculated by using a systematic component z(x) which is
 * different depending on the code and the content analysis program used based
 * on the Bachelor thesis of Britta Rannenberg
 * 
 * @author Sarah Böning
 **/

public class CodeProbCalculator {

	private static Properties properties = PropertiesConfiguration.PropertyLoadHelper
			.loadProperties("application.properties");

	// For logging exceptions into log file
	private static Logger log = LogManager.getLogger(CodeProbCalculator.class.getName());

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	// Probability pi(x) of a text being assigned to a certain code
	private static Double probability(Double z) {

		return 1.0 / (1.0 + (Double) Math.exp(-z));
	}

	// !!! THIS ONLY WORKS IF THE EMPATH/LIWC CATAGORIES ARE TYPED IN CORRECTLY
	// (IN THE APPLICATION.PROPERTIES) OTHERWISE AN EXCEPTION IS THROWN !!!
	private static String adjustExpression(String expression, Object obj) {

		// sets everything to lower case in case a category is typed in with an
		// Upper case to minimize exceptions
		expression = expression.toLowerCase();
		// removes all white spaces and non-visible characters
		// https://stackoverflow.com/questions/5455794/removing-whitespace-from-strings-in-java
		expression = expression.replaceAll("\\s+", "");
		// RegEx to split string into [number,*,variable,+,number,*,variable,
		// etc.]
		String[] expSplit = expression.split("(?<=[-+*])|(?=[-+*])");
		for (int i = 0; i < expSplit.length; i++) {
			// extracts the variables/categories out of the string
			// matches tests if i is a word
			if (expSplit[i].matches("\\w+")) {
				try {
					// calls the variables' getters (of the liwc or empath
					// object) and sets them to 1 or 0
					// for the binary regression
					Object value = PropertyUtils.getProperty(obj, expSplit[i]);
					expSplit[i] = value.toString();
				} catch (IllegalAccessException e) {
					log.error("ERR_CODEPROBCALCULATOR_ILLEGAL_ACCESS_EXCEPTION");
					throw new CodeProbCalculatorException("ERR_CODEPROBCALCULATOR_ILLEGAL_ACCESS_EXCEPTION");
				} catch (InvocationTargetException e) {
					log.error("ERR_CODEPROBCALCULATOR_INVOCATION_TARGET_EXCEPTION");
					throw new CodeProbCalculatorException("ERR_CODEPROBCALCULATOR_INVOCATION_TARGET_EXCEPTION");
				} catch (NoSuchMethodException e) {
					log.error("ERR_CODEPROBCALCULATOR_NO_SUCH_METHOD_EXCEPTION");
					throw new CodeProbCalculatorException("ERR_CODEPROBCALCULATOR_NO_SUCH_METHOD_EXCEPTION");
				}
			}
		}
		// putting the formula back together as a readable string for the parser
		expression = java.util.Arrays.toString(expSplit).replaceAll(",", "").replaceAll("\\]", "").replaceAll("\\[", "")
				.replaceAll("\\(", "").replaceAll("\\)", "");
		return expression;
	}

	// These methods calculate the probability for a certain category for liwc
	// and for empath
	// therefore the formula is loaded from application.properties and set zero
	// if no formula is available
	// if there was returned a valid formula (thus not just one 0) it is formed
	// into an expression for a mathematical expression parser (see above)
	// then the parser calculates the string and the result is saved and
	// returned

	private static TextCodeProb calculateCode0(TextCodeProb textCodeProb, Empath empath) {

		String exp = properties.getProperty("textcodeprob.empath.formula0");
		if (exp.equals("0") || exp.isEmpty())
			textCodeProb.setEmpath_0(0.00);
		else {
			Expression e = new Expression(adjustExpression(exp, empath));
			textCodeProb.setEmpath_0(probability(e.calculate()));
		}
		return textCodeProb;
	}

	private static TextCodeProb calculateCode1(TextCodeProb textCodeProb, Empath empath) {

		String exp = properties.getProperty("textcodeprob.empath.formula1");
		if (exp.equals("0") || exp.isEmpty())
			textCodeProb.setEmpath_1(0.00);
		else {
			Expression e = new Expression(adjustExpression(exp, empath));
			textCodeProb.setEmpath_1(probability(e.calculate()));
		}
		return textCodeProb;
	}

	private static TextCodeProb calculateCode2a(TextCodeProb textCodeProb, Empath empath) {

		String exp = properties.getProperty("textcodeprob.empath.formula2a");
		if (exp.equals("0") || exp.isEmpty())
			textCodeProb.setEmpath_2a(0.00);
		else {
			Expression e = new Expression(adjustExpression(exp, empath));
			textCodeProb.setEmpath_2a(probability(e.calculate()));
		}
		return textCodeProb;
	}

	private static TextCodeProb calculateCode2b(TextCodeProb textCodeProb, Empath empath) {

		String exp = properties.getProperty("textcodeprob.empath.formula2b");
		if (exp.equals("0") || exp.isEmpty())
			textCodeProb.setEmpath_2b(0.00);
		else {
			Expression e = new Expression(adjustExpression(exp, empath));
			textCodeProb.setEmpath_2b(probability(e.calculate()));
		}
		return textCodeProb;
	}

	private static TextCodeProb calculateCode3(TextCodeProb textCodeProb, Empath empath) {

		String exp = properties.getProperty("textcodeprob.empath.formula3");
		if (exp.equals("0") || exp.isEmpty())
			textCodeProb.setEmpath_3(0.00);
		else {
			Expression e = new Expression(adjustExpression(exp, empath));
			textCodeProb.setEmpath_3(probability(e.calculate()));
		}
		return textCodeProb;
	}

	private static TextCodeProb calculateCode4(TextCodeProb textCodeProb, Empath empath) {

		String exp = properties.getProperty("textcodeprob.empath.formula4");
		if (exp.equals("0") || exp.isEmpty())
			textCodeProb.setEmpath_4(0.00);
		else {
			Expression e = new Expression(adjustExpression(exp, empath));
			textCodeProb.setEmpath_4(probability(e.calculate()));
		}
		return textCodeProb;
	}

	private static TextCodeProb calculateCode5(TextCodeProb textCodeProb, Empath empath) {

		String exp = properties.getProperty("textcodeprob.empath.formula5");
		if (exp.equals("0") || exp.isEmpty())
			textCodeProb.setEmpath_5(0.00);
		else {
			Expression e = new Expression(adjustExpression(exp, empath));
			textCodeProb.setEmpath_5(probability(e.calculate()));
		}
		return textCodeProb;
	}

	private static TextCodeProb calculateCode6a(TextCodeProb textCodeProb, Empath empath) {

		String exp = properties.getProperty("textcodeprob.empath.formula6a");
		if (exp.equals("0") || exp.isEmpty())
			textCodeProb.setEmpath_6a(0.00);
		else {
			Expression e = new Expression(adjustExpression(exp, empath));
			textCodeProb.setEmpath_6a(probability(e.calculate()));
		}
		return textCodeProb;
	}

	private static TextCodeProb calculateCode6b(TextCodeProb textCodeProb, Empath empath) {

		String exp = properties.getProperty("textcodeprob.empath.formula6b");
		if (exp.equals("0") || exp.isEmpty())
			textCodeProb.setEmpath_6b(0.00);
		else {
			Expression e = new Expression(adjustExpression(exp, empath));
			textCodeProb.setEmpath_6b(probability(e.calculate()));
		}
		return textCodeProb;
	}

	private static TextCodeProb calculateCode7(TextCodeProb textCodeProb, Empath empath) {

		String exp = properties.getProperty("textcodeprob.empath.formula7");
		if (exp.equals("0") || exp.isEmpty())
			textCodeProb.setEmpath_7(0.00);
		else {
			Expression e = new Expression(adjustExpression(exp, empath));
			textCodeProb.setEmpath_7(probability(e.calculate()));
		}
		return textCodeProb;
	}

	private static TextCodeProb calculateCode84(TextCodeProb textCodeProb, Empath empath) {

		String exp = properties.getProperty("textcodeprob.empath.formula84");
		if (exp.equals("0") || exp.isEmpty())
			textCodeProb.setEmpath_84(0.00);
		else {
			Expression e = new Expression(adjustExpression(exp, empath));
			textCodeProb.setEmpath_84(probability(e.calculate()));
		}
		return textCodeProb;
	}

	private static TextCodeProb calculateCode85(TextCodeProb textCodeProb, Empath empath) {

		String exp = properties.getProperty("textcodeprob.empath.formula85");
		if (exp.equals("0") || exp.isEmpty())
			textCodeProb.setEmpath_85(0.00);
		else {
			Expression e = new Expression(adjustExpression(exp, empath));
			textCodeProb.setEmpath_85(probability(e.calculate()));
		}
		return textCodeProb;
	}

	private static TextCodeProb calculateCode9a(TextCodeProb textCodeProb, Empath empath) {

		String exp = properties.getProperty("textcodeprob.empath.formula9a");
		if (exp.equals("0") || exp.isEmpty())
			textCodeProb.setEmpath_9a(0.00);
		else {
			Expression e = new Expression(adjustExpression(exp, empath));
			textCodeProb.setEmpath_9a(probability(e.calculate()));
		}
		return textCodeProb;
	}

	private static TextCodeProb calculateCode9b(TextCodeProb textCodeProb, Empath empath) {

		String exp = properties.getProperty("textcodeprob.empath.formula9b");
		if (exp.equals("0") || exp.isEmpty())
			textCodeProb.setEmpath_9b(0.00);
		else {
			Expression e = new Expression(adjustExpression(exp, empath));
			textCodeProb.setEmpath_9b(probability(e.calculate()));
		}
		return textCodeProb;
	}

	private static TextCodeProb calculateCode10(TextCodeProb textCodeProb, Empath empath) {

		String exp = properties.getProperty("textcodeprob.empath.formula10");
		if (exp.equals("0") || exp.isEmpty())
			textCodeProb.setEmpath_10(0.00);
		else {
			Expression e = new Expression(adjustExpression(exp, empath));
			textCodeProb.setEmpath_10(probability(e.calculate()));
		}
		return textCodeProb;
	}

	private static TextCodeProb calculateCodeA1(TextCodeProb textCodeProb, Empath empath) {

		String exp = properties.getProperty("textcodeprob.empath.formulaA1");
		if (exp.equals("0") || exp.isEmpty())
			textCodeProb.setEmpath_A1(0.00);
		else {
			Expression e = new Expression(adjustExpression(exp, empath));
			textCodeProb.setEmpath_A1(probability(e.calculate()));
		}
		return textCodeProb;
	}

	private static TextCodeProb calculateCodeA2(TextCodeProb textCodeProb, Empath empath) {

		String exp = properties.getProperty("textcodeprob.empath.formulaA2");
		if (exp.equals("0") || exp.isEmpty())
			textCodeProb.setEmpath_A2(0.00);
		else {
			Expression e = new Expression(adjustExpression(exp, empath));
			textCodeProb.setEmpath_A2(probability(e.calculate()));
		}
		return textCodeProb;
	}

	private static TextCodeProb calculateCodeA3(TextCodeProb textCodeProb, Empath empath) {

		String exp = properties.getProperty("textcodeprob.empath.formulaA3");
		if (exp.equals("0") || exp.isEmpty())
			textCodeProb.setEmpath_A3(0.00);
		else {
			Expression e = new Expression(adjustExpression(exp, empath));
			textCodeProb.setEmpath_A3(probability(e.calculate()));
		}
		return textCodeProb;
	}

	private static TextCodeProb calculateCode0(TextCodeProb textCodeProb, Liwc liwc) {

		String exp = properties.getProperty("textcodeprob.liwc.formula0");
		if (exp.equals("0") || exp.isEmpty())
			textCodeProb.setLiwc_0(0.00);
		else {
			Expression e = new Expression(adjustExpression(exp, liwc));
			textCodeProb.setLiwc_0(probability(e.calculate()));
		}
		return textCodeProb;
	}

	private static TextCodeProb calculateCode1(TextCodeProb textCodeProb, Liwc liwc) {

		String exp = properties.getProperty("textcodeprob.liwc.formula1");
		if (exp.equals("0") || exp.isEmpty())
			textCodeProb.setLiwc_1(0.00);
		else {
			Expression e = new Expression(adjustExpression(exp, liwc));
			textCodeProb.setLiwc_1(probability(e.calculate()));
		}
		return textCodeProb;
	}

	private static TextCodeProb calculateCode2a(TextCodeProb textCodeProb, Liwc liwc) {

		String exp = properties.getProperty("textcodeprob.liwc.formula2a");
		if (exp.equals("0") || exp.isEmpty())
			textCodeProb.setLiwc_2a(0.00);
		else {
			Expression e = new Expression(adjustExpression(exp, liwc));
			textCodeProb.setLiwc_2a(probability(e.calculate()));
		}
		return textCodeProb;
	}

	private static TextCodeProb calculateCode2b(TextCodeProb textCodeProb, Liwc liwc) {

		String exp = properties.getProperty("textcodeprob.liwc.formula2b");
		if (exp.equals("0") || exp.isEmpty())
			textCodeProb.setLiwc_2b(0.00);
		else {
			Expression e = new Expression(adjustExpression(exp, liwc));
			textCodeProb.setLiwc_2b(probability(e.calculate()));
		}
		return textCodeProb;
	}

	private static TextCodeProb calculateCode3(TextCodeProb textCodeProb, Liwc liwc) {

		String exp = properties.getProperty("textcodeprob.liwc.formula3");
		if (exp.equals("0") || exp.isEmpty())
			textCodeProb.setLiwc_3(0.00);
		else {
			Expression e = new Expression(adjustExpression(exp, liwc));
			textCodeProb.setLiwc_3(probability(e.calculate()));
		}
		return textCodeProb;
	}

	private static TextCodeProb calculateCode4(TextCodeProb textCodeProb, Liwc liwc) {

		String exp = properties.getProperty("textcodeprob.liwc.formula4");
		if (exp.equals("0") || exp.isEmpty())
			textCodeProb.setLiwc_4(0.00);
		else {
			Expression e = new Expression(adjustExpression(exp, liwc));
			textCodeProb.setLiwc_4(probability(e.calculate()));
		}
		return textCodeProb;
	}

	private static TextCodeProb calculateCode5(TextCodeProb textCodeProb, Liwc liwc) {

		String exp = properties.getProperty("textcodeprob.liwc.formula5");
		if (exp.equals("0") || exp.isEmpty())
			textCodeProb.setLiwc_5(0.00);
		else {
			Expression e = new Expression(adjustExpression(exp, liwc));
			textCodeProb.setLiwc_5(probability(e.calculate()));
		}
		return textCodeProb;
	}

	private static TextCodeProb calculateCode6a(TextCodeProb textCodeProb, Liwc liwc) {

		String exp = properties.getProperty("textcodeprob.liwc.formula6a");
		if (exp.equals("0") || exp.isEmpty())
			textCodeProb.setLiwc_6a(0.00);
		else {
			Expression e = new Expression(adjustExpression(exp, liwc));
			textCodeProb.setLiwc_6a(probability(e.calculate()));
		}
		return textCodeProb;
	}

	private static TextCodeProb calculateCode6b(TextCodeProb textCodeProb, Liwc liwc) {

		String exp = properties.getProperty("textcodeprob.liwc.formula6b");
		if (exp.equals("0") || exp.isEmpty())
			textCodeProb.setLiwc_6b(0.00);
		else {
			Expression e = new Expression(adjustExpression(exp, liwc));
			textCodeProb.setLiwc_6b(probability(e.calculate()));
		}
		return textCodeProb;
	}

	private static TextCodeProb calculateCode7(TextCodeProb textCodeProb, Liwc liwc) {

		String exp = properties.getProperty("textcodeprob.liwc.formula7");
		if (exp.equals("0") || exp.isEmpty())
			textCodeProb.setLiwc_7(0.00);
		else {
			Expression e = new Expression(adjustExpression(exp, liwc));
			textCodeProb.setLiwc_7(probability(e.calculate()));
		}
		return textCodeProb;
	}

	private static TextCodeProb calculateCode84(TextCodeProb textCodeProb, Liwc liwc) {

		String exp = properties.getProperty("textcodeprob.liwc.formula84");
		if (exp.equals("0") || exp.isEmpty())
			textCodeProb.setLiwc_84(0.00);
		else {
			Expression e = new Expression(adjustExpression(exp, liwc));
			textCodeProb.setLiwc_84(probability(e.calculate()));
		}
		return textCodeProb;
	}

	private static TextCodeProb calculateCode85(TextCodeProb textCodeProb, Liwc liwc) {

		String exp = properties.getProperty("textcodeprob.liwc.formula85");
		if (exp.equals("0") || exp.isEmpty())
			textCodeProb.setLiwc_85(0.00);
		else {
			Expression e = new Expression(adjustExpression(exp, liwc));
			textCodeProb.setLiwc_85(probability(e.calculate()));
		}
		return textCodeProb;
	}

	private static TextCodeProb calculateCode9a(TextCodeProb textCodeProb, Liwc liwc) {

		String exp = properties.getProperty("textcodeprob.liwc.formula9a");
		if (exp.equals("0") || exp.isEmpty())
			textCodeProb.setLiwc_9a(0.00);
		else {
			Expression e = new Expression(adjustExpression(exp, liwc));
			textCodeProb.setLiwc_9a(probability(e.calculate()));
		}
		return textCodeProb;
	}

	private static TextCodeProb calculateCode9b(TextCodeProb textCodeProb, Liwc liwc) {

		String exp = properties.getProperty("textcodeprob.liwc.formula9b");
		if (exp.equals("0") || exp.isEmpty())
			textCodeProb.setLiwc_9b(0.00);
		else {
			Expression e = new Expression(adjustExpression(exp, liwc));
			textCodeProb.setLiwc_9b(probability(e.calculate()));
		}
		return textCodeProb;
	}

	private static TextCodeProb calculateCode10(TextCodeProb textCodeProb, Liwc liwc) {

		String exp = properties.getProperty("textcodeprob.liwc.formula10");
		if (exp.equals("0") || exp.isEmpty())
			textCodeProb.setLiwc_10(0.00);
		else {
			Expression e = new Expression(adjustExpression(exp, liwc));
			textCodeProb.setLiwc_10(probability(e.calculate()));
		}
		return textCodeProb;
	}

	private static TextCodeProb calculateCodeA1(TextCodeProb textCodeProb, Liwc liwc) {

		String exp = properties.getProperty("textcodeprob.liwc.formulaA1");
		if (exp.equals("0") || exp.isEmpty())
			textCodeProb.setLiwc_A1(0.00);
		else {
			Expression e = new Expression(adjustExpression(exp, liwc));
			textCodeProb.setLiwc_A1(probability(e.calculate()));
		}
		return textCodeProb;
	}

	private static TextCodeProb calculateCodeA2(TextCodeProb textCodeProb, Liwc liwc) {

		String exp = properties.getProperty("textcodeprob.liwc.formulaA2");
		if (exp.equals("0") || exp.isEmpty())
			textCodeProb.setLiwc_A2(0.00);
		else {
			Expression e = new Expression(adjustExpression(exp, liwc));
			textCodeProb.setLiwc_A2(probability(e.calculate()));
		}
		return textCodeProb;
	}

	private static TextCodeProb calculateCodeA3(TextCodeProb textCodeProb, Liwc liwc) {

		String exp = properties.getProperty("textcodeprob.liwc.formulaA3");
		if (exp.equals("0") || exp.isEmpty())
			textCodeProb.setLiwc_A3(0.00);
		else {
			Expression e = new Expression(adjustExpression(exp, liwc));
			textCodeProb.setLiwc_A3(probability(e.calculate()));
		}
		return textCodeProb;
	}

	// This method gets called from the controller and returns the calculated
	// probabilities for Liwc and for Empath

	public static TextCodeProb getProb(TextCodeProb textCodeProb, Empath empath, Liwc liwc) {

		textCodeProb = calculateCode0(textCodeProb, liwc);
		textCodeProb = calculateCode1(textCodeProb, liwc);
		textCodeProb = calculateCode2a(textCodeProb, liwc);
		textCodeProb = calculateCode2b(textCodeProb, liwc);
		textCodeProb = calculateCode3(textCodeProb, liwc);
		textCodeProb = calculateCode4(textCodeProb, liwc);
		textCodeProb = calculateCode5(textCodeProb, liwc);
		textCodeProb = calculateCode6a(textCodeProb, liwc);
		textCodeProb = calculateCode6b(textCodeProb, liwc);
		textCodeProb = calculateCode7(textCodeProb, liwc);
		textCodeProb = calculateCode84(textCodeProb, liwc);
		textCodeProb = calculateCode85(textCodeProb, liwc);
		textCodeProb = calculateCode9a(textCodeProb, liwc);
		textCodeProb = calculateCode9b(textCodeProb, liwc);
		textCodeProb = calculateCode10(textCodeProb, liwc);
		textCodeProb = calculateCodeA1(textCodeProb, liwc);
		textCodeProb = calculateCodeA2(textCodeProb, liwc);
		textCodeProb = calculateCodeA3(textCodeProb, liwc); 
		textCodeProb = calculateCode0(textCodeProb, empath);
		textCodeProb = calculateCode1(textCodeProb, empath);
		textCodeProb = calculateCode2a(textCodeProb, empath);
		textCodeProb = calculateCode2b(textCodeProb, empath);
		textCodeProb = calculateCode3(textCodeProb, empath);
		textCodeProb = calculateCode4(textCodeProb, empath);
		textCodeProb = calculateCode5(textCodeProb, empath);
		textCodeProb = calculateCode6a(textCodeProb, empath);
		textCodeProb = calculateCode6b(textCodeProb, empath);
		textCodeProb = calculateCode7(textCodeProb, empath);
		textCodeProb = calculateCode84(textCodeProb, empath);
		textCodeProb = calculateCode85(textCodeProb, empath);
		textCodeProb = calculateCode9a(textCodeProb, empath);
		textCodeProb = calculateCode9b(textCodeProb, empath);
		textCodeProb = calculateCode10(textCodeProb, empath);
		textCodeProb = calculateCodeA1(textCodeProb, empath);
		textCodeProb = calculateCodeA2(textCodeProb, empath);
		textCodeProb = calculateCodeA3(textCodeProb, empath);
		return textCodeProb;
	}
}
