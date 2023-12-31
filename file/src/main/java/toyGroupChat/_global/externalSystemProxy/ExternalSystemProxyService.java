package toyGroupChat._global.externalSystemProxy;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.beans.factory.annotation.Value;

import com.fasterxml.jackson.databind.ObjectMapper;

import toyGroupChat._global.externalSystemProxy.reqDtos.EchoWithJsonReqDto;
import toyGroupChat._global.externalSystemProxy.reqDtos.ExternalSystemProxyReqDto;
import toyGroupChat._global.externalSystemProxy.reqDtos.UploadFileReqDto;
import toyGroupChat._global.externalSystemProxy.resDtos.EchoWithJsonResDto;
import toyGroupChat._global.externalSystemProxy.resDtos.ExternalSystemProxyResDto;
import toyGroupChat._global.externalSystemProxy.resDtos.UploadFileResDto;
import toyGroupChat._global.logger.CustomLogger;
import toyGroupChat._global.logger.CustomLoggerType;

@Service
public class ExternalSystemProxyService {

    @Value("${externalSystem.ip}")
    private String externalSystemIp;

    @Value("${externalSystem.port}")
    private String externalSystemPort;

    
    // JSON 송수신 여부를 간편하게 테스트해보기 위해서
    public EchoWithJsonResDto echoWithJson(EchoWithJsonReqDto echoWithJsonReqDto) throws Exception {
        return this.jsonCommunication("/sanityCheck/echoWithJson", echoWithJsonReqDto, EchoWithJsonResDto.class);
    }

    // 주어진 DataUrl을 기반으로 파일을 S3에 업로드시키고, 관련 URL을 반환하기 위해서
    public UploadFileResDto uploadFile(UploadFileReqDto uploadFileReqDto) throws Exception {
        return this.jsonCommunication("/s3/uploadFile", uploadFileReqDto, UploadFileResDto.class);
    }


    // ExternalSystem과 JSON을 기반으로 한 일관성 있는 통신을 위해서
    public <S extends ExternalSystemProxyReqDto, R extends ExternalSystemProxyResDto> R jsonCommunication(String requestPath, S reqDto, Class<R> resType) throws Exception {
        try {

            String requestUrl = String.format("http://%s:%s", this.externalSystemIp, this.externalSystemPort) + requestPath;
            CustomLogger.debug(CustomLoggerType.EFFECT, "Request to external system",String.format("{requestUrl: %s, reqDto: %s}", requestUrl, reqDto));

            String resultRawText = WebClient.create(requestUrl)
                .put()
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(reqDto.hashMap()))
                .retrieve()
                .bodyToMono(String.class)
                .block();
            CustomLogger.debug(CustomLoggerType.EFFECT, "Read results from external system", String.format("{resultRawText: %s}", resultRawText));

            ObjectMapper mapper = new ObjectMapper();
            R resDto = mapper.readValue(resultRawText, resType);
            return resDto;

        } catch (Exception e) {
            CustomLogger.error(e, "Error while requesting to externalSystem", String.format("{requestPath: %s, reqDto: %s}", requestPath, reqDto));
            throw e;
        }
    }
}
