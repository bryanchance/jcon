//  vVariable -- vDescriptor subclass for Icon variables.
//
//  Variables represent assignable expressions in Icon.
//
//  The subclass vSimpleVar is used for Icon global and local variables 
//  and, when further subclassed, for certain keywords such as &subject.
//
//  The abstract subclass vVarExpr represents a variable expression such
//  as a substring, list or table index, etc.  Each VarExpr points to
//  an underlying SimpleVar.


abstract class vVariable extends vDescriptor {


    // must be implemented:

    abstract vValue deref();			// dereference
    abstract vVariable Assign(vValue x);	// assign
    abstract String report();			// report for traceback


    // for many operations on variables, default action is to deref and retry

    iClosure instantiate(vDescriptor[] args, iClosure parent)
			{ return this.deref().instantiate(args, parent); }

    vString mkString()			{ return this.deref().mkString(); }
    vInteger mkInteger()		{ return this.deref().mkInteger(); }
    vReal mkReal()			{ return this.deref().mkReal(); }
    vNumeric mkNumeric()		{ return this.deref().mkNumeric(); }

    String write()			{ return this.deref().write(); }
    String image()			{ return this.deref().image(); }

    vVariable field(String s)		{ return this.deref().field(s); }

    vNumeric Negate()			{ return this.deref().Negate(); }
    vInteger Size()			{ return this.deref().Size(); }

    vValue Add(vDescriptor v)		{ return this.deref().Add(v); }
    vValue Sub(vDescriptor v)		{ return this.deref().Sub(v); }
    vValue Mul(vDescriptor v)		{ return this.deref().Mul(v); }
    vValue Div(vDescriptor v)		{ return this.deref().Div(v); }
    vValue Mod(vDescriptor v)		{ return this.deref().Mod(v); }

    vValue NLess(vDescriptor v)		{ return this.deref().NLess(v); }
    vValue NLessEq(vDescriptor v)	{ return this.deref().NLessEq(v); }
    vValue NEqual(vDescriptor v)	{ return this.deref().NEqual(v); }
    vValue NUnequal(vDescriptor v)	{ return this.deref().NUnequal(v); }
    vValue NGreaterEq(vDescriptor v)	{ return this.deref().NGreaterEq(v); }
    vValue NGreater(vDescriptor v)	{ return this.deref().NGreater(v); }

    vValue Concat(vDescriptor v)	{ return this.deref().Concat(v); }
}
