//  iOperators.java -- string invocation of Icon operators

package jcon;



public final class iOperators {

static void announce() {
    iEnv.declareOpr(".",   1, "oDeref");
    iEnv.declareOpr(":=",  2, "oAssign");
    iEnv.declareOpr("<-",  2, "oRevAssign");
    iEnv.declareOpr(":=:", 2, "oSwap");
    iEnv.declareOpr("<->", 2, "oRevSwap");
    iEnv.declareOpr("&",   2, "oConjunction");
    iEnv.declareOpr("!",   2, "oProcessArgs");
    iEnv.declareOpr("@",   2, "oActivate");
    iEnv.declareOpr("...", 3, "oToBy");
    iEnv.declareOpr("+",   1, "oNumerate");
    iEnv.declareOpr("-",   1, "oNegate");
    iEnv.declareOpr("*",   1, "oSize");
    iEnv.declareOpr("~",   1, "oComplement");
    iEnv.declareOpr("^",   1, "oRefresh");
    iEnv.declareOpr("=",   1, "oTabMatch");
    iEnv.declareOpr("/",   1, "oIsNull");
    iEnv.declareOpr("\\",  1, "oIsntNull");
    iEnv.declareOpr("?",   1, "oSelect");
    iEnv.declareOpr("!",   1, "oBang");
    iEnv.declareOpr(".",   2, "oField");
    iEnv.declareOpr("[]",  2, "oIndex");
    iEnv.declareOpr("[:]", 3, "oSection");
    iEnv.declareOpr("[+:]",3, "oSectPlus");
    iEnv.declareOpr("[-:]",3, "oSectMinus");
    iEnv.declareOpr("+",   2, "oAdd");
    iEnv.declareOpr("-",   2, "oSub");
    iEnv.declareOpr("*",   2, "oMul");
    iEnv.declareOpr("/",   2, "oDiv");
    iEnv.declareOpr("%",   2, "oMod");
    iEnv.declareOpr("^",   2, "oPower");
    iEnv.declareOpr("<",   2, "oNLess");
    iEnv.declareOpr("<=",  2, "oNLessEq");
    iEnv.declareOpr("=",   2, "oNEqual");
    iEnv.declareOpr("~=",  2, "oNUnequal");
    iEnv.declareOpr(">=",  2, "oNGreaterEq");
    iEnv.declareOpr(">",   2, "oNGreater");
    iEnv.declareOpr("<<",  2, "oLLess");
    iEnv.declareOpr("<<=", 2, "oLLessEq");
    iEnv.declareOpr("==",  2, "oLEqual");
    iEnv.declareOpr("~==", 2, "oLUnequal");
    iEnv.declareOpr(">>=", 2, "oLGreaterEq");
    iEnv.declareOpr(">>",  2, "oLGreater");
    iEnv.declareOpr("===", 2, "oVEqual");
    iEnv.declareOpr("~===",2, "oVUnequal");
    iEnv.declareOpr("||",  2, "oConcat");
    iEnv.declareOpr("|||", 2, "oListConcat");
    iEnv.declareOpr("**",  2, "oIntersect");
    iEnv.declareOpr("++",  2, "oUnion");
    iEnv.declareOpr("--",  2, "oDiff");
}

public static vProc instantiate(String name) {
	if (name.equals("oDeref")) return new oDeref();
	if (name.equals("oAssign")) return new oAssign();
	if (name.equals("oRevAssign")) return new oRevAssign();
	if (name.equals("oSwap")) return new oSwap();
	if (name.equals("oRevSwap")) return new oRevSwap();
	if (name.equals("oConjunction")) return new oConjunction();
	if (name.equals("oProcessArgs")) return new oProcessArgs();
	if (name.equals("oActivate")) return new oActivate();
	if (name.equals("oToBy")) return new oToBy();
	if (name.equals("oNumerate")) return new oNumerate();
	if (name.equals("oNegate")) return new oNegate();
	if (name.equals("oSize")) return new oSize();
	if (name.equals("oComplement")) return new oComplement();
	if (name.equals("oRefresh")) return new oRefresh();
	if (name.equals("oTabMatch")) return new oTabMatch();
	if (name.equals("oIsNull")) return new oIsNull();
	if (name.equals("oIsntNull")) return new oIsntNull();
	if (name.equals("oSelect")) return new oSelect();
	if (name.equals("oBang")) return new oBang();
	if (name.equals("oField")) return new oField();
	if (name.equals("oIndex")) return new oIndex();
	if (name.equals("oSection")) return new oSection();
	if (name.equals("oSectPlus")) return new oSectPlus();
	if (name.equals("oSectMinus")) return new oSectMinus();
	if (name.equals("oAdd")) return new oAdd();
	if (name.equals("oSub")) return new oSub();
	if (name.equals("oMul")) return new oMul();
	if (name.equals("oDiv")) return new oDiv();
	if (name.equals("oMod")) return new oMod();
	if (name.equals("oPower")) return new oPower();
	if (name.equals("oNLess")) return new oNLess();
	if (name.equals("oNLessEq")) return new oNLessEq();
	if (name.equals("oNEqual")) return new oNEqual();
	if (name.equals("oNUnequal")) return new oNUnequal();
	if (name.equals("oNGreaterEq")) return new oNGreaterEq();
	if (name.equals("oNGreater")) return new oNGreater();
	if (name.equals("oLLess")) return new oLLess();
	if (name.equals("oLLessEq")) return new oLLessEq();
	if (name.equals("oLEqual")) return new oLEqual();
	if (name.equals("oLUnequal")) return new oLUnequal();
	if (name.equals("oLGreaterEq")) return new oLGreaterEq();
	if (name.equals("oLGreater")) return new oLGreater();
	if (name.equals("oVEqual")) return new oVEqual();
	if (name.equals("oVUnequal")) return new oVUnequal();
	if (name.equals("oConcat")) return new oConcat();
	if (name.equals("oListConcat")) return new oListConcat();
	if (name.equals("oIntersect")) return new oIntersect();
	if (name.equals("oUnion")) return new oUnion();
	if (name.equals("oDiff")) return new oDiff();
	return null;
} // vProc instantiate(String)

} // class iOperators



