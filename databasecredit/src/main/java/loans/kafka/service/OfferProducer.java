package loans.kafka.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import loans.domain.entity.CreditOffer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OfferProducer {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    /**
     * Producer для CreditOffer
     * @param creditOffer
     * @param key
     */
    public void produceOffer(CreditOffer creditOffer, String key) {
        log.info("Producing the offer:=" + creditOffer);
        try {
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            //ow.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
            kafkaTemplate.send("offer", key, ow.writeValueAsString(creditOffer));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            log.error("err:=", e.getMessage());
        }
    }

}
