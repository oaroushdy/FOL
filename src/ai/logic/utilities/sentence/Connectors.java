package ai.logic.utilities.sentence;

import ai.logic.utilities.CF;
import ai.logic.utilities.Sentence;

public class Connectors extends Sentence implements CF {

	final static int AND = 1;

	final static int OR = 2;

	final static int IF = 3;

	final static int IFF = 4;

	public int type;

	public Connectors(int type) {
		this.type = type;
	}


}
