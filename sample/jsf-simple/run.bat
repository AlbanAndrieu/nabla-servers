cls

call setenv.bat
REM OK call mvn org.codehaus.cargo:cargo-maven2-plugin:run -Psample,run-its -Dserver=jetty9x -Djetty.port=9090 -Ddatabase=derby -Dwebdriver.base.url=http://localhost:9090 > deploy.log 2>&1
REM OK call mvn clean install org.codehaus.cargo:cargo-maven2-plugin:run -Psample,run-its -Dserver=jetty9x -Ddatabase=derby -Djetty.port=9090 -Dwebdriver.base.url=http://localhost:9090 > deploy.log 2>&1
REM TODO call mvn clean install org.codehaus.cargo:cargo-maven2-plugin:run -Psample,run-its -Dserver=tomcat7x -Ddatabase=derby > deploy.log 2>&1
REM call mvn clean install tomcat7:run -Psample,run-its -Dserver=tomcat7x -Ddatabase=derby > deploy.log 2>&1
REM OK clean mvn tomcat7:redeploy -Psample,run-its -Dserver=tomcat7x -Ddatabase=derby -Dtomcat.username=admin -Dtomcat.password=microsoft > deploy.log 2>&1
REM OK call mvn clean tomcat7:redeploy -Psample,run-its -Dserver=tomcat7x -Ddatabase=derby > deploy.log 2>&1
REM call mvn clean install cargo:undeploy cargo:deploy -Psample,run-its -Dserver=tomcat7x -Ddatabase=derby > deploy.log 2>&1
REM call mvn org.codehaus.cargo:cargo-maven2-plugin:deployer-deploy -Psample,run-its -Dserver=tomcat7x -Ddatabase=derby > deploy.log 2>&1
REM check result at http://192.168.0.29:8280/manager/html
REM OK call mvn clean install jetty:run-war -Psample,run-its -Dserver=jetty9x -Ddatabase=derby -Djetty.port=9090 > deploy.log 2>&1
REM OK call mvn org.codehaus.cargo:cargo-maven2-plugin:run -Psample,run-its,arq-jbossas-managed -Dserver=jboss7x -Dwebdriver.base.url=http://localhost:8180 -Darquillian=arq-jbossas-managed -Dwebdriver.chrome.driver=/var/lib/chromedriver -Djboss.port=8180 > deploy.log 2>&1
REM OK call mvn clean install jmeter:jmeter -Psample,run-its,jmeter -Dserver=jetty9x -Ddatabase=derby -Djetty.port=9090 -Dwebdriver.base.url=http://localhost:9090 > jmeter.log 2>&1
REM OK call mvn jmeter:gui -Psample,jmeter -Dserver=jetty9x -Ddatabase=derby -Djetty.port=9090 -Dwebdriver.base.url=http://localhost:9090 > jmeter-gui.log 2>&1

REM build with mvn clean install -Psample,jacoco,run-its -Dserver=jetty9x
call java -jar target/dependency/jetty-runner.jar --port 9090 target/*.war

pause
