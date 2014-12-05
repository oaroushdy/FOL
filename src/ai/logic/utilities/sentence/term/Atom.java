package ai.logic.utilities.sentence.term;

import ai.logic.utilities.sentence.Term;
import ai.test.CNMain;

public abstract class Atom extends Term {
	// .equals() TODO using HashKey/tostring
	@Override
	public void negate() {
		isNegative = !isNegative;
	}

	public boolean equals(Atom a) {
		return this.equals(a);
	}

	public static Atom clone(Atom c) {
		return CNMain.clone.deepClone(c);
	}

}