final class oDeref extends vProc1 {				//  .x
    public vDescriptor Call(vDescriptor a) {
	return a.Deref();
    }
}

final class oAssign extends vProc2 {				//  v := x
    public vDescriptor Call(vDescriptor a, vDescriptor b) {
	return a.Assign(b);
    }
}

final class oRevAssign extends vProc2 {				//  v <- x
    public vDescriptor Call(vDescriptor a, vDescriptor b) {
	return a.RevAssign(b);
    }
}

final class oSwap extends vProc2 {				//  v :=: v
    public vDescriptor Call(vDescriptor a, vDescriptor b) {
	return a.Swap(b);
    }
}

final class oRevSwap extends vProc2 {				//  v <-> v
    public vDescriptor Call(vDescriptor a, vDescriptor b) {
	return a.RevSwap(b);
    }
}

final class oConjunction extends vProc2 {			//  e1 & e2
    public vDescriptor Call(vDescriptor a,vDescriptor b) {
	return a.Conjunction(b);
    }
}

final class oProcessArgs extends vProc2 {			//  p ! L
    public vDescriptor Call(vDescriptor a,vDescriptor b) {
	return a.ProcessArgs(b);
    }
}

final class oActivate extends vProc2 {				//  v @ C
    public vDescriptor Call(vDescriptor a, vDescriptor b) {
	return b.Activate(a);	// note reversal of arg order for a @ b
    }
}

final class oToBy extends vProc3 {				//  i to j by k
    public vDescriptor Call(vDescriptor a, vDescriptor b, vDescriptor c) {
	return a.ToBy(b, c);
    }
}

final class oNumerate extends vProc1 {				//  +n
    public vDescriptor Call(vDescriptor a) {
	return a.Numerate();
    }
}

final class oNegate extends vProc1 {				//  -n
    public vDescriptor Call(vDescriptor a) {
	return a.Negate();
    }
}

final class oSize extends vProc1 {				//  *x
    public vDescriptor Call(vDescriptor a) {
	return a.Size();
    }
}

final class oComplement extends vProc1 {			//  ~x
    public vDescriptor Call(vDescriptor a) {
	return a.Complement();
    }
}

final class oRefresh extends vProc1 {				//  ^C
    public vDescriptor Call(vDescriptor a) {
	return a.Refresh();
    }
}

final class oTabMatch extends vProc1 {				//  =s
    public vDescriptor Call(vDescriptor a) {
	return a.TabMatch();
    }
}

final class oIsNull extends vProc1 {				//  /x
    public vDescriptor Call(vDescriptor a) {
	return a.IsNull();
    }
}

final class oIsntNull extends vProc1 {				//  \x
    public vDescriptor Call(vDescriptor a) {
	return a.IsntNull();
    }
}

final class oSelect extends vProc1 {				//  ?x
    public vDescriptor Call(vDescriptor a) {
	return a.Select();
    }
}

final class oBang extends vProc1 {				//  ~x
    public vDescriptor Call(vDescriptor a) {
	return a.Bang();
    }
}

final class oField extends vProc2 {				//  R . f
    public vDescriptor Call(vDescriptor a, vDescriptor b) {
	if (!(a.Deref() instanceof vRecord)) {  // check before converting b
	    iRuntime.error(107, a);
	}
	return a.Field(b.mkString().toString());
    }
}

final class oIndex extends vProc2 {				//  x[v]
    public vDescriptor Call(vDescriptor a, vDescriptor b) {
	return a.Index(b);
    }
}

final class oSection extends vProc3 {				//  x[i:j]
    public vDescriptor Call(vDescriptor a, vDescriptor b, vDescriptor c) {
	return a.Section(b, c);
    }
}

