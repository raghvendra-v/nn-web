FROM jboss/wildfly:10.1.0.Final
EXPOSE 8080
COPY nn-web.war ${wildfly.deployment.dir}

