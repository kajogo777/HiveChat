package jawbreakergummybear.hivechat.core;

import org.spongycastle.jce.provider.BouncyCastleProvider;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PublicKey;
import java.security.Security;
import java.security.spec.ECGenParameterSpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 * Created by Rana on 12/2/16.
 */
public class Crypto {
    KeyPair keyPair;

    public Crypto(){
        try{
            Security.insertProviderAt(new org.spongycastle.jce.provider.BouncyCastleProvider(), 1);

            String name = "secp256r1";

            KeyPairGenerator kpg = KeyPairGenerator.getInstance("ECDH", BouncyCastleProvider.PROVIDER_NAME);
            kpg.initialize(new ECGenParameterSpec(name));

            keyPair = kpg.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        }
    }

    public static byte[] encodeKey(PublicKey pubKey){

        final byte[] keyEncoded = pubKey.getEncoded();

        return keyEncoded;

    }

    public static PublicKey decodeKey(byte[] encodedKey){
        final byte[] ek = encodedKey;
        final String format = "X.509";
        final String algorithm = "ECIES";

        PublicKey publicKey = new PublicKey() {
            @Override
            public String getAlgorithm() {
                return algorithm;
            }

            @Override
            public String getFormat() {
                return format;
            }

            @Override
            public byte[] getEncoded() {
                return ek;
            }
        };
        return publicKey;
    }

    public static byte[] encrypt(PublicKey pkey, String data){
        byte[] encodedBytes = null;

        try {
            Cipher c = Cipher.getInstance("ECIES", BouncyCastleProvider.PROVIDER_NAME);
            c.init(Cipher.ENCRYPT_MODE, pkey);

            encodedBytes = c.doFinal(data.getBytes());

        }catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        }

        return encodedBytes;
    }

    public byte[] decrypt(byte[] encodedBytes){
        byte[] decodedBytes = null;
        try {

            Cipher c = Cipher.getInstance("ECIES", BouncyCastleProvider.PROVIDER_NAME);
            c.init(Cipher.DECRYPT_MODE, keyPair.getPrivate());
            decodedBytes = c.doFinal(encodedBytes);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }

        return decodedBytes;
    }
}
