package utils

import java.util.Properties

import com.alibaba.druid.pool.DruidDataSourceFactory
import javax.sql.DataSource

/**
  * Description : mysql数据库连接池
  *
  * @author lyh
  * @date 2020-06-20
  */
object MysqlConnectPool {
  private val props: Properties = PropertyRead.getProperties("connect-mysql.properties")

  private val source: DataSource = DruidDataSourceFactory.createDataSource(props)

  def getConnection={
    source.getConnection()
  }
}
