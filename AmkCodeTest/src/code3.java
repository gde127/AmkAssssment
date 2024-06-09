import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class code3 {

	public static void main(String[] args) {
		
		
		if (args.length != 3) {
            System.err.println("Data not entered correctly");
            System.exit(1);
        }

        String inputFile = args[0];
        String encryptedFile = args[1];
        String decryptedFile = args[2];

        try {
            // Read RSA key pair
            FileInputStream publicKeyFile = new FileInputStream("public.key");
            byte[] publicKeyBytes = new byte[publicKeyFile.available()];
            publicKeyFile.read(publicKeyBytes);
            publicKeyFile.close();

            FileInputStream privateKeyFile = new FileInputStream("private.key");
            byte[] privateKeyBytes = new byte[privateKeyFile.available()];
            privateKeyFile.read(privateKeyBytes);
            privateKeyFile.close();

            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PublicKey publicKey = keyFactory.generatePublic(new X509EncodedKeySpec(publicKeyBytes));
            PrivateKey privateKey = keyFactory.generatePrivate(new PKCS8EncodedKeySpec(privateKeyBytes));

            // Encrypt the file
            encryptFile(inputFile, encryptedFile, publicKey);

            // Decrypt the file
            decryptFile(encryptedFile, decryptedFile, privateKey);

            System.out.println("Encryption and decryption completed successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void encryptFile(String inputFile, String encryptedFile, PublicKey publicKey)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IOException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);

        try (FileInputStream inputStream = new FileInputStream(inputFile);
             FileOutputStream outputStream = new FileOutputStream(encryptedFile)) {
            byte[] inputBytes = new byte[117];
            int bytesRead;
            while ((bytesRead = inputStream.read(inputBytes)) != -1) {
                byte[] encryptedBytes = cipher.doFinal(inputBytes, 0, bytesRead);
                outputStream.write(encryptedBytes);
            }
        }
    }

    private static void decryptFile(String encryptedFile, String decryptedFile, PrivateKey privateKey)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IOException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);

        try (FileInputStream inputStream = new FileInputStream(encryptedFile);
             FileOutputStream outputStream = new FileOutputStream(decryptedFile)) {
            byte[] encryptedBytes = new byte[128];
            int bytesRead;
            while ((bytesRead = inputStream.read(encryptedBytes)) != -1) {
                byte[] decryptedBytes = cipher.doFinal(encryptedBytes, 0, bytesRead);
                outputStream.write(decryptedBytes);
            }
        }


	}

}
