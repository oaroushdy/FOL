package ai.test;

import java.util.ArrayList;

import ai.logic.main.Unifier;
import ai.logic.utilities.sentence.term.Atom;
import ai.logic.utilities.sentence.term.atom.Constant;
import ai.logic.utilities.sentence.term.atom.Predicate;
import ai.logic.utilities.sentence.term.atom.Variable;

public class UnifierMain {

	public static boolean visualize = false;

	public static void main(String[] args) {

		// // ==================================
		// // Older(Father(y),y),Older(Father(x),John) = [JOHN/x, JOHN/y]
		// // ==================================
		//
		// Variable x = new Variable("x"); Variable y = new Variable("y");
		// Constant john = new Constant("john");
		//
		// Predicate f1 = new Predicate("father", y); Predicate o1 = new
		// Predicate("Older", new Atom[] { f1, y });
		//
		// Predicate f2 = new Predicate("father", new Atom[] { x }); Predicate
		// o2 = new Predicate("Older", new Atom[] { f2, john });
		//
		// System.out.println(o1); System.out.println(o2);
		//
		// System.out.println(Unifier.unify(o1, o2));

		// // ==================================
		// // Knows(Father(y),y),Knows(x,x) = No solution
		// // ==================================
		// Variable x = new Variable("x");
		// Variable y = new Variable("y");
		// Predicate f1 = new Predicate("father", y);
		// Predicate k1 = new Predicate("Knows", new Atom[] { f1, y });
		//
		// Predicate k2 = new Predicate("Knows", new Atom[] { x, x });
		// System.out.println(k1);
		// System.out.println(k2);
		// System.out.println(Unifier.unify(k1, k2));

		// // ==================================
		// // Q(y,G(A,B)),Q(G(x,x),y) = No Solution
		// // ==================================
		// Variable x = new Variable("x");
		// Variable y = new Variable("y");
		//
		// Constant a = new Constant("a");
		// Constant b = new Constant("b");
		//
		// Predicate gab = new Predicate("G", new Atom[] { a, b });
		// Predicate gxx = new Predicate("G", new Atom[] { x, x });
		//
		// Predicate q1 = new Predicate("Q", new Atom[] { y, gab });
		// Predicate q2 = new Predicate("Q", new Atom[] { gxx, y });
		// System.out.println(q1);
		// System.out.println(q2);
		// System.out.println(Unifier.unify(q1, q2));

		// // ==================================
		// // P(x,g(x),g(f(a)))andP(f(u),v,v) = [f(u)/x, g(x)/v, a/u]
		// // ==================================
		// Variable x = new Variable("x");
		// Variable a = new Variable("a");
		//
		// Variable u = new Variable("u");
		// Variable v = new Variable("v");
		//
		// Predicate gx = new Predicate("g", x);
		// Predicate fa = new Predicate("f", a);
		// Predicate gfa = new Predicate("g", fa);
		// Predicate p1 = new Predicate("P", new Atom[] { x, gx, gfa });
		//
		// Predicate fu = new Predicate("f", u);
		// Predicate p2 = new Predicate("P", new Atom[] { fu, v, v });
		// System.out.println(p1);
		// System.out.println(p2);
		// System.out.println(Unifier.unify(p1, p2));

		// // ==================================
		// // P(a,y,f(y)) and P(z,z,u) = No solution
		// // ==================================
		// Variable y = new Variable("y");
		// Variable a = new Variable("a");
		// Variable z = new Variable("z");
		// Variable u = new Variable("u");
		//
		// Predicate fy = new Predicate("f", y);
		// Predicate p1 = new Predicate("P", new Atom[] { a, y, fy });
		// Predicate p2 = new Predicate("P", new Atom[] { z, z, u });
		//
		// System.out.println(p1);
		// System.out.println(p2);
		// System.out.println(Unifier.unify(p1, p2));

		// // ==================================
		// // p(x,g(x),x) and p(g(u),g(g(z)),z) = No solution
		// // ==================================
		// Variable x = new Variable("x");
		// Variable u = new Variable("u");
		// Variable z = new Variable("z");
		//
		// Predicate gx = new Predicate("g", x);
		// Predicate gu = new Predicate("g", u);
		// Predicate gz = new Predicate("g", z);
		// Predicate ggz = new Predicate("g", gz);
		//
		// Predicate p1 = new Predicate("P", new Atom[] { x,gx,x });
		// Predicate p2 = new Predicate("P", new Atom[] { gu,ggz,z });
		//
		// System.out.println(p1);
		// System.out.println(p2);
		// System.out.println(Unifier.unify(p1, p2));

		// ==================================
		// p(A,x,h(g(z))) and p(z,h(y),h(y)) = [A/z, h(y)/x, g(z)/y]
		// ==================================
		Constant a = new Constant("a");
		Variable x = new Variable("x");
		Variable z = new Variable("Z");
		Variable y = new Variable("Y");

		Predicate gz = new Predicate("g", z);
		Predicate hgz = new Predicate("h", gz);
		Predicate hy = new Predicate("h", y);

		Predicate p1 = new Predicate("P", new Atom[] { a, x, hgz });
		Predicate p2 = new Predicate("P", new Atom[] { z, hy, hy });

		System.out.println("Initially:");
		System.out.println(p1);
		System.out.println(p2 + "\n");
		System.out.println("Solution:");
		System.out.println("Final subset(MGU): "
				+ Unifier.unify(p1, p2, visualize));

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
