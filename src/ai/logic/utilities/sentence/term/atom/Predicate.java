package ai.logic.utilities.sentence.term.atom;

import java.util.ArrayList;

import ai.logic.utilities.Common;
import ai.logic.utilities.sentence.term.Atom;

public class Predicate extends Atom {

	public String predicateName;
	public ArrayList<Atom> value;

	// negate: dont call negate on values, only change type!

	public Predicate(String predicateName, ArrayList<Atom> value,
			boolean isNegative) {

		this.predicateName = predicateName;
		this.value = value;
		this.isNegative = isNegative;

	}
	

	public Predicate(Predicate p, ArrayList<Variable> domain) {
		this.predicateName = p.predicateName;
		this.value = p.value;
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
		x=x.substring(0, x.length() - 1);
		if (isNegative)
			return Common.NOT + predicateName + "(" + x + ")";
		else
			return predicateName + "(" + x + ")";
	}

	public String toStringWithSpace() {
		String x = " ";
		for (Atom v : value)
			x += v.toString() + " , ";
		x=x.substring(0, x.length() - 2);
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
		int indexOfX;
		while ((indexOfX = value.indexOf(x)) != -1) {
			value.set(indexOfX, y);
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

}
