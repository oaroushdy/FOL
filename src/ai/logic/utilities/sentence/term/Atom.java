package ai.logic.utilities.sentence.term;

import ai.logic.utilities.sentence.Term;

public abstract class Atom extends Term {
	// .equals() TODO using HashKey/tostring
	@Override
	public void negate() {
		isNegative = !isNegative;
	}

}
