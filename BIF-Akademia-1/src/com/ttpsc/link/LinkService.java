package com.ttpsc.link;

import wt.doc.WTDocument;
import wt.method.RemoteInterface;
import wt.part.WTPart;
import wt.part.WTPartDescribeLink;
import wt.vc.wip.Workable;

@RemoteInterface
public interface LinkService {

	void createChildForPart(WTPart parent) throws Exception;
	
	WTPartDescribeLink createDescribeLink(WTPart part, WTDocument document) throws Exception; 
	
	
	
}
