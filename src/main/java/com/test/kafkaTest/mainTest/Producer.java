package com.test.kafkaTest.mainTest;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.List;
import java.util.Properties;

/**
 * Created by gexiaoshan on 2018/9/18.
 */
public class Producer {

    private static final String TOPIC = "test1";
    private static final String BROKER_LIST = "192.168.204.107:9092,192.168.204.108:9092,192.168.204.109:9092";//
    private static KafkaProducer<String, String> producer = null;

    /*
    初始化生产者
     */
    static {
        Properties configs = initConfig();
        producer = new KafkaProducer<String, String>(configs);
    }

    /*
    初始化配置
     */
    private static Properties initConfig() {
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BROKER_LIST);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.ACKS_CONFIG, "1");
//        properties.put(ProducerConfig.INTERCEPTOR_CLASSES_CONFIG, "ProducerInterceptorImpl");
//        properties.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, 120);
        return properties;
    }

    public static void main(String[] args) throws InterruptedException {

        List<PartitionInfo> list = producer.partitionsFor(TOPIC);
        System.out.println(list.size());
       /* try {
            final Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            for (final Enumeration<NetworkInterface> ifaces = networkInterfaces; ifaces.hasMoreElements();) {
                final NetworkInterface iface = ifaces.nextElement();
                InetAddress ia = null;
                for (final Enumeration<InetAddress> ips = iface.getInetAddresses(); ips.hasMoreElements();) {
                    ia = ips.nextElement();
                    System.out.println(ia.toString());
                }
            }
        } catch (final SocketException e) {
            throw new RuntimeException("unable to retrieve host names of localhost");
        }*/

        //消息实体
        ProducerRecord<String, String> record = null;
        for (int i = 0; i < 10; i++) {
            record = new ProducerRecord<String, String>(TOPIC, "value" + (int) (10 * (Math.random())));
            //发送消息
            producer.send(record, new Callback() {
                @Override
                public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                    if (null != e) {
                        System.out.println("send error:" + e.getMessage());
                    } else {
                        System.out.println(String.format("offset:%s,partition:%s", recordMetadata.offset(), recordMetadata.partition()));
//                        System.out.println(recordMetadata.toString());
                    }
                }
            });
        }
        producer.close();
    }
}
