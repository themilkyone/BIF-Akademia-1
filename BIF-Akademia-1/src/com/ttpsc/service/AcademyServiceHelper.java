package com.ttpsc.service;

import wt.services.ServiceFactory;

public class AcademyServiceHelper {
	
	public static final AcademyService service=ServiceFactory.getService(AcademyService.class);
	

	public static void helloNotFromService(String message) {
		System.out.println(message);
	}
}
