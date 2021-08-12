package loans.kafka.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import loans.domain.entity.CreditOffer;
import loans.repository.OfferRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import scoring.domain.Approved;


import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class ApprovedConsumer {

    @Autowired
    private OfferRepository offerRepository;

    @KafkaListener(topics = "approved", groupId = "offer_id")
    public void msgListener(ConsumerRecord<String, Object> record){
        //
        log.info("=====Consuming the offer:=" + record.key());
        log.info("==== object value:==" + record.value());
        //
        Approved approved = objectToClass(record.value().toString());
        log.info("Approved:=" + approved.getId() + " offerId:=" + approved.getOfferId());

        Optional<CreditOffer> clo = offerRepository.findById(approved.getOfferId());
        if(clo.isEmpty()) {
            log.info("CreditOffer c id:=" + approved.getOfferId() + " не найден!");
        } else {
            CreditOffer creditOffer = clo.get();
            creditOffer.setMessage(approved.getMessage());
            creditOffer.setCompleted(approved.isCompleted());
            offerRepository.save(creditOffer);
            log.info("CreditOffer update ok!");
        }
    }


    public Approved objectToClass(String json) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Approved approved  = mapper.readValue(json, Approved.class);
            return approved;
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
