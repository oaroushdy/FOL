package ai.test;

import java.util.ArrayList;

import ai.logic.main.Unifier;
import ai.logic.utilities.sentence.term.Atom;
import ai.logic.utilities.sentence.term.atom.Constant;
import ai.logic.utilities.sentence.term.atom.Predicate;
import ai.logic.utilities.sentence.term.atom.Variable;

public class Main {

	public static void main(String[] args) {
		// Older(Father(y),y),Older(Father(x),John)

		Variable x = new Variable("x");
		Variable y = new Variable("y");
		Constant john = new Constant("john");

		Predicate f1 = new Predicate("father", y);
		Predicate o1 = new Predicate("Older", new Atom[] { f1, y });

		Predicate f2 = new Predicate("father", new Atom[] { x });
		Predicate o2 = new Predicate("Older", new Atom[] { f2, john });

		System.out.println(o1);
		System.out.println(o2);

		System.out.println(Unifier.unify(o1, o2));
		
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
