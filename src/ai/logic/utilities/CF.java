package ai.logic.utilities;

import java.util.ArrayList;

import ai.logic.utilities.sentence.term.atom.Variable;

public interface CF {

	public void Step1EliminateIFF();

	public void Step2RemoveIF();

	public void Step3PushNegative();

	public void Step4Standardize();

	public void Step5Skolomize(ArrayList<Variable> currentDomain);

	public void Step6EliminateAQuantifier();

	public void Step7Expand();

	public void Step8Extract();

	public void negate();

	public String hashKey();

}
