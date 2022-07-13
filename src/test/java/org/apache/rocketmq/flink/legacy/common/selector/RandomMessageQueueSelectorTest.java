package org.apache.rocketmq.flink.legacy.common.selector;

import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;

import org.junit.Assert;
import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class RandomMessageQueueSelectorTest {

    @Test
    public void testSelect() {
        MessageQueueSelector hash = new RandomMessageQueueSelector();
        List<Integer> queueIds =
                new ArrayList<Integer>() {
                    {
                        add(0);
                        add(1);
                        add(2);
                    }
                };
        List<MessageQueue> queues = new ArrayList<>();
        MessageQueue queue0 = new MessageQueue("test", "broker-a", 0);
        MessageQueue queue1 = new MessageQueue("test", "broker-b", 1);
        MessageQueue queue2 = new MessageQueue("test", "broker-c", 2);
        queues.add(queue0);
        queues.add(queue1);
        queues.add(queue2);

        Message message = new Message("test", "*", "1", "body".getBytes(StandardCharsets.UTF_8));

        MessageQueue messageQueue = hash.select(queues, message, 1);
        int queueId = messageQueue.getQueueId();

        Assert.assertTrue(queueIds.contains(queueId));
    }
}
