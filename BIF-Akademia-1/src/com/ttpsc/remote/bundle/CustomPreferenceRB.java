package com.ttpsc.remote.bundle;

import wt.util.resource.RBEntry;
import wt.util.resource.RBUUID;
import wt.util.resource.WTListResourceBundle;

@RBUUID("com.ttpsc.remote.bundle.CustomPreferenceRB")
public class CustomPreferenceRB extends WTListResourceBundle {
	
	@RBEntry("Custom document category")
	public static final String CUSTOMIZATION_CATEGORY="CUSTOMIZATION_CATEGORY";
	
	@RBEntry("Custom document preference desc")
	public static final String CUSTOMIZATION_PREF_DESC="CUSTOMIZATION_PREF_DESC";

	@RBEntry("Custom document preference name")
	public static final String CUSTOMIZATION_PREF_NAME="CUSTOMIZATION_PREF_NAME";
	


}
