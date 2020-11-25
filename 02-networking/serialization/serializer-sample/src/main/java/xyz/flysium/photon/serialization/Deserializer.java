package xyz.flysium.photon.serialization;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * A strategy interface for converting from data in an InputStream to an Object.
 *
 * @param <T> the object type
 * @author zeno
 * @see Serializer
 */
@FunctionalInterface
public interface Deserializer<T> {

  /**
   * Read (assemble) an object of type T from the given InputStream.
   * <p>Note: Implementations should not close the given InputStream
   * (or any decorators of that InputStream) but rather leave this up to the caller.
   *
   * @param inputStream the input stream
   * @param type        the object type
   * @return the deserialized object
   * @throws IOException in case of errors reading from the stream
   */
  T deserialize(InputStream inputStream, Class<T> type) throws IOException;

  /**
   * Read (assemble) an object of type T from the given byte array.
   *
   * @param serialized the byte array
   * @param type       the object type
   * @return the deserialized object
   * @throws IOException in case of deserialization failure
   * @since 5.2.7
   */
  default T deserializeFromByteArray(byte[] serialized, Class<T> type) throws IOException {
    return deserialize(new ByteArrayInputStream(serialized), type);
  }

}
