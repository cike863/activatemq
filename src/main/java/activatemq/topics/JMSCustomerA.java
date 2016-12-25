package activatemq.topics;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * 消息订阅者A
 * Created by  XY on 2016/12/25.
 */
public class JMSCustomerA {
    private static final String USERNAME = ActiveMQConnection.DEFAULT_USER;//默认用户名
    private static final String PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD;//默认密码
    private static final String BROKEURL = ActiveMQConnection.DEFAULT_BROKER_URL;//默认连接url

    public static void main(String[] args) {
        ConnectionFactory connectionFactory;//连接工厂
        Connection connection = null;//连接
        Session session;//会话或者接受发送消息的线程
        Destination destination;//消息目的地
        MessageConsumer messageConsumer;//消息消费者
        //实例化连接工厂
        connectionFactory = new ActiveMQConnectionFactory(JMSCustomerA.USERNAME, JMSCustomerA.PASSWORD, JMSCustomerA.BROKEURL);
        try {
            connection = connectionFactory.createConnection();//通过连接工厂获取连接
            connection.start();//启动连接
            session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);//创建session
            //destination = session.createQueue("FristQueue1");//创建连接的消息队列
            destination = session.createTopic("FristTopic1");//创建消息队列
            messageConsumer = session.createConsumer(destination);//创建消息消费者
            messageConsumer.setMessageListener(new ListenerA());//注册消息监听
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
