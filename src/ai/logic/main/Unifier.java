package ai.logic.main;

import java.util.ArrayList;

import ai.logic.utilities.Pair;
import ai.logic.utilities.sentence.term.Atom;
import ai.logic.utilities.sentence.term.atom.Constant;
import ai.logic.utilities.sentence.term.atom.Predicate;
import ai.logic.utilities.sentence.term.atom.Variable;

public class Unifier {

	public static boolean visualize;

	public static ArrayList<Pair> unify(Atom E1, Atom E2, boolean visualize) {
		Unifier.visualize = visualize;
		return unify(E1, E2, new ArrayList<Pair>());
	}

	public static ArrayList<Pair> unify(Atom E1, Atom E2, ArrayList<Pair> subSet) {
		if (visualize) {
			System.out.println("Unifying:");
			System.out.println("E1: " + E1);
			System.out.println("E2: " + E2);
			System.out.println("current subSet: " + subSet + "\n");
		}
		if (subSet == null) {
			fail();
			return null;
		}
		if (E1 == null | E2 == null)
			return subSet;

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

		// Atom tempE1 = new Predicate(((Predicate) E1).predicateName, restE1,
		// E1.isNegative);
		// Atom tempE2 = new Predicate(((Predicate) E2).predicateName, restE2,
		// E2.isNegative);

		return unify(restE1, restE2, unify(firstE1, firstE2, subSet));
	}

	public static ArrayList<Pair> unify(ArrayList<Atom> E1, ArrayList<Atom> E2,
			ArrayList<Pair> subSet) {

		if (visualize) {
			System.out.println("Unifying:");
			System.out.println("E1: " + E1);
			System.out.println("E2: " + E2);
			System.out.println("Current subSet: " + subSet + "\n");
		}
		if (subSet == null) {
			fail();
			return null;
		}
		if (E1.isEmpty() | E2.isEmpty())
			return subSet;

		if (E1.equals(E2))
			return subSet;

		Atom firstE1 = first(E1);
		Atom firstE2 = first(E2);

		ArrayList<Atom> restE1 = rest(E1);
		ArrayList<Atom> restE2 = rest(E2);
		if (restE1.isEmpty())
			return unify(firstE1, firstE2, subSet);

		return unify(restE1, restE2, unify(firstE1, firstE2, subSet));
	}

	// TODO swap val with E1 in getAtomFromSubSet()
	public static ArrayList<Pair> unifyVar(Variable E1, Atom E2,
			ArrayList<Pair> subSet) {
		Atom val;
		if ((val = getAtomFromSubSet(E1, subSet)) != null)
			return unify(val, E2, subSet);

		if ((val = getAtomFromSubSet(E2, subSet)) != null)
			return unify(E1, val, subSet);

		if (occurs(E1, E2)) {
			if (visualize)
				System.err.println(E1 + " occurs " + E2);
			fail();
			return null;
		}
		Pair add = new Pair(E1, E2);
		if (!evaluate(add, subSet)) {
			fail();
			return null;
		}
		// TODO add if they don't exist, dont repeat
		if (!exist(add, subSet))
			subSet.add(add);
		return subSet;

	}

	public static boolean exist(Pair pair, ArrayList<Pair> subSet) {
		for (Pair x : subSet) {
			if (x.variable.equals(pair.variable)) {
				// System.out.println("subset will not add:");
				// System.out.println("add: " + pair);
				// System.out.println("to subset: " + subSet);
				return true;
			}
		}
		return false;
	}

