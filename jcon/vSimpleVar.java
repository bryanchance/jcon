class vSimpleVar extends vVariable {

    vValue value;	// value of variable
    
    vSimpleVar(vValue x)	{ value = x; }		// constructor
    vSimpleVar()		{ value = iNew.Null(); }

    vValue deref()		{ return value; }

    vVariable Assign(vValue x)	{ value = x; return this; }

    String report()	{ return "(variable = " + this.value.report() + ")"; }



    vDescriptor isNull()	{
    	if (value instanceof vNull) {
	    return this;	// return variable 
	} else {
	    return null;	// fail
	}
    }

    vDescriptor isntNull()	{ 
    	if (value instanceof vNull) {
	    return null;	// fail
	} else {
	    return this;	// return variable 
	}
    }

    vDescriptor Select()	  { return value.deref().SelectVar(this); }
    vDescriptor Bang(iClosure c)  { return value.deref().BangVar(c, this); }

    vDescriptor Index(vValue i)
    				{ return value.deref().IndexVar(this, i); }
    vDescriptor Section(vValue i, vValue j)
    				{ return value.deref().SectionVar(this, i, j); }
}
