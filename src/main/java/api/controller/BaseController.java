package api.controller;

import api.controller.filter.CORS;
import java.util.Base64;


/**
 * Base Controller for end-points with @CORS and @Inherited end-points.
 */
@CORS
public class BaseController {
    protected final        String encryptionAlgorithm    = "AES"; // Encryption algorithm
    protected static final String APPLICATION_JSON_UTF_8 = "application/json;charset=utf-8"; // Response type for end-points

    protected byte[] getApiKeyBinary() {
        String apiKey = "PEKYDbc6bpiNff7n+dt1pQ==";
        return Base64.getDecoder().decode(apiKey);
    }
}
