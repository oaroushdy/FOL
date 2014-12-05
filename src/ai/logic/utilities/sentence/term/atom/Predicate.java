package ai.logic.utilities.sentence.term.atom;

import java.util.ArrayList;
import java.util.Arrays;

import ai.logic.utilities.Common;
import ai.logic.utilities.sentence.term.Atom;
import ai.test.CNMain;

public class Predicate extends Atom {

	public String predicateName;
	public ArrayList<Atom> value;

	// negate: dont call negate on values, only change type!

	public Predicate(String predicateName, ArrayList<Atom> value,
			boolean isNegative) {

		this.predicateName = predicateName;
		this.value = CNMain.clone.deepClone(value);
		this.isNegative = isNegative;

	}

	public Predicate(String predicateName, ArrayList<Atom> value) {

		this.predicateName = predicateName;
		this.value = CNMain.clone.deepClone(value);
		this.isNegative = false;

	}

	public Predicate(String predicateName, Atom[] value) {

		this.predicateName = predicateName;
		this.value = CNMain.clone.deepClone((new ArrayList<Atom>(Arrays
				.asList(value))));
		this.isNegative = false;

	}

	public Predicate(String predicateName, Atom value, boolean isNegative) {
		this.value = new ArrayList<>();
		this.predicateName = predicateName;
		this.value.add(clone(value));
		this.isNegative = isNegative;

	}

	public Predicate(String predicateName, Atom value) {
		this.value = new ArrayList<>();
		this.predicateName = predicateName;
		this.value.add(clone(value));
		this.isNegative = false;

	}

	@SuppressWarnings("unchecked")
	public Predicate(Predicate p, ArrayList<Variable> domain) {
		this.predicateName = p.predicateName;
		this.value = (ArrayList<Atom>) clone(p.value);
		this.isNegative = p.isNegative;

		for (Atom x : p.value)
			value.add(x);

		// append domain to predicate, used in skolomizing step 5
		for (Variable x : domain)
			value.add((Atom) x);
	}

	public int getValuesLength() {
		return value.size();
	}

	@Override
	public String toString() {
		String x = "";
		for (Atom v : value)
			x += v.toString() + ",";
		x = x.substring(0, x.length() - 1);
		if (isNegative)
			return Common.NOT + predicateName + "(" + x + ")";
		else
			return predicateName + "(" + x + ")";
	}

	public String toStringWithSpace() {
		String x = " ";
		for (Atom v : value)
			x += v.toString() + " , ";
		x = x.substring(0, x.length() - 2);
		if (isNegative)
			return Common.NOT + predicateName + "(" + x + ")";
		else
			return predicateName + "(" + x + ")";
	}

	@Override
	public void Step7Expand() {
		// TODO Auto-generated method stub

	}

	@Override
	public void substituteBy(Variable x, Variable y) {
		for (Atom a : value)
			a.substituteBy(x, y);
	}

	@Override
	public void substituteBy(Variable x, Predicate y) {
		Atom a;
		for (int i = 0; i < value.size(); i++) {
			a = value.get(i);
			if (a instanceof Variable){
				if (((Variable) a).equals(x))
					value.set(i, clone(y));
			}
			else if(a instanceof Predicate)
				a.substituteBy(x, y);
				
		}
	}

	public boolean equals(Predicate a) {
		return this.toString().equals(a.toString());
	}

	public boolean equals(Atom a) {
		if (a.getClass().equals(this.getClass()))
			return this.toString().equals(a.toString());
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
