package zhou.wu.bootes.config;

//import org.elasticsearch.client.transport.TransportClient;
//import org.elasticsearch.common.network.InetAddresses;
//import org.elasticsearch.common.settings.Settings;
//import org.elasticsearch.common.transport.InetSocketTransportAddress;
//import org.elasticsearch.transport.client.PreBuiltTransportClient;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.net.InetSocketAddress;

/**
 * @author Lin.xc
 * @date 2019/9/27
 */
//@Configuration
public class ServerModule {

  /*  @Bean
    public TransportClient transportClient() {
        Settings settings = Settings.builder().put("cluster.name", "elasticsearch").build();
        //我用6.3.2版本的时候这里一直报异常说找不到InetSocketTransportAddress类，这应该和jar有关，当我改成5.6.8就不报错了
        //6.3.2这里TransportAddress代替InetSocketTransportAddress
        TransportClient client = new PreBuiltTransportClient(settings);
        client.addTransportAddress(new InetSocketTransportAddress(
                new InetSocketAddress(InetAddresses.forString("127.0.0.1"), 9300)));
        return client;
    }*/
}
