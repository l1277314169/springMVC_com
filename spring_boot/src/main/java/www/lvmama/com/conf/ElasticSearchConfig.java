package www.lvmama.com.conf;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.transport.TransportAddress;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

/**
 * Created by Administrator on 2017/5/16.
 */
@Configuration
@ConfigurationProperties(prefix = "spring.data.elasticsearch.cluster-nodes")
public class ElasticSearchConfig {

   private String url;

    public void setUrl(String url) {
        this.url = url;
    }

//    @Bean
//    public ElasticsearchOperations elasticsearchTemplate() {
//        return new ElasticsearchTemplate(client());
//    }

//    @Bean
//    public Client client(){
//        Settings settings = ImmutableSettings.settingsBuilder().put("cluster.name", "elasticsearch").build();
//        TransportClient client= new TransportClient();
//        TransportAddress address = new InetSocketTransportAddress(hostname, port);
//        client.addTransportAddress(address);


        // 配置你的es,现在这里只配置了集群的名,默认是elasticsearch,跟服务器的相同
//        Settings settings = ImmutableSettings.settingsBuilder().put("cluster.name", "elasticsearch").build();
//        // 这里可以同时连接集群的服务器,可以多个,并且连接服务是可访问的
//        TransportClient  client = new TransportClient(settings).addTransportAddress(
//                new InetSocketTransportAddress(InetAddressUtilities.getLocalIp(), 9300)).addTransportAddress(
//                new InetSocketTransportAddress("host2", 9300));
//        return null;
//    }
}