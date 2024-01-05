package toyGroupChat.sanityCheck;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import toyGroupChat._global.event.FileUploadFailed;
import toyGroupChat._global.event.FileUploaded;
import toyGroupChat._global.logger.CustomLogger;
import toyGroupChat._global.logger.CustomLoggerType;

import toyGroupChat.sanityCheck.reqDtos.LogsReqDto;
import toyGroupChat.sanityCheck.reqDtos.MockFileUploadFailedReqDto;
import toyGroupChat.sanityCheck.reqDtos.MockFileUploadedReqDto;
import toyGroupChat.sanityCheck.resDtos.LogsResDto;

@Service
@RequiredArgsConstructor
public class SanityCheckService {
    private final String logFilePath = "./logs/logback.log";

    // 출력된 로그들 중에서 끝부분 몇라인을 읽어서 반환시키기 위해서
    public LogsResDto logs(LogsReqDto logsReqDto) throws FileNotFoundException {
            List<String> logs = new ArrayList<>();

            try {
                
                CustomLogger.debug(CustomLoggerType.EFFECT, "Try to read logs", String.format("{filePath: %s}", logFilePath));
                
                Scanner myReader = new Scanner(new File(logFilePath));
                while (myReader.hasNextLine())
                {
                    String readLog = myReader.nextLine();
                    if (logsReqDto.getRegFilter().isEmpty()) logs.add(readLog);
                    else if(readLog.matches(logsReqDto.getRegFilter())) logs.add(readLog);
                }
                myReader.close();
                
                CustomLogger.debug(CustomLoggerType.EFFECT, "Read logs", String.format("{logsSize: %d}", logs.size()));

            } catch (FileNotFoundException e) {
                CustomLogger.error(e, "Error while reading logs", String.format("{filePath: %s}", logFilePath));
                throw new FileNotFoundException();
            }

            return new LogsResDto(logs.subList(Math.max(logs.size()-logsReqDto.getLineLength(), 0), logs.size()));
    }


    // Policy 테스트용으로 FileUploaded 이벤트를 강제로 발생시키기 위해서
    public void mockFileUploaded(MockFileUploadedReqDto mockData) {
        (new FileUploaded(mockData)).publish();
    }

    // Policy 테스트용으로 FileUploadFailed 이벤트를 강제로 발생시키기 위해서
    public void mockFileUploadFailed(MockFileUploadFailedReqDto mockData) {
        (new FileUploadFailed(mockData)).publish();
    }
}
