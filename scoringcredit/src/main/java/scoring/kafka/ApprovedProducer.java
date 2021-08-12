package scoring.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import scoring.domain.Approved;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApprovedProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void produceApproved(Approved approved, String key) {
        log.info("Producing the offer:=" + approved);
        try {
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            kafkaTemplate.send("approved", key, ow.writeValueAsString(approved));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            log.error("err:=", e.getMessage());
        }
    }

}
