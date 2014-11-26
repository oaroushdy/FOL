package ai.test;

import java.util.ArrayList;

import ai.logic.utilities.sentence.term.Atom;
import ai.logic.utilities.sentence.term.atom.Constant;
import ai.logic.utilities.sentence.term.atom.Predicate;
import ai.logic.utilities.sentence.term.atom.Variable;

public class Main {

	public static void main(String[] args) {
		ArrayList<Atom> value = new ArrayList<>();
		ArrayList<Atom> valuef = new ArrayList<>();
		ArrayList<Atom> valueg = new ArrayList<>();

		value.add(new Constant("A", false));
		value.add(new Variable("x", false));
		value.add(new Variable("y", false));

		Atom a = new Predicate("p", value, false);

		Atom f = new Predicate("f", valuef, false);
		Atom fg = new Predicate("g", valueg, false);

		valuef.add(fg);
		valueg.add(new Variable("x", false));
		
		value.add(f);
		value.add(fg);

		System.out.println(a);

	}

	public static Atom first(ArrayList<Atom> list) {
		return list.get(0);
	}

	public static ArrayList<Atom> rest(ArrayList<Atom> list) {
		ArrayList<Atom> temp = new ArrayList<>();
		for (int i = 1; i < list.size(); i++) {
			temp.add(list.get(i)); // doesnt copy element
		}
		return temp;
	}
}
