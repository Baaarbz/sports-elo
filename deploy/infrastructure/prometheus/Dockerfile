FROM docker.io/prom/prometheus

USER root
RUN adduser -D -H prometheus
USER prometheus

COPY --chown=65534:65534 prometheus/prometheus.yml /etc/prometheus/prometheus.yml