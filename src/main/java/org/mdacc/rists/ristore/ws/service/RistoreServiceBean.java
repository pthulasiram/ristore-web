package org.mdacc.rists.ristore.ws.service;

import java.util.ArrayList;
import java.util.List;

import org.mdacc.rists.bdi.models.FmReportTb;
import org.mdacc.rists.bdi.models.SpecimenTb;
import org.mdacc.rists.ristore.ws.model.vo.FmReportVO;
import org.mdacc.rists.ristore.ws.repository.FileLoadRepository;
import org.mdacc.rists.ristore.ws.repository.FmReportRepository;
import org.mdacc.rists.ristore.ws.repository.SpecimenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RistoreServiceBean implements RistoreService {

	@Autowired
	private SpecimenRepository specimenRepository;
	
	@Autowired
	private FmReportRepository fmReportRepository;
	
	@Autowired
	private FileLoadRepository fileLoadRepository;
	
	@Override
	public List<FmReportVO> findAllFmReports() {
		List<FmReportTb> reports = fmReportRepository.findAllFmReports();
		if (reports == null) {
			return null;
		}
		List<FmReportVO> fmReports = new ArrayList<FmReportVO>();
		for (FmReportTb report : reports) {
			FmReportVO reportVo = new FmReportVO(report);
			String filepath = fileLoadRepository.findUriByFileLoadId(report.getFileLoadId().longValue());
			reportVo.setFilepath(filepath);
			fmReports.add(reportVo);
		}
		
		return fmReports;
	}
	
	@Override
	public FmReportVO findFmReportByBlockId(String blockId) {
		FmReportTb report = fmReportRepository.findByBlockId(blockId);
		if (report == null) {
			return null;
		}
		FmReportVO reportVo = new FmReportVO(report);
		String filepath = fileLoadRepository.findUriByFileLoadId(report.getFileLoadId().longValue());
		System.out.println(filepath);
		reportVo.setFilepath(filepath);
		return reportVo;
	}

	@Override
	public FmReportVO findFmReportByReportId(String reportId) {
		FmReportTb report = fmReportRepository.findByReportId(reportId);
		if (report == null) {
			return null;
		}
		FmReportVO reportVo = new FmReportVO(report);
		String filepath = fileLoadRepository.findUriByFileLoadId(report.getFileLoadId().longValue());
		reportVo.setFilepath(filepath);
		return reportVo;
	}

	@Override
	public List<FmReportVO> findFmReportByMrn(String mrn) {
		List<SpecimenTb> specimens = specimenRepository.findFmSpecimenByMrn(mrn);
		if (specimens == null) {
			return null;
		}
		List<FmReportVO> fmReports = new ArrayList<FmReportVO>();
		for (SpecimenTb specimen : specimens) {
			FmReportTb report = specimen.getFmReportTb();
			if (report.getDeleteTs() == null) {
				FmReportVO reportVo = new FmReportVO(report);
				String filepath = fileLoadRepository.findUriByFileLoadId(report.getFileLoadId().longValue());
				reportVo.setFilepath(filepath);
				fmReports.add(reportVo);
			}
		}
		return fmReports;
	}

}
