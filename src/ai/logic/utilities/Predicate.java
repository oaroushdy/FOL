package ai.logic.utilities;

import java.util.ArrayList;

public class Predicate extends Atom {

	public String predicateName;
	public ArrayList<Atom> value;

	public Predicate(String predicateName, ArrayList<Atom> value) {

		this.predicateName = predicateName;
		this.value = value;

	}

}
