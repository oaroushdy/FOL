package ai.logic.main;

import java.util.ArrayList;

import ai.logic.utilities.Pair;
import ai.logic.utilities.sentence.term.Atom;
import ai.logic.utilities.sentence.term.atom.Constant;
import ai.logic.utilities.sentence.term.atom.Predicate;
import ai.logic.utilities.sentence.term.atom.Variable;

public class Unifier {

	public static ArrayList<Pair> unify(Atom E1, Atom E2) {
		return unify(E1, E2, new ArrayList<Pair>());
	}

	public static ArrayList<Pair> unify(Atom E1, Atom E2, ArrayList<Pair> subSet) {
		if (E1 == null | E2 == null)
			return subSet;
		if (subSet == null)
			return null;
		if (E1.equals(E2))
			return subSet;
		if (E1 instanceof Variable)
			return unifyVar((Variable) E1, E2, subSet);
		if (E2 instanceof Variable)
			return unifyVar((Variable) E2, E1, subSet);
		if (E1 instanceof Constant || E2 instanceof Constant)
			return null;
		if (((Predicate) E1).getValuesLength() != ((Predicate) E2)
				.getValuesLength())
			return null;

		Atom firstE1 = first(E1);
		Atom firstE2 = first(E2);

		ArrayList<Atom> restE1 = rest(E1);
		ArrayList<Atom> restE2 = rest(E2);
		if (restE1.isEmpty())
			return unify(firstE1, firstE2, subSet);

		Atom tempE1 = new Predicate(((Predicate) E1).predicateName, restE1,
				E1.isNegative);
		Atom tempE2 = new Predicate(((Predicate) E2).predicateName, restE2,
				E2.isNegative);

		return unify(tempE1, tempE2, unify(firstE1, firstE2, subSet));
	}

	// TODO swap val with E1 in getAtomFromSubSet()
	public static ArrayList<Pair> unifyVar(Variable E1, Atom E2,
			ArrayList<Pair> subSet) {
		Atom val;
		if ((val = getAtomFromSubSet(E1, subSet)) != null)
			return unify(val, E2, subSet);
		if ((val = getAtomFromSubSet(E2, subSet)) != null)
			return unify(E1, val, subSet);
		if (occurs(E1, E2))
			return null;
		subSet.add(new Pair(E1, E2));
		return subSet;

	}
	
	public static Atom getAtomFromSubSet(Atom E1, ArrayList<Pair> subSet) {
		for (Pair x : subSet) {
			if (x.substituteBy.equals(E1))
				return x.variable;
		}
		return null;
	}


	public static boolean occurs(Variable E1, Atom E2) {
		if (E2 instanceof Variable) // x, x
			return E1.equals(E2);
		if (E2 instanceof Predicate)// x, f(x)
			if (((Predicate) E2).toStringWithSpace().contains(
					" " + E1.toString() + " "))
				return false;
		return true;

	}

	public static Atom first(Atom a) {
		Predicate p;
		if (!(a instanceof Predicate))
			return null;
		p = (Predicate) a;
		if (!p.value.isEmpty())
			return p.value.get(0);
		else
			return null;
	}

	public static Atom first(ArrayList<Atom> list) {
		if (!list.isEmpty())
			return list.get(0);
		else
			return null;
	}

	public static ArrayList<Atom> rest(Atom a) {
		Predicate p;
		if (!(a instanceof Predicate))
			return null;
		p = (Predicate) a;
		if (p.value.isEmpty())
			return null;
		ArrayList<Atom> temp = new ArrayList<>();
		for (int i = 1; i < p.value.size(); i++) {
			temp.add(p.value.get(i));
		}
		return temp;
	}

	public static ArrayList<Atom> rest(ArrayList<Atom> list) {
		if (list.isEmpty())
			return null;
		ArrayList<Atom> temp = new ArrayList<>();
		for (int i = 1; i < list.size(); i++) {
			temp.add(list.get(i));
		}
		return temp;
	}

}
