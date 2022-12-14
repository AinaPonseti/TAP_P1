package decorators;

import actors.Actor;
import messages.Message;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.spec.KeySpec;

public class EncryptionDecorator extends ActorDecorator {

    //Constants
    private static final Charset FORMAT = StandardCharsets.UTF_8;
    private static String encryptionKey = "ThisIsSpartaThisIsSparta";
    private static String encryptionScheme = "DESede";

    //attributes
    private KeySpec ks;
    private SecretKeyFactory skf;
    private Cipher cipher;
    private SecretKey key;


    public EncryptionDecorator(Actor actor) {
        super(actor);
        try {
            ks = new DESedeKeySpec(encryptionKey.getBytes(StandardCharsets.UTF_8));
            skf = SecretKeyFactory.getInstance(encryptionScheme);
            cipher = Cipher.getInstance(encryptionScheme);
            key = skf.generateSecret(ks);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void send(Message message) {
        encryptMessage(message);
        decoratedActor.send(message);
    }

    /**
     * Method to encrypt the text of a message
     * @param message message to encrypt
     */
    private void encryptMessage(Message message) {
        try {
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] encryptedMessage = cipher.doFinal(message.getText().getBytes(StandardCharsets.UTF_8));
            message.setText(new String(encryptedMessage));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMessageReceived(Message message) {
        decryptMessage(message);
        decoratedActor.onMessageReceived(message);
    }

    /**
     * Method to decrupt a message
     * @param message message to decrypt
     */
    private void decryptMessage(Message message){
        try {
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] decryptedMessage = cipher.doFinal(message.getText().getBytes());
            message.setText(new String(decryptedMessage));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}