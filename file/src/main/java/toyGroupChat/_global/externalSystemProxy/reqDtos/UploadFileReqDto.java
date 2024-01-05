package toyGroupChat._global.externalSystemProxy.reqDtos;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;

@Getter
public class UploadFileReqDto implements ExternalSystemProxyReqDto {
    private final String dataUrl;

    public UploadFileReqDto(String dataUrl) {
        this.dataUrl = dataUrl;
    }

    public Map<String, Object> hashMap() {
        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("dataUrl", this.dataUrl);
        return hashMap;
    }

    public String toString() { 
        return String.format("%s(dataUrlLength=%d)",
            this.getClass().getSimpleName(), this.dataUrl.length());
    }
}
