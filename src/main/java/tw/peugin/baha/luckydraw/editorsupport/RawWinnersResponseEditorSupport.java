package tw.peugin.baha.luckydraw.editorsupport;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import tw.peugin.baha.bahaForum.entity.DrawRequest;
import tw.peugin.baha.luckydraw.entity.RawWinnersResponse;

import java.beans.PropertyEditorSupport;

public class RawWinnersResponseEditorSupport extends PropertyEditorSupport {
    @Override
    public void setAsText(String json) throws IllegalArgumentException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, false);

        RawWinnersResponse rawWinnersResponse = null;
        try {
            rawWinnersResponse = mapper.readValue(json, RawWinnersResponse.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        setValue(rawWinnersResponse);
    }
}