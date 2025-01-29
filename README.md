# IDDE laborfeladatok

# Deploy tomcat webservlet
gradle :bvim2209-web:deploy

# Undeploy tomcat webservlet
gradle :bvim2209-web:undeploy

# Startup catalina
C:\Tomcat\apache-tomcat-10.1.30\bin\startup.bat

# Shutdown catalina
C:\Tomcat\apache-tomcat-10.1.30\bin\shutdown.bat

# Setting environment variable for config
$env:ACTIVE_PROFILE="jdbc"