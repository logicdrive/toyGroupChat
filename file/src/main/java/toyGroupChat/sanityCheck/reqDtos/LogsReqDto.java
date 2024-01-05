package toyGroupChat.sanityCheck.reqDtos;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class LogsReqDto {
    private int lineLength = 10;
    private String regFilter = "";
}
