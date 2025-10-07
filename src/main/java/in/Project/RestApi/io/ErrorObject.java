package in.Project.RestApi.io;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorObject {
    private Integer statusCode;
    private String message;
    private Date timeStamp;
    private String errorCode;
}
