package scoring.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import scoring.domain.Approved;
import scoring.domain.CreditOffer;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class OfferScoringConsumer {

    private final ScoringService scoringService;

    @KafkaListener(topics = "offer", groupId = "offer_id")
    //public void msgListener(Object msg, ConsumerRecordMetadata meta) {
    public void msgListener(ConsumerRecord<String, Object> record) {
        try {
            log.info("=====Consuming the offer:=" + record.key());
            log.info("==== object value:==" + record.value());
            //json to class creditOffer
            CreditOffer creditOffer = objectToClass(record.value().toString());
            log.info("Offer:=" + creditOffer.getId() + " name:=" + creditOffer.getFullName());

            //через map
            objectToClassMap(record.value().toString());
            //расчет скоринга
            Approved approved = new Approved();
            approved.setOfferId(creditOffer.getId());
            approved.setCompleted(
                    scoringService.creditApproval(creditOffer)
            );
            if(approved.isCompleted()) {
                log.info("Offer:= Кредит на суммe:" + creditOffer.getAmount() + " одобрен!");
                approved.setMessage("Кредит на суммe:" + creditOffer.getAmount() + " одобрен!");
            } else {
                log.info("Offer:= Кредит на суммe:" + creditOffer.getAmount() + " не может быть предоставлен!");
                approved.setMessage("Кредит на суммe:" + creditOffer.getAmount() + " не может быть предоставлен!");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public CreditOffer objectToClass(String json) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            CreditOffer creditOffer = mapper.readValue(json, CreditOffer.class);
            return creditOffer;
        } catch (JsonProcessingException ex) {
            log.error("Err:=" + ex.getMessage());
            return null;
        }
    }

    public void objectToClassMap(String json) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> objectMap = mapper.readValue(json, Map.class);

            log.info("map:=" + objectMap);

        } catch (JsonProcessingException ex) {
            log.error("Err:=" + ex.getMessage());
        }
    }

}
