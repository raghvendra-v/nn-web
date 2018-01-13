FROM jboss/wildfly:10.1.0.Final
EXPOSE 8080
COPY /maven/${project.build.finalName}.${project.packaging} ${wildfly.deployment.dir}