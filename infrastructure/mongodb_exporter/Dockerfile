FROM alpine

#RUN apk update && apk add wget && \
#    wget https://github.com/dcu/mongodb_exporter/releases/download/v1.0.0/mongodb_exporter-linux-amd64

# this is a big no no :) - don't commit binaries in git.
ADD mongodb_exporter-linux-amd64 /

RUN chmod 755 mongodb_exporter-linux-amd64

EXPOSE 9001
