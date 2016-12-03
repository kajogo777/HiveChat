package jawbreakergummybear.hivechat.core;

import android.util.Base64;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;

/**
 * Created by georgesedky on 12/3/16.
 */
public class Peer{
    public PublicKey pKey;
    public String name;

    public Peer(byte[] key){
        pKey = Crypto.decodeKey(key);
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
            byte[] thedigest = md.digest(key);
            name = Base64.encodeToString(thedigest, Base64.DEFAULT);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

}
