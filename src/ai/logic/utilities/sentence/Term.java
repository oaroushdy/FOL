package ai.logic.utilities.sentence;

import ai.logic.utilities.Sentence;
import ai.logic.utilities.sentence.term.atom.Variable;

public abstract class Term extends Sentence {
	public abstract String toString();

	@Override
	public abstract void Step1EliminateIFF();

	@Override
	public abstract void Step2RemoveIF();

	@Override
	public abstract void Step3PushNegative();

	@Override
	public abstract void Step4Standardize();


	@Override
	public abstract void Step6EliminateAQuantifier();

	@Override
	public abstract void Step7Expand();

	@Override
	public abstract void Step8Extract();

	@Override
	public abstract void substituteBy(Variable x, Variable y);

}
