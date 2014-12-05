package ai.logic.utilities.sentence.term.atom;

import ai.logic.utilities.Common;
import ai.logic.utilities.sentence.term.Atom;

public class Constant extends Atom {
	public String value;

	public Constant(String value, boolean isNegative) {
		this.value = value.toUpperCase();
		this.isNegative = isNegative;
	}

	public Constant(String value) {
		this.value = value.toUpperCase();
		this.isNegative = false;
	}

	@Override
	public String toString() {
		if (isNegative)
			return Common.NOT + value;
		else
			return value;
	}

	@Override
	public void Step7Expand() {

	}

	@Override
	public void substituteBy(Variable x, Variable y) {

	}

	public boolean equals(Constant a) {
		return this.value.equals(a.value);
	}

	public boolean equals(Atom a) {
		if (a.getClass().equals(this.getClass()))
			return this.value.equals(((Constant) a).value);
		return false;
	}

	@Override
	public void Step1EliminateIFF() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void Step2RemoveIF() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void Step3PushNegative() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void Step4Standardize() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void Step5Skolomize() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void Step6EliminateAQuantifier() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void Step8Extract() {
		// TODO Auto-generated method stub
		
	}
}
