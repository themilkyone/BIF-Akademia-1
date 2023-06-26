package com.ttpsc.remote.bundle;

import wt.util.resource.RBEntry;
import wt.util.resource.RBUUID;
import wt.util.resource.WTListResourceBundle;


@RBUUID("com.ttpsc.remote.bundle.TestBoundleResource")
public class TestBundleResource extends WTListResourceBundle {
	
	
	@RBEntry("Instruction_1")
	public static final String FIRST_INSTRUCTION="FIRST_INSTRUCTION";
	
	@RBEntry("Hi {0}")
	public static final String SECOND_INSTRUCTION="SECOND_INSTRUCTION";

}
