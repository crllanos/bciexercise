package cl.bci.bciexercise.util;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Util {

    private final Gson gsonUtil;

    public String objToJson(Object o){
        return gsonUtil.toJson(o);
    }
}