package org.mdacc.rists.ristore.ws.service;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.mdacc.rists.bdi.WorkflowUtils;
import org.mdacc.rists.bdi.db.utils.DBConnection;
import org.mdacc.rists.bdi.models.FmReportTb;
import org.mdacc.rists.bdi.models.SpecimenTb;
import org.mdacc.rists.ristore.ws.model.vo.FmReportVO;
import org.mdacc.rists.ristore.ws.models.FoundationTRF;
import org.mdacc.rists.ristore.ws.repository.FileLoadRepository;
import org.mdacc.rists.ristore.ws.repository.FmReportRepository;
import org.mdacc.rists.ristore.ws.repository.SpecimenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import oracle.jdbc.OracleTypes;

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
			String filename = getBase(fileLoadRepository.findUriByFileLoadId(report.getFileLoadId().longValue()));
			reportVo.setFilename(filename);
			fmReports.add(reportVo);
		}
		return fmReports;
	}

	@Override
	public List<FmReportVO> findFmReportsByBlockId(String blockId) {
		List<FmReportTb> reports = fmReportRepository.findByBlockId(blockId);
		if (reports == null) {
			return null;
		}
		List<FmReportVO> fmReports = new ArrayList<FmReportVO>();
		for (FmReportTb report : reports) {
			FmReportVO reportVo = new FmReportVO(report);
			String filename = getBase(fileLoadRepository.findUriByFileLoadId(report.getFileLoadId().longValue()));
			reportVo.setFilename(filename);
			fmReports.add(reportVo);
		}
		return fmReports;
	}

	@Override
	public List<FmReportVO> findFmReportsByReportId(String reportId) {
		List<FmReportTb> reports = fmReportRepository.findByReportId(reportId);
		if (reports == null) {
			return null;
		}
		List<FmReportVO> fmReports = new ArrayList<FmReportVO>();
		for (FmReportTb report : reports) {
			FmReportVO reportVo = new FmReportVO(report);
			String filename = getBase(fileLoadRepository.findUriByFileLoadId(report.getFileLoadId().longValue()));
			reportVo.setFilename(filename);
			fmReports.add(reportVo);
		}
		return fmReports;
	}

	@Override
	public List<FmReportVO> findFmReportsByMrn(String mrn) {
		List<SpecimenTb> specimens = specimenRepository.findFmSpecimenByMrn(mrn);
		if (specimens == null) {
			return null;
		}
		List<FmReportVO> fmReports = new ArrayList<FmReportVO>();
		for (SpecimenTb specimen : specimens) {
			FmReportTb report = specimen.getFmReportTb();
			if (report.getDeleteTs() == null) {
				FmReportVO reportVo = new FmReportVO(report);
				String filename = getBase(fileLoadRepository.findUriByFileLoadId(report.getFileLoadId().longValue()));
				reportVo.setFilename(filename);
				fmReports.add(reportVo);
			}
		}
		return fmReports;
	}
	
	@Override
	public void updateTRFList(List<FoundationTRF> trfs) {
		Connection con = DBConnection.getConnection();
		CallableStatement stmt;
		try {
			stmt = con.prepareCall("{call FILE_QUEUE_UTIL.get_invalid_file_by_type(?,?,?,?,?)}");
			stmt.registerOutParameter(2, OracleTypes.CURSOR);
			stmt.registerOutParameter(3, Types.VARCHAR);
			stmt.registerOutParameter(4, Types.VARCHAR);
			stmt.registerOutParameter(5, Types.VARCHAR);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			System.exit(1);
		}
		
		
		
	}
	
	private String getBase(String path) {
		int indexSlash = path.lastIndexOf('/');
		int indexDot = path.lastIndexOf('.');
		if (indexSlash > 1) {
			if (indexDot > 1) {
				return path.substring(indexSlash + 1, indexDot);
			} else {
				return path.substring(indexSlash + 1);
			}
		}
		return null;
	}

	

}