final class oSectPlus extends vProc3 {				//  x[i+:j]
    public vDescriptor Call(vDescriptor a, vDescriptor b, vDescriptor c) {
	return a.SectPlus(b, c);
    }
}

final class oSectMinus extends vProc3 {				//  x[i-:j]
    public vDescriptor Call(vDescriptor a, vDescriptor b, vDescriptor c) {
	return a.SectMinus(b, c);
    }
}

final class oAdd extends vProc2 {				//  n1 + n2
    public vDescriptor Call(vDescriptor a, vDescriptor b) {
	return a.Add(b);
    }
}

final class oSub extends vProc2 {				//  n1 - n2
    public vDescriptor Call(vDescriptor a, vDescriptor b) {
	return a.Sub(b);
    }
}

final class oMul extends vProc2 {				//  n1 * n2
    public vDescriptor Call(vDescriptor a, vDescriptor b) {
	return a.Mul(b);
    }
}

final class oDiv extends vProc2 {				//  n1 / n2
    public vDescriptor Call(vDescriptor a, vDescriptor b) {
	return a.Div(b);
    }
}

final class oMod extends vProc2 {				//  n1 % n2
    public vDescriptor Call(vDescriptor a, vDescriptor b) {
	return a.Mod(b);
    }
}

final class oPower extends vProc2 {				//  n1 ^ n2
    public vDescriptor Call(vDescriptor a, vDescriptor b) {
	return a.Power(b);
    }
}

final class oNLess extends vProc2 {				//  n1 < n2
    public vDescriptor Call(vDescriptor a, vDescriptor b) {
	return a.NLess(b);
    }
}

final class oNLessEq extends vProc2 {				//  n1 <= n2
    public vDescriptor Call(vDescriptor a, vDescriptor b) {
	return a.NLessEq(b);
    }
}

final class oNEqual extends vProc2 {				//  n1 = n2
    public vDescriptor Call(vDescriptor a, vDescriptor b) {
	return a.NEqual(b);
    }
}

final class oNUnequal extends vProc2 {				//  n1 ~= n2
    public vDescriptor Call(vDescriptor a, vDescriptor b) {
	return a.NUnequal(b);
    }
}

final class oNGreaterEq extends vProc2 {			//  n1 >= n2
    public vDescriptor Call(vDescriptor a, vDescriptor b) {
	return a.NGreaterEq(b);
    }
}

final class oNGreater extends vProc2 {				//  n1 > n2
    public vDescriptor Call(vDescriptor a, vDescriptor b) {
	return a.NGreater(b);
    }
}

final class oLLess extends vProc2 {				//  s1 << s2
    public vDescriptor Call(vDescriptor a, vDescriptor b) {
	return a.LLess(b);
    }
}

final class oLLessEq extends vProc2 {				//  s1 <<= s2
    public vDescriptor Call(vDescriptor a, vDescriptor b) {
	return a.LLessEq(b);
    }
}

final class oLEqual extends vProc2 {				//  s1 == s2
    public vDescriptor Call(vDescriptor a, vDescriptor b) {
	return a.LEqual(b);
    }
}

final class oLUnequal extends vProc2 {				//  s1 ~== s2
    public vDescriptor Call(vDescriptor a, vDescriptor b) {
	return a.LUnequal(b);
    }
}

final class oLGreaterEq extends vProc2 {			//  s1 >>= s2
    public vDescriptor Call(vDescriptor a, vDescriptor b) {
	return a.LGreaterEq(b);
    }
}

final class oLGreater extends vProc2 {				//  s1 >> s2
    public vDescriptor Call(vDescriptor a, vDescriptor b) {
	return a.LGreater(b);
    }
}

final class oVEqual extends vProc2 {				//  s1 === s2
    public vDescriptor Call(vDescriptor a, vDescriptor b) {
	return a.VEqual(b);
    }
}

final class oVUnequal extends vProc2 {				//  v1 ~=== v2
    public vDescriptor Call(vDescriptor a, vDescriptor b) {
	return a.VUnequal(b);
    }
}

final class oConcat extends vProc2 {				//  s1 || s2
    public vDescriptor Call(vDescriptor a, vDescriptor b) {
	return a.Concat(b);
    }
}

final class oListConcat extends vProc2 {			//  L1 ||| L2
    public vDescriptor Call(vDescriptor a, vDescriptor b) {
	return a.ListConcat(b);
    }
}

final class oIntersect extends vProc2 {				//  x ** x
    public vDescriptor Call(vDescriptor a, vDescriptor b) {
	return a.Intersect(b);
    }
}

final class oUnion extends vProc2 {				//  x ++ x
    public vDescriptor Call(vDescriptor a, vDescriptor b) {
	return a.Union(b);
    }
}

final class oDiff extends vProc2 {				//  x -- x
    public vDescriptor Call(vDescriptor a, vDescriptor b) {
	return a.Diff(b);
    }
}
