package com.ttpsc.sql;

import java.util.List;
import java.util.Optional;

import wt.doc.WTDocument;
import wt.fc.Persistable;
import wt.inf.container.WTContainer;
import wt.inf.container.WTContainerRef;
import wt.method.RemoteInterface;
import wt.part.WTPart;
import wt.part.WTPartMaster;
import wt.part.WTPartUsageLink;
import wt.query.QueryException;
import wt.query.QuerySpec;
import wt.util.WTException;

@RemoteInterface
public interface SQLQueryService {

	QuerySpec getObjectInfromation(Persistable persistable) throws QueryException;

	void queryWTPart() throws QueryException;

	Optional<WTPart> findPartByName(String name) throws WTException;

	Optional<WTPart> findPart(String name) throws WTException;

	Optional<WTPart> findPart(String name, WTContainer container) throws WTException;

	Optional<WTPart> findPart(String name, WTContainer container, boolean isLatest) throws WTException;

	
	Optional<WTPart> findPartByNumber(String number) throws WTException;

	List<WTPart> findAllPartsBySoftType(String softTypeName) throws WTException;

	List<WTDocument> findDocumentsBySoftType(String softTypeName) throws WTException;

	List<WTPart> findPartsInContainer(String containerName) throws WTException;

	List<WTPart> findPartsInOrganization(String organizationName) throws WTException;

	List<WTPartUsageLink> getUsageLinksByWTPart(WTPart wtPart) throws WTException;
	
	WTPartUsageLink findUsageLink(WTPart part,WTPartMaster child) throws WTException;

	WTContainer findContainerByName(String name) throws WTException;

	void deleteParts(WTContainer container) throws WTException;

}