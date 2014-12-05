package ai.logic.utilities;

import java.util.ArrayList;

import ai.logic.utilities.sentence.Connector;
import ai.logic.utilities.sentence.Term;
import ai.logic.utilities.sentence.term.Atom;
import ai.logic.utilities.sentence.term.Quantifier;
import ai.logic.utilities.sentence.term.atom.Predicate;
import ai.logic.utilities.sentence.term.atom.Variable;
import ai.test.CNMain;

/*
 * We assume that a sentence consists of a sentence with at least one 
 * connector
 * 
 * In case the sentence consists only of Ex[Sentence] or Ax[Sentence],
 * then the input is replaced by { Ex[Sentence] OR TRUE }
 */
public class Sentence  implements CF {

	public Sentence beforeConnector;
	public Sentence afterConnector;
	public Connector connector;

	public boolean isNegative;

	public Sentence(Sentence beforeConnector, Connector connector,
			Sentence afterConnector, boolean isNegative) {
		this.beforeConnector = clone(beforeConnector);
		this.connector = (Connector) clone(connector);
		this.afterConnector = clone(afterConnector);
		this.isNegative = isNegative;
	}

	public Sentence(Sentence beforeConnector, int connector,
			Sentence afterConnector, boolean isNegative) {
		this.beforeConnector = clone(beforeConnector);
		this.connector = new Connector(connector);
		this.afterConnector = clone(afterConnector);
		this.isNegative = isNegative;
	}

	public Sentence(Sentence beforeConnector, int connector,
			Sentence afterConnector) {
		this.beforeConnector = clone(beforeConnector);
		this.connector = new Connector(connector);
		this.afterConnector = clone(afterConnector);
		this.isNegative = false;
	}

	public Sentence() {
	}

	/**
	 * Copy constructor
	 * 
	 * @param sentence
	 *            to copy
	 */
	public Sentence(Sentence sentence) {
		if (sentence.getClass().equals(Sentence.class)) {
			this.beforeConnector = clone(beforeConnector);
			this.connector = (Connector) clone(sentence.connector);
			this.afterConnector = clone(sentence.afterConnector);
		} 
	}

	@Override
	public void Step1EliminateIFF() {
		Sentence tempLeft, tempRight;
		if (beforeConnector != null) {
			beforeConnector.Step1EliminateIFF();
		}
		if (afterConnector != null) {
			afterConnector.Step1EliminateIFF();
		}
		if (connector != null)
			if (connector.type == Connector.IFF) {
				tempLeft = clone(beforeConnector);
				tempRight = clone(afterConnector);
				beforeConnector = new Sentence(tempLeft, new Connector(
						Connector.IF), tempRight, false);
				afterConnector = new Sentence(tempRight, new Connector(
						Connector.IF), tempLeft, false);
				connector.type = Connector.AND;
			}
	}

	@Override
	public void Step2RemoveIF() {
		Sentence tempLeft, tempRight;
		tempLeft = clone(beforeConnector);
		tempRight = clone(afterConnector);

		if (beforeConnector != null)
			tempLeft.Step2RemoveIF();
		if (afterConnector != null)
			tempRight.Step2RemoveIF();

		if (connector != null)
			if (connector.type == Connector.IF) {
				tempLeft.negate(); // negate the left sentence
				connector.type = Connector.OR;
			}
		beforeConnector = tempLeft;
		afterConnector = tempRight;
	}

	@Override
	public void Step3PushNegative() {
		if (isNegative) {
			if (beforeConnector != null)
				beforeConnector.negate();
			if (afterConnector != null)
				afterConnector.negate();
			connector.negate();
			negate();
		}
		if (beforeConnector != null)
			beforeConnector.Step3PushNegative();
		if (afterConnector != null)
			afterConnector.Step3PushNegative();
	}

	@Override
	public void Step4Standardize() {
		if (beforeConnector != null)
			beforeConnector.Step4Standardize();
		if (afterConnector != null)
			afterConnector.Step4Standardize();
	}

	public void Step5Skolomize() {
		Step5Skolomize(new ArrayList<Variable>());
	}

	@SuppressWarnings("unchecked")
	@Override
	public void Step5Skolomize(ArrayList<Variable> currentDomain) {
		if (beforeConnector != null)
			beforeConnector
					.Step5Skolomize((ArrayList<Variable>) clone(currentDomain));
		if (afterConnector != null)
			afterConnector
					.Step5Skolomize((ArrayList<Variable>) clone(currentDomain));
	}

	@Override
	public void Step6EliminateAQuantifier() {
		if (beforeConnector != null)
			beforeConnector.Step6EliminateAQuantifier();
		if (afterConnector != null)
			afterConnector.Step6EliminateAQuantifier();
	}

