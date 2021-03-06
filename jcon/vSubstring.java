package jcon;

public final class vSubstring extends vVariable {

    vVariable var;

    int start, end;	// substring bounds, in Icon terms



//  constructors assume valid, ordered indices in positive form

public static vSubstring New(vVariable v, int i1, int i2)
				{ return new vSubstring(v, i1, i2); }

private vSubstring(vVariable v, int i1, int i2) {	// construct from String
    var = v;
    start = i1;
    end = i2;
}

public boolean isnull()			{ return false; }

public vString Name() {
    String vname = var.Name().toString();
    return vString.New(vname +"[" + start + ":" + end + "]");
}



//  ss.strval() -- return underlying vString value.
//
//  Verifies that the underlying variable is still a string and
//  that the indices are still in range, and returns the vString.

vString strval() {
    return strval(var.Deref());
}
vString strval(vDescriptor v) {
    if (! (v instanceof vString)) {
	iRuntime.error(103, var);
    }
    vString s = (vString) v;
    if (end > s.length() + 1)  {
	iRuntime.error(205);
    }
    return s;
}



//  ss.posEq(n) -- return positive equivalent of position n in substring ss,
//		   or zero if out of bounds

int posEq(long n) {
    int len = end - start;
    if (n <= 0) {
	n += len + 1;
    }
    if (n > 0 && n <= len + 1) {
	return (int)(start + n - 1);
    } else {
	return 0;
    }
}



//  internal methods

public vDescriptor Return() {
    return this.DerefLocal();
}

public vDescriptor DerefLocal() {
    vDescriptor v = var.DerefLocal();
    if (v instanceof vValue) {
        return vString.New(this.strval(v), start, end);
    }
    var = (vVariable) v;
    return this;
}

public vValue Deref() {
    return vString.New(this.strval(), start, end);
}

public vString report() {
    if (start + 1 == end) {
	return vString.New(var.Deref().report() + "[" + start + "]");
    } else {
	return vString.New(var.Deref().report() + "[" + start +":"+ end + "]");
    }
}



//  operators

public vVariable Assign(vDescriptor x) {
    vString s = x.mkString();		// coerce assigned value
    var.Assign(vString.New(this.strval(), start, end, s));
    end = start + s.length();
    return this;
}

public vDescriptor Index(vDescriptor i) {			// s[i]
    this.strval();	// validate
    int m = this.posEq(i.mkInteger().value);
    if (m == 0) {
	return null; /*FAIL*/
    }
    return vSubstring.New(var, m, m+1);
}

public vDescriptor Section(vDescriptor i, vDescriptor j) {	// s[i:j]
    this.strval();	// validate
    int m = this.posEq(i.mkInteger().value);
    int n = this.posEq(j.mkInteger().value);
    if (m == 0 || n == 0) {
	return null; /*FAIL*/
    }
    if (m > n) {
	return vSubstring.New(var, n, m);
    } else {
	return vSubstring.New(var, m, n);
    }
}



public vDescriptor Select() {				// ?s
    if (start == end) {
	return null; /*FAIL*/
    }
    int offset = (int) iKeyword.random.choose(end - start) + 1;
    return vSubstring.New(this, offset, offset + 1);
}

public vDescriptor Bang() {				// !s
    return new vClosure() {
	int i = start;
	public vDescriptor Resume() {
	    if (i < end) {
		retval = vSubstring.New(var, i, i+1);
		i++;
		return this;	// suspend
	    } else {
		return null; /*FAIL*/
	    }
	}
    }.Resume();
}



} // class vSubstring
