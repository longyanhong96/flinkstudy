package dynamic.broadcast.mongo;

/**
 * Description : 获取mongolian的配置
 *
 * @author lyh
 * @date 2020-08-09
 */
public interface MongoDBSource {

    default String getCleanXML(){return null;}

    default String getStandardXML() {
        return null;
    }

}
