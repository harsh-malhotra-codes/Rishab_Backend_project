package in.Project.RestApi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProfileDTO {

    private String profileId;

    private String email;

    private String name;

    private String password;

    private Timestamp updatedAt;

    private Timestamp createdAt;

}
