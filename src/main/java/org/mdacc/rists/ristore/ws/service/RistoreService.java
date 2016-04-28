package org.mdacc.rists.ristore.ws.service;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.core.io.InputStreamResource;

public interface RistoreService {
	InputStream findByTRF(String trf) throws IOException;
	InputStreamResource findByMRN(String mrn);
}
