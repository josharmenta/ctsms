#!/bin/bash
$JRE_HOME/bin/java -DCTSMS_PROPERTIES="$CTSMS_PROPERTIES" -DCTSMS_JAVA="$CTSMS_JAVA" -Dfile.encoding=Cp1252 -Djava.awt.headless=true -classpath $CATALINA_HOME/webapps/ctsms-web/WEB-INF/lib/ctsms-core-1.78.0.jar:$CATALINA_HOME/webapps/ctsms-web/WEB-INF/lib/* org.phoenixctms.ctsms.executable.DBTool $*