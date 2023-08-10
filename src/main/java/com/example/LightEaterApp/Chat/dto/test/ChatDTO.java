package com.example.LightEaterApp.Chat.dto.test;
import com.example.LightEaterApp.Chat.model.ChatEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
//사용자에게 보여줄 데이터만 따로
public class ChatDTO {
    private String chatId;
    private String userId;
    //1학기 임시 테스트용 - 문장 주고 받기
    private String chatWords;       //다희언니에게 변수명 물어보기
    private int resultNum;

    public ChatDTO(final ChatEntity chatEntity){
        this.chatId = chatEntity.getChatId();
        this.userId = chatEntity.getUserId();
        //this.chatWords = chatEntity.getChatWords();
        this.resultNum = chatEntity.getResultNum();
    }
    public static ChatEntity toEntity(final ChatDTO dto) {
        return ChatEntity.builder()
                .chatId(dto.getChatId())
                .userId(dto.getUserId())
                //.chatWords(dto.getChatWords())
                .resultNum(dto.getResultNum())
                .build();
    }
    /*
    public static List<Object> toArray(final ChatDTO dto) {
        List<Object> array = new ArrayList<>();

        array.add(dto.getChatId());
        array.add(dto.getUserId());
        array.add(dto.getChatWords());
        array.add(dto.getResultNum());
        array.add(dto.getChatId());

        return array<>();
    }

*/
    /*
    private String relation;
    private String chatFiles;
    private String[] chatImage;
    private String[] gSentence;
    private int resultNum;
    private Date chatDate;
*/
}