	@Override
	public void Step7Expand() {
		if (beforeConnector instanceof Quantifier)
			beforeConnector = ((Quantifier) beforeConnector).quantifier;
		if (afterConnector instanceof Quantifier)

			afterConnector = ((Quantifier) afterConnector).quantifier;
		if (connector.type == Connector.AND) {
			if (isAtom(beforeConnector) // Atom OR Sentence
					&& isSentence(afterConnector)) {
				convertSentence((Atom) beforeConnector, afterConnector,
						Common.EXPAND_AND);

			} else if (isAtom(afterConnector) && isSentence(beforeConnector)) {
				convertSentence((Atom) afterConnector, beforeConnector,
						Common.EXPAND_AND);
			}
		} else if (connector.type == Connector.OR) {
			if (isAtom(beforeConnector) // Atom OR Sentence
					&& isSentence(afterConnector)) {
				convertSentence((Atom) beforeConnector, afterConnector,
						Common.EXPAND_OR);

			} else if (isAtom(afterConnector) && isSentence(beforeConnector)) {
				convertSentence((Atom) afterConnector, beforeConnector,
						Common.EXPAND_OR);
			}
		}
		if (beforeConnector != null)
			beforeConnector.Step7Expand();
		if (afterConnector != null)
			afterConnector.Step7Expand();
	}

	// private void convertSentence(Atom left, Sentence right, int expandType) {
	// Sentence tempLeft = right.beforeConnector;
	// Sentence tempRight = right.afterConnector;
	// switch (expandType) {
	// case Common.EXPAND_AND: // TODO should copy object left!
	// beforeConnector = new Sentence(left, new Connector(Connector.AND),
	// tempLeft, false); // isNegative = false since we already
	// // expanded the negative
	// afterConnector = new Sentence(left, new Connector(Connector.AND),
	// tempRight, false);
	// connector.type = Connector.OR;
	// break;
	// case Common.EXPAND_OR: // TODO should copy object left!
	// beforeConnector = new Sentence(left, new Connector(Connector.OR),
	// tempLeft, false); // isNegative = false since we already
	// // expanded the negative
	// afterConnector = new Sentence(left, new Connector(Connector.OR),
	// tempRight, false);
	// connector.type = Connector.AND;
	// break;
	// }
	// }

	private void convertSentence(Atom left, Sentence right, int expandType) {
		if (right == null)
			return;
		Sentence tempLeft = clone(right.beforeConnector);
		Sentence tempRight = clone(right.afterConnector);
		switch (expandType) {
		case Common.EXPAND_AND: // TODO should copy object left!
			if (isSentence(right.beforeConnector)) {
				convertSentence(left, right.beforeConnector, Common.EXPAND_AND);
			}
			if (isSentence(right.afterConnector)) {
				convertSentence(left, right.beforeConnector, Common.EXPAND_AND);
			}
			beforeConnector = new Sentence(left, new Connector(Connector.AND),
					tempLeft, false); // isNegative = false since we already
										// expanded the negative
			afterConnector = new Sentence(left, new Connector(Connector.AND),
					tempRight, false);
			connector.type = Connector.OR;
			break;
		case Common.EXPAND_OR: // TODO should copy object left!
			if (isSentence(right.beforeConnector)) {
				convertSentence(left, right.beforeConnector, Common.EXPAND_OR);
			}
			if (isSentence(right.afterConnector)) {
				convertSentence(left, right.beforeConnector, Common.EXPAND_OR);
			}
			beforeConnector = new Sentence(left, new Connector(Connector.OR),
					tempLeft, false); // isNegative = false since we already
										// expanded the negative
			afterConnector = new Sentence(left, new Connector(Connector.OR),
					tempRight, false);
			connector.type = Connector.AND;
			break;
		}
	}

	public static boolean isTerm(Sentence s) {
		return s instanceof Term;
	}

	public static boolean isAtom(Sentence s) {
		return (s instanceof Atom);
	}

	@Override
	public void Step8Extract() {
		// TODO Auto-generated method stub

	}

	@Override
	public String hashKey() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void negate() {
		isNegative = !isNegative;
	}

	public String toString() {
		String x = "";
		if (isNegative)
			x += Common.NOT;
		x += "(";
		x += beforeConnector;
		x += " " + connector + " ";
		x += afterConnector + ") ";
		return x.trim();
	}

	public void substituteBy(Variable x, Variable y) {
		beforeConnector.substituteBy(x, y);
		afterConnector.substituteBy(x, y);
	}

	public void substituteBy(Variable x, Predicate y) {
		if (beforeConnector != null)
			beforeConnector.substituteBy(x, y);
		if (afterConnector != null)
			afterConnector.substituteBy(x, y);
	}

	public static Sentence clone(Sentence c) {
		return CNMain.clone.deepClone(c);
	}

	public static Object clone(Object c) {
		return CNMain.clone.deepClone(c);
	}

	public static boolean isSentence(Sentence s) {
		return s.getClass().equals(Sentence.class);
	}

	public static boolean isQuantifier(Sentence s) {
		return s instanceof Quantifier;
	}

	public void standardize(Variable x) {
		if (beforeConnector != null)
			beforeConnector.standardize(x);
		if (afterConnector != null)
			afterConnector.standardize(x);
	}
}
