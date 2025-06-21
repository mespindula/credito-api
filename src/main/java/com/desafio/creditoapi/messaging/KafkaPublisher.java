package com.desafio.creditoapi.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaPublisher {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaPublisher.class);
    private static final String TOPICO_CONSULTA_CREDITO = "consulta-credito";

    private final KafkaTemplate<String, String> kafkaTemplate;

    public KafkaPublisher(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    /**
     * Publica um evento de consulta de crédito no tópico Kafka.
     *
     * @param mensagem Mensagem de evento a ser publicada
     */
    public void publicarEventoConsulta(String mensagem) {
        try {
            LOGGER.info("Publicando evento de consulta no Kafka: {}", mensagem);
            kafkaTemplate.send(TOPICO_CONSULTA_CREDITO, mensagem);
        } catch (Exception e) {
            LOGGER.error("Erro ao publicar evento no Kafka", e);
        }
    }
}
