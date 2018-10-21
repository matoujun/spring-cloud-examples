package org.matoujun.cloud.common;

import java.io.*;
import java.util.Base64;

/**

 * User: matoujun
 *
 */
public class ObjectSerializeUtil {
  public static String toSerializableString(Serializable o) throws IOException {
    ByteArrayOutputStream stream = new ByteArrayOutputStream();
    ObjectOutputStream oos = new ObjectOutputStream(stream);
    oos.writeObject(o);
    oos.close();
    return Base64.getEncoder().encodeToString(stream.toByteArray());
  }

  @SuppressWarnings("unchecked")
  public static <T> T fromSerializableString(String s) throws IOException, ClassNotFoundException {
    byte[] data = Base64.getDecoder().decode(s);
    ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data));
    Object o = ois.readObject();
    ois.close();
    return (T) o;
  }
}
