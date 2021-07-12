package tw.peugin.baha.luckydraw.editorsupport;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import tw.peugin.baha.bahaForum.entity.DrawRequest;

import java.beans.PropertyEditorSupport;

public class DrawRequestEditorSupport extends PropertyEditorSupport {
    @Override
    public void setAsText(String json) throws IllegalArgumentException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, false);

        DrawRequest drawRequest = null;
        try {
            drawRequest = mapper.readValue(json, DrawRequest.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        setValue(drawRequest);
    }
}
