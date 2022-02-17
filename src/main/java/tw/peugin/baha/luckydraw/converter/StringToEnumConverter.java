package tw.peugin.baha.luckydraw.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import tw.peugin.baha.luckydraw.model.DuplicatePost;

@Component
public class StringToEnumConverter implements Converter<String, DuplicatePost> {
    @Override
    public DuplicatePost convert(String source) {
        try {
            return DuplicatePost.fromInteger(Integer.parseInt(source));
        }catch (IllegalArgumentException e){
            return null;
        }
    }
}
