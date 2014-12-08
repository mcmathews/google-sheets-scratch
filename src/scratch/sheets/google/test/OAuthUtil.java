package scratch.sheets.google.test;

import java.io.File;
import java.net.URI;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;

public class OAuthUtil {
	
	public static Credential authorize() throws Exception {
//		byte[] keyBytes = "-----BEGIN PRIVATE KEY-----\nMIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAJnC8OW2y6R1x9sl\nRUKSLBzInZ6E+1+UaCYoP4LCptYeOcq9m60CpjgpOoa6XlNCl346G69iPET8toT+\nK03mlRG8XUQmGekvQuh9TP/WkJddsSGkp0Di5S5O7BrjVoh5b16cGW/gjbd7iMpE\n9k/oluKLXWellBWT0lIieMUUh7+DAgMBAAECgYBwIOjrm9IFrJ6CATvwwAfLNcE7\nyF5H5UHOEau+/5QKsSAT6RGNqVDtw6MafO4saXNwaY+m5kxtQp0XfwK1XGF33Clr\njTz8rMvWeq66L6eE96p/RcWIkc26uaURsJJBMzoHrlzoKz5S7Qyj4oY8vs2CKXLc\nO/tj+6uiQFxFHQGIAQJBAMpMqsJ5D7SiZ2ySdqT8nVqvz4y8ys12CWp2GQhHLIc8\nC2mUH4/gEEMp0p0ujt0R/CnlWsiUpWo3WcEgdkt0wUMCQQDCk9uEfkHmkb7S0mvk\nLXLyCdqenjFlGxYcmUHC3sFOL+yQV8UAVpZGg7rPPGbxPRL+4bGYDOTawlHgn9eF\nPwTBAkEAiWJVmSqUAu1D5ZT15Af2aO76ua5A4BWoQVegmmGTmOerLHE+mfvlr2cI\ntFw5A2NCZ8VtyMhCKgca+jAshgPtdwJASAb7x7wFIRjqjpgeIEVCu54yM21T052H\nPebtlPlojUzoQTZ8Une7ATdaO8ScRsjY80kQuLxtKx2ruX7yBP0jAQJAFzASBb5j\nZNNvHawlUEPw/Mgrw97S4owKO5M3BFGTE90Kj8OWH9ga1TZ5/ZSFxn6QYFQdUzH0\ni3Ghd+dUpcFetw\u003d\u003d\n-----END PRIVATE KEY-----\n".getBytes();
//		PrivateKey privateKey = SecurityUtils.getRsaKeyFactory().generatePrivate(new PKCS8EncodedKeySpec(keyBytes));

		URI keyUri = OAuthUtil.class.getClassLoader().getResource("TestProjectThingAndStuff-6de8fea199c1.p12").toURI();
		GoogleCredential credential = new GoogleCredential.Builder()
			    .setTransport(new NetHttpTransport())
			    .setJsonFactory(new JacksonFactory())
			    .setServiceAccountId(OAuthConstants.CLIENT_EMAIL)
//			    .setServiceAccountPrivateKey(privateKey)
			    .setServiceAccountPrivateKeyFromP12File(new File(keyUri))
			    .setServiceAccountScopes(OAuthConstants.SCOPES)
			    .setServiceAccountUser("mcmathe1@asu.edu")
			    .build();
		
		return credential;
	}
	
}
