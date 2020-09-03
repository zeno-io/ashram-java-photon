/*
 * MIT License
 *
 * Copyright (c) 2020 SvenAugustus
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package rocketmq.classic.samples.batch;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;

/**
 * 生产者：批量发送消息， 建议消息大小不超过256KB
 */
public class SplitBatchProducer {

  public static void main(String[] args) throws Exception {
    DefaultMQProducer producer = new DefaultMQProducer("BatchProducerGroupName");
    producer.setNamesrvAddr("127.0.0.1:9876");
    producer.start();

    //large batch
    String topic = "BatchTest";
    List<Message> messages = new ArrayList<>(100 * 1000);
    for (int i = 0; i < 100 * 1000; i++) {
      messages.add(new Message(topic, "Tag", "OrderID" + i, ("Hello world " + i).getBytes()));
    }

    //split the large batch into small ones:
    ListSplitter splitter = new ListSplitter(messages);
    while (splitter.hasNext()) {
      List<Message> listItem = splitter.next();
      producer.send(listItem);
    }
  }

}

class ListSplitter implements Iterator<List<Message>> {

  private int sizeLimit = 1000 * 1000;
  private final List<Message> messages;
  private int currIndex;

  public ListSplitter(List<Message> messages) {
    this.messages = messages;
  }

  @Override
  public boolean hasNext() {
    return currIndex < messages.size();
  }

  @Override
  public List<Message> next() {
    int nextIndex = currIndex;
    int totalSize = 0;
    for (; nextIndex < messages.size(); nextIndex++) {
      Message message = messages.get(nextIndex);
      int tmpSize = message.getTopic().length() + message.getBody().length;
      Map<String, String> properties = message.getProperties();
      for (Map.Entry<String, String> entry : properties.entrySet()) {
        tmpSize += entry.getKey().length() + entry.getValue().length();
      }
      tmpSize = tmpSize + 20; //for log overhead
      if (tmpSize > sizeLimit) {
        //it is unexpected that single message exceeds the sizeLimit
        //here just let it go, otherwise it will block the splitting process
        if (nextIndex - currIndex == 0) {
          //if the next sublist has no element, add this one and then break, otherwise just break
          nextIndex++;
        }
        break;
      }
      if (tmpSize + totalSize > sizeLimit) {
        break;
      } else {
        totalSize += tmpSize;
      }

    }
    List<Message> subList = messages.subList(currIndex, nextIndex);
    currIndex = nextIndex;
    return subList;
  }

  @Override
  public void remove() {
    throw new UnsupportedOperationException("Not allowed to remove");
  }
}
