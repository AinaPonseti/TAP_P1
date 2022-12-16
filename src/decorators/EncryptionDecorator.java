package decorators;

import actors.Actor;
import messages.Message;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class EncryptionDecorator extends ActorDecorator {

    //attributes
    KeyGenerator kg;
    private Cipher cipher;
    private SecretKey key;


    public EncryptionDecorator(Actor actor) {
        super(actor);
        try {
            kg = KeyGenerator.getInstance("AES");
            kg.init(128);
            key = kg.generateKey();
            cipher = Cipher.getInstance("AES");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void send(Message message) {
        if (message.getText() != null){
            System.out.println("Message encrypted successfully");
        }
        messageQueue.add(message);
    }

    /**
     * Method to encrypt the text of a message
     * @param message message to encrypt
     */
    private void encryptMessage(Message message) {
        try {
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] encryptedMessage = cipher.doFinal(message.getText().getBytes(StandardCharsets.UTF_8));
            Base64.Encoder encoder = Base64.getEncoder();
            message.setText(encoder.encodeToString(encryptedMessage));

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
     * Method to decrypt a message
     * @param message message to decrypt
     */
    private void decryptMessage(Message message){
        try {
            Base64.Decoder decoder = Base64.getDecoder();
            byte[] encryptedMessage = decoder.decode(message.getText());
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] decryptedMessage = cipher.doFinal(encryptedMessage);
            message.setText(new String(decryptedMessage));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}