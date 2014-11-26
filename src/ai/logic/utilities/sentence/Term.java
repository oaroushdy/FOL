package ai.logic.utilities.sentence;

import ai.logic.utilities.Sentence;
import ai.logic.utilities.sentence.term.atom.Variable;

public abstract class Term extends Sentence {
	public abstract String toString();

	@Override
	public abstract void Step7Expand();

	@Override
	public abstract void substituteBy(Variable x, Variable y);

	@Override
	public void Step5Skolomize() {
	}
}
