package com.ttpsc.remote.bundle;

import wt.util.resource.RBComment;
import wt.util.resource.RBEntry;
import wt.util.resource.RBUUID;
import wt.util.resource.WTListResourceBundle;

@RBUUID("com.ttpsc.remote.bundle.PreferenceRB")
public class PreferenceRB extends WTListResourceBundle {
	
	@RBEntry("My custom category")
	@RBComment("Custom commment")
	public static final String CUSTOMIZATION_CATEGORY="CUSTOMIZATION_CATEGORY";
	
	@RBEntry("My custom pref desc")
	public static final String CUSTOMIZATION_PREF_DESC="CUSTOMIZATION_PREF_DESC";

	@RBEntry("My custom pref name")
	public static final String CUSTOMIZATION_PREF_NAME="CUSTOMIZATION_PREF_NAME";
	
	
	
	@RBEntry("My second custom pref desc")
	public static final String CUSTOMIZATION_PREF_SECOND_DESC="CUSTOMIZATION_PREF_SECOND_DESC";

	@RBEntry("My second custom pref name")
	public static final String CUSTOMIZATION_PREF_SECOND_NAME="CUSTOMIZATION_PREF_SECOND_NAME";


	

}
