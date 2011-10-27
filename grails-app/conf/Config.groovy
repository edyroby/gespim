// locations to search for config files that get merged into the main config
// config files can either be Java properties files or ConfigSlurper scripts

grails.config.locations = [MyConfig]
// grails.config.locations = [ "classpath:${appName}-config.properties",
//                             "classpath:${appName}-config.groovy",
//                             "file:${userHome}/.grails/${appName}-config.properties",
//                             "file:${userHome}/.grails/${appName}-config.groovy"]

// if(System.properties["${appName}.config.location"]) {
//    grails.config.locations << "file:" + System.properties["${appName}.config.location"]
// }


/*
* Definizione di un file di configurazione esterno
*/
def ENV_NAME = "GESPIM_CONFIG_PATH"
println "--------------------------------------------------------"
// 1: A command line option should override everything.
// Test by running:
// grails -Dgepam_import.config.location=C:\path\to\config_file.groovy run-app
// or
// grails -Dgepam_import.config.location=C:\path\to\config_file.properties run-app
if (System.getProperty(ENV_NAME) && new File(System.getProperty(ENV_NAME)).exists()) {
	println "Including configuration file specified on command line: " + System.getProperty(ENV_NAME)
	grails.config.locations << "file:" + System.getProperty(ENV_NAME)
}
// 2: If this is a developer machine, then they will have their own config and
// I should use that.
else if (new File("${userHome}/.grails/${appName}-config.groovy").exists()) {
	println "*** User defined config: file:${userHome}/.grails/${appName}-config.groovy. ***"
	grails.config.locations << "file:${userHome}/.grails/${appName}-config.groovy"
}
// 3: Most QA and PROD machines should define a System Environment variable
// that will define where we should look.
else if (System.getenv(ENV_NAME) && new File(System.getenv(ENV_NAME)).exists()) {
	println("Including System Environment configuration file: " + System.getenv(ENV_NAME))
	grails.config.locations << "file:" + System.getenv(ENV_NAME)
}
// 4: Last resort is looking for a properties based configuration on the developer
// machine.
else if (new File("${userHome}/.grails/${appName}-config.properties").exists()) {
	println "*** User defined config: file:${userHome}/.grails/${appName}-config.properties. ***"
	grails.config.locations << "file:${userHome}/.grails/${appName}-config.properties"
}
// 5: Houston, we have a problem!
else {
	println "*** No external configuration file defined. ***"
	println "*** No external configuration file defined. ***"
	println "*** No external configuration file defined. ***"
}
println "...............config locations: " +grails.config.locations

grails.project.groupId = appName // change this to alter the default package name and Maven publishing destination
grails.mime.file.extensions = true // enables the parsing of file extensions from URLs into the request format
grails.mime.use.accept.header = false
grails.mime.types = [ html: ['text/html','application/xhtml+xml'],
                      xml: ['text/xml', 'application/xml'],
                      text: 'text/plain',
                      js: 'text/javascript',
                      rss: 'application/rss+xml',
                      atom: 'application/atom+xml',
                      css: 'text/css',
                      csv: 'text/csv',
                      all: '*/*',
                      json: ['application/json','text/json'],
                      form: 'application/x-www-form-urlencoded',
                      multipartForm: 'multipart/form-data'
                    ]

// URL Mapping Cache Max Size, defaults to 5000
//grails.urlmapping.cache.maxsize = 1000

// The default codec used to encode data with ${}
grails.views.default.codec = "none" // none, html, base64
grails.views.gsp.encoding = "UTF-8"
grails.converters.encoding = "UTF-8"
// enable Sitemesh preprocessing of GSP pages
grails.views.gsp.sitemesh.preprocess = true
// scaffolding templates configuration
grails.scaffolding.templates.domainSuffix = 'Instance'

// Set to false to use the new Grails 1.2 JSONBuilder in the render method
grails.json.legacy.builder = false
// enabled native2ascii conversion of i18n properties files
grails.enable.native2ascii = true
// whether to install the java.util.logging bridge for sl4j. Disable for AppEngine!
grails.logging.jul.usebridge = true
// packages to include in Spring bean scanning
grails.spring.bean.packages = []

// request parameters to mask when logging exceptions
grails.exceptionresolver.params.exclude = ['password']

