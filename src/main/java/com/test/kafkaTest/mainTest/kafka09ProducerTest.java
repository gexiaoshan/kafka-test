//import org.apache.kafka.clients.producer.Callback;
//import org.apache.kafka.clients.producer.KafkaProducer;
//import org.apache.kafka.clients.producer.ProducerRecord;
//import org.apache.kafka.clients.producer.RecordMetadata;
//
//import java.util.Properties;
//
///**
// * Created by gexiaoshan on 2018/10/10.
// */
//public class kafka09ProducerTest {
//    public static void main(String[] args) {
//        Properties properties = new Properties();
//        properties.put("bootstrap.servers", "192.168.40.65:9093");
//        properties.put("metadata.broker.list", "192.168.40.65:9093");
//        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
//        properties.put("serializer.class", "kafka.serializer.StringEncoder");
//        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
//        properties.put("request.required.acks", "1");
//        KafkaProducer<Integer, String> producer = new KafkaProducer<Integer, String>(properties);
//        for (int iCount = 0; iCount < 100; iCount++) {
//            String message = "My Test Message No " + iCount;
//            ProducerRecord<Integer, String> record = new ProducerRecord<Integer, String>("mytesttopic", message);
//            producer.send(record);
//        }
//        producer.close();
//
//    }
//}