	public static boolean evaluate(Pair pair, ArrayList<Pair> subSet) {
		// check pair.variable SubstituteBy/Variable
		// Ex: [x/y, JOHN/y] should be [JOHN/x, JOHN/y]
		Pair temp = getPairFromSubSet(pair.variable, subSet);

		// System.out.println("Evaluate");
		// System.out.println("Pair: " + pair);
		// System.out.println("temp: " + temp);
		// System.out.println("subSet: " + subSet);

		if (temp != null) {

			if (pair.substituteBy instanceof Variable
					&& temp.substituteBy instanceof Variable)
				return false;
			if (pair.substituteBy instanceof Constant
					&& temp.substituteBy instanceof Constant)
				return false;
			if (pair.substituteBy instanceof Constant) {
				if (temp.variable instanceof Constant)
					return false;
				temp.variable = temp.substituteBy;
				temp.substituteBy = pair.substituteBy;
				// TODO make pair = null
				// pair = null;
				return true;
			}
			if (temp.substituteBy instanceof Constant) {
				if (pair.variable instanceof Constant)
					return false;
				pair.variable = pair.substituteBy;
				pair.substituteBy = temp.substituteBy;
				// TODO remove temp from subset
				// subSet.remove(getPairFromSubSet(pair.variable, subSet));
				return true;
			}
			if (temp.substituteBy instanceof Predicate
					&& pair.substituteBy instanceof Predicate) {
				unify(temp.substituteBy, pair.substituteBy, subSet);
				return true;
			}
		}

		temp = getPairSubBySubSetVariable(pair, subSet);
		if (temp == null)
			return true;
		if (occurs(pair.substituteBy, temp.variable))
			return false;
		// temp= compare( pair.substituteBy with subSet.variable)
		// if occur(temp.substitueBy, pair.variable)
		// return null
		return true;
	}

	public static Pair getPairSubBySubSetVariable(Pair pair,
			ArrayList<Pair> subSet) {
		for (Pair x : subSet) {
			if (x.variable.equals(pair.substituteBy))
				return x;
		}
		return null;
	}

	public static int getIndexOfPairFromSubSet(Atom E1, ArrayList<Pair> subSet) {
		Pair x;
		for (int i = 0; i < subSet.size(); i++) {
			x = subSet.get(i);
			if (x.variable.equals(E1))
				return i;
		}

		return -1;
	}

	public static Pair getPairFromSubSet(Atom E1, ArrayList<Pair> subSet) {
		for (Pair x : subSet) {
			if (x.variable.equals(E1))
				return x;
		}
		return null;
	}

	public static Atom getAtomFromSubSet(Atom E1, ArrayList<Pair> subSet) {
		for (Pair x : subSet) {
			if (x.substituteBy.equals(E1))
				return x.variable;
		}
		return null;
	}

	public static Atom getAtomFromSubSetAgain(Atom E1, ArrayList<Pair> subSet) {
		for (Pair x : subSet) {
			if (x.variable.equals(E1))
				return x.substituteBy;
		}
		return null;
	}

	public static boolean occurs(Variable E1, Atom E2) {

		if (E2 instanceof Variable) // x, x
			return E1.equals(E2);
		if (E2 instanceof Predicate)// x, f(x)
			if (((Predicate) E2).toStringWithSpace().contains(
					" " + E1.toString() + " "))
				return true;
		return false;

	}

	public static boolean occurs(Atom E1, Atom E2) {
		if (E2 instanceof Variable) // x, x
			return E1.equals(E2);
		if (E1 instanceof Predicate && E2 instanceof Predicate)// x, f(x)
			return (((Predicate) E2).toStringWithSpace()
					.contains(((Predicate) E1).toStringWithSpace()))
					|| (((Predicate) E1).toStringWithSpace()
							.contains(((Predicate) E2).toStringWithSpace()));
		return false;

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
		ArrayList<Atom> temp = new ArrayList<>();
		if (p.value.isEmpty())
			return temp;
		for (int i = 1; i < p.value.size(); i++) {
			temp.add(p.value.get(i));
		}
		return temp;
	}

	public static ArrayList<Atom> rest(ArrayList<Atom> list) {
		ArrayList<Atom> temp = new ArrayList<>();
		if (list.isEmpty())
			return temp;
		for (int i = 1; i < list.size(); i++) {
			temp.add(list.get(i));
		}
		return temp;
	}

	public static void fail() {
		System.err.println("Cannot find MGU, fails");
		System.exit(0);
	}

}
