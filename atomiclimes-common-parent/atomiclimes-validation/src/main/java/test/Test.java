package test;

import java.io.File;
import java.util.Collection;

import com.fasterxml.jackson.databind.JsonNode;
import com.reprezen.jsonoverlay.JsonOverlay;
import com.reprezen.jsonoverlay.Overlay;
import com.reprezen.kaizen.oasparser.OpenApi3Parser;
import com.reprezen.kaizen.oasparser.model3.OpenApi3;
import com.reprezen.kaizen.oasparser.model3.Schema;
import com.reprezen.kaizen.oasparser.val.ValidationResults;
import com.reprezen.kaizen.oasparser.val3.OpenApi3Validator;
import com.reprezen.kaizen.oasparser.val3.SchemaValidator;

class Test {
	static OpenApi3Parser parser = new OpenApi3Parser();

	public static void main(String[] args) {
		File specFile = null;
		OpenApi3 spec = null;
		try {
			spec = parser.parse(specFile);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Schema schema = spec.getSchema("name");
		
	}

}
