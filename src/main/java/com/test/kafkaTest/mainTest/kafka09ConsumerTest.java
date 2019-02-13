//import org.apache.kafka.clients.consumer.Consumer;
//import org.apache.kafka.clients.consumer.ConsumerRecord;
//import org.apache.kafka.clients.consumer.ConsumerRecords;
//import org.apache.kafka.clients.consumer.KafkaConsumer;
//
//import java.util.Arrays;
//import java.util.Map;
//import java.util.Properties;
//
///**
// * Created by gexiaoshan on 2018/10/10.
// */
//public class kafka09ConsumerTest {
//    public static void main(String[] args) {
//        Properties props = new Properties();
//        props.put("bootstrap.servers", "192.168.40.65:9093");
//        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
//        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
//        props.put("group.id", "mygroup");
//        props.put("enable.auto.commit", "true");
//        props.put("auto.offset.reset", "earliest");
//        props.put("partition.assignment.strategy", "1");
//
//        Consumer<String, String> consumer = new KafkaConsumer<String, String>(props);
//        consumer.subscribe("test1");
//
//       while(true){
//           Map<String, ConsumerRecords<String, String>> records = consumer.poll(1000);
//           if(records!=null)
//           System.out.println(records);
////            for (ConsumerRecord<String, String> record : records) {
////                System.out.println(record);
////                //consumer.seekToBeginning(new TopicPartition(record.topic(), record.partition()));
////            }
//        }
//    }
//}
