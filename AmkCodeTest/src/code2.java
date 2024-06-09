import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class code2 {
	
	public static void main(String[] args) {
		 if (args.length != 1) {
	            System.err.println("No files exists theres");
	            System.exit(1);
	        }

	        String filePath = args[0];

	        try {
	           
	            MessageDigest digest = MessageDigest.getInstance("SHA-256");

	            
	            try (FileInputStream inputStream = new FileInputStream(filePath)) {
	                byte[] buffer = new byte[8192];
	                int bytesRead;
	                while ((bytesRead = inputStream.read(buffer)) != -1) {
	                    digest.update(buffer, 0, bytesRead);
	                }
	            }

	            
	            byte[] hashBytes = digest.digest();

	         
	            StringBuilder hexString = new StringBuilder();
	            for (byte hashByte : hashBytes) {
	                String hex = Integer.toHexString(0xff & hashByte);
	                if (hex.length() == 1) {
	                    hexString.append('0');
	                }
	                hexString.append(hex);
	            }

	            // Print the hash in hexadecimal encoding
	            System.out.println("SHA-256 Hash of " + filePath + ":");
	            System.out.println(hexString.toString());
	        } catch (NoSuchAlgorithmException e) {
	            System.err.println("SHA-256 algorithm not available: " + e.getMessage());
	        } catch (IOException e) {
	            System.err.println("Error reading file: " + e.getMessage());
	        }
	}

}
