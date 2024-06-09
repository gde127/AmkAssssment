import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;



public class Code1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		try {
			KeyPairGenerator generator=KeyPairGenerator.getInstance("RSA");
			generator.initialize(2048);
			KeyPair keyPair=generator.generateKeyPair();
			
			byte[] publicKey=keyPair.getPublic().getEncoded();
			byte[] privateKey=keyPair.getPrivate().getEncoded();
			
			String publicvalue=Base64.getEncoder().encodeToString(publicKey);
			String privateValue=Base64.getEncoder().encodeToString(privateKey);
			System.out.println("public key"+publicvalue);
			System.out.println("private key"+privateKey);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

}
