#docker run --rm  -p 5432:5432 -e POSTGRES_PASSWORD=nnpassword -e POSTGRES_DB=nn --name postgres_nn -d postgres
#docker run --rm -p 8080:8080 -p 9990:9990  --name wildfly jboss/wildfly /opt/jboss/wildfly/bin/standalone.sh -bmanagement 0.0.0.0
#docker cp /opt/workspace/gitrepo/nn-web/target /opt/jboss/wildfly/standalone/deployments/
FROM postgres:latest

ARG postgres

ENV POSTGRES_PASSWORD=nnpassword
ENV POSTGRES_DB=nn

ENTRYPOINT docker-entrypoint.sh

CMD postgres