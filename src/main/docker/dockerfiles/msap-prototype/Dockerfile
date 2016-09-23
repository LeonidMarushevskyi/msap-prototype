FROM java:alpine

ADD msap-prototype.war /msap-prototype.war
ADD run.sh /run.sh
ADD msap-prototype.git.txt /msap-prototype.git.txt
RUN sh -c 'touch /msap-prototype.war' \
&&  apk add --update tzdata \
&&  chmod +x /run.sh

EXPOSE 8080

CMD ["/run.sh"]
