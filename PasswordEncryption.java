import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.util.Base64;

public class PasswordEncryption {

    private static final int SALT_LENGTH = 16;
    private static final int ITERATIONS = 65536;
    private static final int KEY_LENGTH = 256;

    // Generate salt
    public static String generateSalt() throws NoSuchAlgorithmException {
        SecureRandom random = SecureRandom.getInstanceStrong();
        byte[] salt = new byte[SALT_LENGTH];
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    // Hash password
    public static String hashPassword(String password, String salt)
            throws NoSuchAlgorithmException, InvalidKeySpecException {

        PBEKeySpec spec = new PBEKeySpec(
                password.toCharArray(),
                Base64.getDecoder().decode(salt),
                ITERATIONS,
                KEY_LENGTH
        );

        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        byte[] hash = factory.generateSecret(spec).getEncoded();

        return Base64.getEncoder().encodeToString(hash);
    }

    // Verify password
    public static boolean verifyPassword(String inputPassword, String storedHash, String storedSalt)
            throws NoSuchAlgorithmException, InvalidKeySpecException {

        String newHash = hashPassword(inputPassword, storedSalt);
        return newHash.equals(storedHash);
    }
}