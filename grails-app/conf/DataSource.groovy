dataSource {
    pooled = true
    //driverClassName = "org.hsqldb.jdbcDriver"
	driverClassName = "com.mysql.jdbc.Driver"
	dialect = "org.hibernate.dialect.MySQL5InnoDBDialect"
    username = "root"
    password = ""
}
hibernate {
    cache.use_second_level_cache = true
    cache.use_query_cache = true
    cache.provider_class = 'net.sf.ehcache.hibernate.EhCacheProvider'
}
// environment specific settings
environments {
    development {
        dataSource {
			logSql = true
            dbCreate = "create-drop" // one of 'create', 'create-drop','update'
            //url = "jdbc:hsqldb:mem:devDB"
			url = "jdbc:mysql://127.0.0.1:3306/db_sana"
        }
    }
    test {
        dataSource {
            dbCreate = "update"
            //url = "jdbc:hsqldb:mem:testDb"
			url = "jdbc:mysql://127.0.0.1:3306/db_sana"
        }
    }
    production {
        dataSource {
            dbCreate = "update"
            url = "jdbc:hsqldb:file:prodDb;shutdown=true"
        }
    }
}
