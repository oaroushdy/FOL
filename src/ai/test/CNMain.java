package ai.test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ai.logic.utilities.Common;
import ai.logic.utilities.Sentence;
import ai.logic.utilities.sentence.Connector;
import ai.logic.utilities.sentence.term.Atom;
import ai.logic.utilities.sentence.term.atom.Predicate;
import ai.logic.utilities.sentence.term.atom.Variable;
import ai.logic.utilities.sentence.term.quantifier.AQuantifier;
import ai.logic.utilities.sentence.term.quantifier.EQuantifier;

import com.rits.cloning.Cloner;

public class CNMain {

	public static Cloner clone = new Cloner();
	public static boolean visualize;

	public static void main(String[] args) {
		// A:x[P (x) <=> (Q(x) & E:y[Q(y) & R(y, x)])]

		Variable x = new Variable("x");
		Variable y = new Variable("x");

		Predicate p = new Predicate("P", x);
		Predicate R = new Predicate("R", new Atom[] { y, x });
		Predicate qx = new Predicate("Q", x);
		Predicate qy = new Predicate("Q", y);
		Sentence in = new EQuantifier(new Sentence(qy, Connector.AND, R), x);

		Sentence out = new Sentence(qx, Connector.AND, in);
		Sentence f = new AQuantifier(new Sentence(p, Connector.IFF, out), x);

		ClauseForm(f, true);
	}

	public static void ClauseForm(Sentence f, boolean visualize) {
		CNMain.visualize = visualize;
		prettyPrint(0, f.toString());

		f.Step1EliminateIFF();
		prettyPrint(1, f.toString());

		f.Step2RemoveIF();
		prettyPrint(2, f.toString());

		f.Step3PushNegative();
		prettyPrint(3, f.toString());

		f.Step4Standardize();
		prettyPrint(4, f.toString());

		f.Step5Skolomize();
		prettyPrint(5, f.toString());

		f.Step6EliminateAQuantifier();
		prettyPrint(6, f.toString());

		f.Step7Expand();
		prettyPrint(7, f.toString());

		String s = Step8Flatten(f);
		prettyPrint(8, s);

		s = Step9(s);
		prettyPrint(9, s);

		s = Step10(s);
		prettyPrint(10, s);

		s = Step11(s);
		prettyPrint(11, s);
	}

	public static void prettyPrint(int step, String result) {
		if (visualize)
			System.out.println("Step " + step + ":\n" + result + "\n");
		else if (step == 11)
			System.out.println("Clause Form:" + result);
	}

	public static String Step8Flatten(Sentence f) {
		String result = "";
		String s = f.toString();
		s = s.substring(1, s.length());
		int firstCharOr = s.indexOf("|");
		int firstCharAnd = s.indexOf("&");
		int firstCharType = (firstCharOr > firstCharAnd) ? Common.EXPAND_OR
				: Common.EXPAND_AND;
		int charIndex = (firstCharOr > firstCharAnd) ? firstCharOr
				: firstCharAnd;
		// println(s.substring(0, charIndex));
		result += s.substring(0, charIndex) + "\n";

		s = s.substring(charIndex + 1, s.length());

		switch (firstCharType) {
		case Common.EXPAND_AND:
			while ((charIndex = s.indexOf("&")) != -1) {
				// println("&" + s.substring(0, charIndex));
				result += "&" + s.substring(0, charIndex) + "\n";
				s = s.substring(charIndex + 1, s.length());
			}
			if (!result.isEmpty())
				result += "&" + s + "\n";
			break;
		case Common.EXPAND_OR:
			while ((charIndex = s.indexOf("|")) != -1) {
				// println("|" + s.substring(0, charIndex));
				result += "|" + s.substring(0, charIndex) + "\n";
				s = s.substring(charIndex + 1, s.length());
			}
			if (!result.isEmpty())
				result += "|" + s + "\n";
			break;
		}
		return Step8Flatten(result);
	}

	public static void println(String s) {
		System.out.println(s);
	}

	public static String Step8Flatten(String s) {
		String split[] = s.split("\\r?\\n");
		String result = "";
		StringBuilder resultLine;
		for (String line : split) {
			resultLine = new StringBuilder(line);
			int open = -1;
			ArrayList<Integer> openBrackets = new ArrayList<>();
			while ((open = line.indexOf("(", open + 1)) != -1) {
				openBrackets.add(open);
			}
			int i = 0;
			// Make only one main opening bracket
			while ((openBrackets.get(i) + 1) == openBrackets.get(i + 1)) {
				resultLine.setCharAt(openBrackets.get(i), '\0');
				openBrackets.remove(i);
			}
			// Make number of closing brackets = new number of Opening brackets
			// calculated in previous step using stack
			Stack<Integer> brackets = new Stack<>();
			for (i = 0; i < resultLine.length(); i++) {
				if (line.charAt(i) == '(')
					brackets.push(i);
				else if (line.charAt(i) == ')')
					if (!brackets.isEmpty())
						brackets.pop();
					else
						resultLine.setCharAt(i, '\0');
			}
			result += resultLine.toString() + "\n";
		}
		return result.trim();
	}

