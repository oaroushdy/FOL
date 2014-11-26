package ai.logic.utilities;


public abstract class Expression implements CF {

	protected Expression exp;

	// Expression exp = new sentence(before,con, after);
	// OR
	// Expression exp = AQuantifier( new sentence(before,con, after), domain)

	@Override
	public void Step4Standardize() {
		exp.Step4Standardize();
	}

	@Override
	public void Step5Skolomize() {
		// TODO Auto-generated method stub
		exp.Step5Skolomize();

	}
}
