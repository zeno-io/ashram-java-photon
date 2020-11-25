package xyz.flysium.photon.serialization;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Objects;

/**
 * A convenient delegate with pre-arranged configuration state for common serialization needs.
 * Implements {@link Serializer} and {@link Deserializer} itself, so can also be passed into such
 * more specific callback methods.
 *
 * @author zeno
 */
public class SerializationDelegate<T> implements Serializer<T>, Deserializer<T> {

  private final Serializer<T> serializer;

  private final Deserializer<T> deserializer;

  /**
   * Create a {@code SerializationDelegate} with the given serializer/deserializer.
   *
   * @param serializer   the {@link Serializer} to use (never {@code null)}
   * @param deserializer the {@link Deserializer} to use (never {@code null)}
   */
  public SerializationDelegate(Serializer<T> serializer, Deserializer<T> deserializer) {
    Objects.requireNonNull(serializer, "Serializer must not be null");
    Objects.requireNonNull(deserializer, "Deserializer must not be null");
    this.serializer = serializer;
    this.deserializer = deserializer;
  }

  public String name() {
    return "";
  }

  @Override
  public void serialize(T object, OutputStream outputStream) throws IOException {
    this.serializer.serialize(object, outputStream);
  }

  @Override
  public T deserialize(InputStream inputStream, Class<T> type) throws IOException {
    return this.deserializer.deserialize(inputStream, type);
  }

}