	/**
	 * Transform disjunctions or conjunctions to clause form
	 * 
	 * @param s
	 *            sentence to transform
	 * @return
	 */
	public static String Step9(String s) {
		int firstCharOr = s.indexOf("|");
		int firstCharAnd = s.indexOf("&");
		int firstCharType = (firstCharOr < firstCharAnd) ? Common.EXPAND_OR
				: Common.EXPAND_AND;
		switch (firstCharType) {
		case Common.EXPAND_OR:
			s = s.replaceAll(" [|] ", ",");
			break;
		case Common.EXPAND_AND:
			s = s.replaceAll(" [&] ", ",");
			break;
		}
		String results = "";
		StringBuilder result = new StringBuilder();
		String split[] = s.split("\\r?\\n");
		for (String line : split) {
			result = new StringBuilder(line);
			result.setCharAt(result.indexOf("("), '{');
			result.setCharAt(result.lastIndexOf(")"), '}');
			results += result.toString() + "\n";
		}

		return results.trim();
	}

	/**
	 * Transform disjunctions or conjunctions to clause form
	 * 
	 * @param s
	 *            sentence to transform
	 * @return
	 */
	public static String Step10(String s) {
		StringBuilder result = new StringBuilder(s);
		result.insert(0, '{');
		result.insert(result.length(), '}');
		s = result.toString();

		int firstCharOr = s.indexOf("|");
		int firstCharAnd = s.indexOf("&");
		int firstCharType = (firstCharOr > firstCharAnd) ? Common.EXPAND_OR
				: Common.EXPAND_AND;
		switch (firstCharType) {
		case Common.EXPAND_OR:
			s = s.replaceAll("[|] ", ",");
			break;
		case Common.EXPAND_AND:
			s = s.replaceAll("[&] ", ",");
			break;
		}
		return s;
	}

	public static String Step11(String s) {
		// For each line:
		// replace already used variables by new ones
		Set<String> variablesInLine;
		String split[] = s.split("\\r?\\n");
		String result = "";
		String replace;
		for (String line : split) {
			variablesInLine = getAllVariables(line);
			for (String x : variablesInLine) {
				if (Character.isUpperCase(x.charAt(0)))
					continue;
				if (line.matches(".*\\b" + x + "[\\,\\}\\)].*")) {
					replace = Common.createNewUnusedVariable().toString();
					line = line.replaceAll("\\b" + x + "\\b", replace);
				}
			}
			result += line += "\n";
		}

		return result;
	}

	public static int getLastIndexOf(String sentence, String expression, int to) {
		return sentence.substring(0, to).lastIndexOf(expression);
	}

	public static int getNextClosingBracket(String line, int from) {
		String temp = line.substring(from);
		Stack<Integer> stack = new Stack<>();
		for (int i = 0; i < temp.length(); i++) {

			if (temp.charAt(i) == '(')
				stack.push(i);
			if (stack.isEmpty())
				return i + from - 1;
			else if (temp.charAt(i) == ')')
				stack.pop();
		}
		return -1;
	}

	//
	/**
	 * get all variables *(x), *(x, , *,x) , *{x, , *,x} , *{x} from a line
	 * example: for input: {x, p(x), f(f(v)}, returned variables: x, v ;Note:
	 * set can contains constants .: constants are handled later
	 * 
	 * @param input
	 *            sentence containing predicates | variables
	 * @return set of variables(no duplicates)
	 */
	public static Set<String> getAllVariables(String input) {
		Set<String> set = new HashSet<>();
		String[] split;

		Pattern p = Pattern.compile("(\\()(\\w+)(\\))");
		Matcher m = p.matcher(input);
		StringBuffer result = new StringBuffer();
		while (m.find()) {
			m.appendReplacement(result, m.group(1) + "*");
			set.add(m.group(2));
		}
		m.appendTail(result);
		input = result.toString();

		p = Pattern.compile("(\\b)(\\w+\\,\\w+)+");
		m = p.matcher(input);
		result = new StringBuffer();
		while (m.find()) {
			m.appendReplacement(result, m.group(1) + "*");
			split = m.group(2).split("\\,");
			for (String splitted : split)
				set.add(splitted);
		}
		m.appendTail(result);
		input = result.toString();

		p = Pattern.compile("(\\b)(\\()(.*)(\\,)(\\w+)+(\\))");
		m = p.matcher(input);
		result = new StringBuffer();
		while (m.find()) {
			m.appendReplacement(result, m.group(1) + "*");
			set.add(m.group(5));
		}
		m.appendTail(result);
		input = result.toString();

		p = Pattern.compile("([\\{\\,])(\\w+)(\\b)([^\\(])");
		m = p.matcher(input);
		result = new StringBuffer();
		while (m.find()) {
			m.appendReplacement(result, m.group(1) + "*");
			set.add(m.group(2));
		}
		m.appendTail(result);
		input = result.toString();

		return set;
		// System.out.println("Set of Variables in input:");
		// for (String s : set)
		// System.out.println(s);
	}

}
