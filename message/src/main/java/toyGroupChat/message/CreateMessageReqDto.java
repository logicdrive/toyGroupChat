package toyGroupChat.message;

import lombok.Data;

@Data
public class CreateMessageReqDto {
    private Long roomId;
    private String content;
    private String fileName="";
    private String fileDataUrl="";

    public String toString() { 
        return String.format("%s(roomId=%d, content=%s, fileName=%s, fileDataUrlLength=%d)",
            this.getClass().getSimpleName(), this.roomId, this.content, this.fileName, this.fileDataUrl.length());
    }
}