// set per-environment serverURL stem for creating absolute links
environments {
    production {
        grails.serverURL = "http://www.changeme.com"
    }
    development {
        grails.serverURL = "http://localhost:8080/${appName}"
    }
    test {
        grails.serverURL = "http://localhost:8080/${appName}"
    }

}

// log4j configuration
log4j = {
    // Example of changing the log pattern for the default console
    // appender:
    //
    //appenders {
    //    console name:'stdout', layout:pattern(conversionPattern: '%c{2} %m%n')
    //}

    error  'org.codehaus.groovy.grails.web.servlet',  //  controllers
           'org.codehaus.groovy.grails.web.pages', //  GSP
           'org.codehaus.groovy.grails.web.sitemesh', //  layouts
           'org.codehaus.groovy.grails.web.mapping.filter', // URL mapping
           'org.codehaus.groovy.grails.web.mapping', // URL mapping
           'org.codehaus.groovy.grails.commons', // core / classloading
           'org.codehaus.groovy.grails.plugins', // plugins
           'org.codehaus.groovy.grails.orm.hibernate', // hibernate integration
           'org.springframework',
           'org.hibernate',
           'net.sf.ehcache.hibernate'

    warn   'org.mortbay.log'
}

// Added by the Joda-Time plugin:
grails.gorm.default.mapping = {
	"user-type" type: org.joda.time.contrib.hibernate.PersistentDateTime, class: org.joda.time.DateTime
	"user-type" type: org.joda.time.contrib.hibernate.PersistentDuration, class: org.joda.time.Duration
	"user-type" type: org.joda.time.contrib.hibernate.PersistentInstant, class: org.joda.time.Instant
	"user-type" type: org.joda.time.contrib.hibernate.PersistentInterval, class: org.joda.time.Interval
	"user-type" type: org.joda.time.contrib.hibernate.PersistentLocalDate, class: org.joda.time.LocalDate
	"user-type" type: org.joda.time.contrib.hibernate.PersistentLocalTimeAsString, class: org.joda.time.LocalTime
	"user-type" type: org.joda.time.contrib.hibernate.PersistentLocalDateTime, class: org.joda.time.LocalDateTime
	"user-type" type: org.joda.time.contrib.hibernate.PersistentPeriod, class: org.joda.time.Period
}

// Added by the Spring Security Core plugin:
grails.plugins.springsecurity.userLookup.userDomainClassName = 'it.solvingteam.gespim.security.Utente'
grails.plugins.springsecurity.userLookup.authorityJoinClassName = 'it.solvingteam.gespim.security.UtenteRuolo'
grails.plugins.springsecurity.authority.className = 'it.solvingteam.gespim.security.Ruolo'
grails.plugins.springsecurity.requestMap.className = 'it.solvingteam.gespim.security.RequestMap'
grails.plugins.springsecurity.securityConfigType = 'Requestmap'


jasper.dir.reports = '/reports'


// Added by the Grails Activiti plugin:
activiti {
    processEngineName = "activiti-engine-default"
	  databaseType = "mysql" 
	  deploymentName = appName
	  deploymentResources = ["file:./grails-app/conf/**/*.bpmn*.xml", 
	                         "file:./grails-app/conf/**/*.png", 
	                         "file:./src/taskforms/**/*.form"]
	  jobExecutorActivate = false
	  mailServerHost = "smtp.gmail.com"
	  mailServerPort = "465"
	  mailServerUsername = ""
	  mailServerPassword = ""
	  mailServerDefaultFrom = "username@yourserver.com"
	  history = "audit" // "none", "activity", "audit" or "full"
	  sessionUsernameKey = "username"
	  useFormKey = true
}

environments {
    development {
        activiti {
			  processEngineName = "activiti-engine-dev"
			  databaseSchemaUpdate = true // true, false or "create-drop"	  
        }
    }
    test {
        activiti {
			  processEngineName = "activiti-engine-test"
			  databaseSchemaUpdate = true
	      mailServerPort = "5025"			  
        }
    }	
    production {
        activiti {
			  processEngineName = "activiti-engine-prod"
			  databaseSchemaUpdate = false
			  jobExecutorActivate = true
        }
    }
}	

