//  vFuncVar -- for lazy instantiation of function classes
//
//  Every built-in function is implemented by a different subclass of
//  vProc (as are some "functional" keywords).  There is some cost
//  associated with locating and loading classes, so it makes sense
//  to defer this until and unless a class is actually needed.
//
//  A vFuncVar instance is a vSimpleVar for which the first
//  dereference causes the loading of the associated vProc class --
//  provided that no other value has already been assigned.



package rts;

class vFuncVar extends vSimpleVar {

    vString img;		// image
    String classname;		// class name
    int args;			// argument count



vFuncVar(String name, String img, String classname, int args) {	// constructor
    super(name, null);
    this.img = vString.New(img);
    this.classname = classname;
    this.args = args;
}

public vValue Deref() {						// Deref
    if (value != null) {
	return value;
    } else {
	vProc p = (vProc) iEnv.instantiate(classname);
	p.img = this.img;
	p.args = this.args;
	value = p;
	return p;
    }
}



} // class vFuncVar