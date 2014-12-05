package ai.logic.utilities.sentence;

import java.util.ArrayList;

import ai.logic.utilities.CF;
import ai.logic.utilities.sentence.term.atom.Variable;

public class Connector implements CF {

	public final static int AND = 1;

	public final static int OR = 2;

	public final static int IF = 3;

	public final static int IFF = 4;

	public int type;

	public Connector(int type) {
		this.type = type;
	}

	@Override
	public void Step1EliminateIFF() {
	}

	@Override
	public void Step2RemoveIF() {
		// TODO Auto-generated method stub

	}

	@Override
	public void negate() {
		switch (type) {
		case AND:
			type = OR;
			break;
		case OR:
			type = AND;
			break;
		}
	}

	@Override
	public void Step4Standardize() {
		// TODO Auto-generated method stub

	}

	@Override
	public void Step5Skolomize(ArrayList<Variable> currentDomain) {
		// TODO Auto-generated method stub

	}

	@Override
	public void Step6EliminateAQuantifier() {
		// TODO Auto-generated method stub

	}

	@Override
	public void Step7Expand() {
		// TODO Auto-generated method stub

	}

	@Override
	public void Step8Extract() {
		// TODO Auto-generated method stub

	}

	@Override
	public String hashKey() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void Step3PushNegative() {
		// TODO Auto-generated method stub

	}

	@Override
	public String toString() {
		switch (type) {
		case AND:
			return "&";
		case OR:
			return "|";
		case IF:
			return "=>";
		case IFF:
			return "<=>";
		default:
			return "";
		}
	}

}
