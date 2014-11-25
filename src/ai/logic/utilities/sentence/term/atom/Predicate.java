package ai.logic.utilities.sentence.term.atom;

import java.util.ArrayList;

import ai.logic.utilities.sentence.term.Atom;

public class Predicate extends Atom {

	public String predicateName;
	public ArrayList<Atom> value;

	public Predicate(String predicateName, ArrayList<Atom> value) {

		this.predicateName = predicateName;
		this.value = value;

	}

}
