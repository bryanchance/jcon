package rts;

public abstract class iBinaryRefClosure extends iBinaryClosure {

	// Class for simple binary operations that return at most one value
	// but do not want their arguments dereferenced

	public void nextval() {
		if (PC == 1) {
			retvalue = function(argument0, argument1);
			returned = true;
			PC = 0;
		} else {
			retvalue = null;
		}
	}
}
