package org.mdacc.rists.ristore.ws.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;

@Service
public class RistoreServiceBean implements RistoreService {

	public InputStream findByTRF(String trf) throws IOException {
		String filePath = "/Users/djiao/Box Sync/Work/Projects/RIStore/foundation/foundation_new/" + trf + ".xml";
		File file = new File(filePath);
		if (!file.exists()) {
			System.err.println("File " + filePath + " does not exist.");
			System.exit(1);
		}
		return new FileInputStream(file);
	}

	@Override
	public InputStreamResource findByMRN(String mrn) {
		// TODO Auto-generated method stub
		return null;
	}
}
