package com.ttpsc.workable;

import wt.method.RemoteInterface;
import wt.vc.wip.Workable;

@RemoteInterface
public interface WorkableService {
	
	<T extends Workable> T checkIn(T workable,String checkInComment) throws Exception;
	<T extends Workable> T checkOut(T workable,String checkOutComment) throws Exception;

}
