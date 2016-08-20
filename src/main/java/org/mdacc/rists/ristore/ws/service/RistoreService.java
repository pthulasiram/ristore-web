package org.mdacc.rists.ristore.ws.service;

import java.util.List;

import org.mdacc.rists.bdi.models.SpecimenTb;
import org.mdacc.rists.ristore.ws.model.vo.FmReportVO;

public interface RistoreService {

	List<SpecimenTb> findAll();
	
	FmReportVO findFmReportByBlockId(String blockId);
}
