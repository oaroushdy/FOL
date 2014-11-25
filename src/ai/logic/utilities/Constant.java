package ai.logic.utilities;

public class Constant extends Atom {
	public String value;
	public boolean isNegative;

	public Constant(String value, boolean isNegative) {
		this.value = value;
		this.isNegative = isNegative;
	}
}
