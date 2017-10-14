//package www.lvmama.com.conf;
//
//import com.mongodb.Mongo;
//import com.mongodb.MongoClient;
//import com.mongodb.MongoClientURI;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.PropertySource;
//import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
//import org.springframework.data.mongodb.MongoDbFactory;
//import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
//import org.springframework.data.mongodb.config.EnableMongoAuditing;
//import org.springframework.data.mongodb.core.MongoTemplate;
//import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
//import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
//import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
//import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
//import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
//import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
//
///**
// * Created by Administrator on 2017/5/15.
// */
//
//@ConfigurationProperties(prefix="spring.data.mongodb")
//@Configuration
//public class MongoConfig extends AbstractMongoConfiguration {
//
//
//    private String uri;
//
//    private String database;
//
//    public void setUri(String uri) {
//        this.uri = uri;
//    }
//
//    public void setDatabase(String database) {
//        this.database = database;
//    }
//
//    @Autowired
//    private ApplicationContext appContext;
//
//    @Override
//    protected String getDatabaseName() {
//        return database;
//    }
//
//    @Override
//    @Bean
//    public Mongo mongo() throws Exception {
//        MongoClientURI mongoClientURI = new MongoClientURI(uri);
//        return new MongoClient(mongoClientURI);
//    }
//
//    @Override
//    @Bean
//    public MongoTemplate mongoTemplate() throws Exception {
//        MongoDbFactory factory = mongoDbFactory();
//
//        MongoMappingContext mongoMappingContext = new MongoMappingContext();
//        mongoMappingContext.setApplicationContext(appContext);
//
//        MappingMongoConverter converter = new MappingMongoConverter(new DefaultDbRefResolver(factory), mongoMappingContext);
//        converter.setTypeMapper(new DefaultMongoTypeMapper(null));
//
//        return new MongoTemplate(factory, converter);
//    }
//
//
//    @Bean
//    public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
//        return new PropertySourcesPlaceholderConfigurer();
//    }
//}
