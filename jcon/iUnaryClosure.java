package rts;

public abstract class iUnaryClosure extends iClosure {

	vDescriptor argument;

	abstract vDescriptor function(vDescriptor arg);

	public vDescriptor call(vDescriptor arg, iClosure parent) {
	    vDescriptor ret;
	    
	    closure(arg, parent);
	    try {
		try {
		    ret = nextval();
		} catch (OutOfMemoryError e) {
		    iRuntime.error(307);	// #%#%# really out of memory.
		    ret = null;
		}
	    } catch (iError e) {
		//  e.printStackTrace();  //#%#%#% TEMP: enable for debugging
		//#%#%# check &error here and fail or:
		e.report(this);  // returns only on error->failure conversion.
		ret = null;
	    }
	    return ret;
	}

	public void closure(vDescriptor[] args, iClosure parent) {
		init();
		argument = args[0];
		this.parent = parent;
	}
	public void closure(vDescriptor arg0, iClosure parent) {
		init();
		argument = arg0;
		this.parent = parent;
	}

	String getarg(int n) {
		switch (n) {
		default: return "???";
		case 0 : return argument.report();
		}
	}
}
